/**
 * Nexus 3 privilege management
 *
 * @param  action       create/delete (string)
 * @param  id           privilege name (string)
 * @param  description  privilege description (string/optional)
 * @param  type         privilege type (string/optional/default: repository-content-selector)
 * @param  properties   privilege properties (map). Dependent on privilege type.
 *                      e.g. privlege type "repository-content-selector" uses the following map content:
 *                          content-selector: selector_name
 *                          repository: repository_name
 *                          actions: actions (e.g. "browse,read,edit")
 * @return              true/fale
 */

import org.sonatype.nexus.common.entity.*
import org.sonatype.nexus.security.*
import org.sonatype.nexus.security.authz.*
import org.sonatype.nexus.selector.*
import groovy.json.JsonSlurper

def selectorManager = container.lookup(SelectorManager.class.name)
def securitySystem = container.lookup(SecuritySystem.class.name)
def authorizationManager = securitySystem.getAuthorizationManager('default')

def args = new JsonSlurper().parseText(args)

// parameters
String action = args.action
String id = args.id
String name = args.name ?: id
String description = args.description ?: id + ' Privilege'
String type = args.type ?: 'repository-content-selector'
def properties = args.properties
int errors = 0

// main
switch(action) {
    case 'create':
        def privilege = new org.sonatype.nexus.security.privilege.Privilege(
            id: id,
            version: '',
            name: name,
            description: description,
            type: type,
            properties: properties
        )
        authorizationManager.addPrivilege(privilege)
        break
    case 'delete':
        authorizationManager.deletePrivilege(id)
        break
    default:
        log.error('undefined action')
        errors = 1
        break
}

// result
if (errors == 0) {
    log.info('Script completed successfully')
    return(true)
} else {
    log.info('Script failed')
    return(false)
}
