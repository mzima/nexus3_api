/**
 * Nexus 3 content selector management
 *
 * @param  action       create/delete (string)
 * @param  id           content selector name (string)
 * @param  description  content selector description (string/optional)
 * @param  expression   content selector expression (string)
 * @param  type         content selector type (string/optional/default: csel)
 * @return              true/fale
 */

import org.sonatype.nexus.common.entity.*
import org.sonatype.nexus.security.*
import org.sonatype.nexus.security.authz.*
import org.sonatype.nexus.selector.*
import groovy.json.JsonSlurper

//def securitySystem = container.lookup(SecuritySystem.class.name)
//def authorizationManager = securitySystem.getAuthorizationManager('default')
def selectorManager = container.lookup(SelectorManager.class.name)
def args = new JsonSlurper().parseText(args)

// parameters
String action = args.action
String id = args.id
String description = args.description ?: id + ' Content Selector'
String expression = args.expression ?: ''
String type = args.type ?: 'csel'

// main
switch(action) {
    case 'create':
        def selectorConfig = new SelectorConfiguration(
            name: id,
            type: type,
            description: description,
            attributes: ['expression': expression]
        )
        selectorManager.create(selectorConfig)
        return(true)
        break
    case 'delete':
        selectorManager.browse().each {
            if (it.name == id) {
                selectorManager.delete(it)
            }
        }
        return(true)
        break
    default:
        log.error('undefined action')
        return(false)
        break
}
