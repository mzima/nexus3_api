/**
 * Nexus 3 role management
 *
 * @param  action       create/delete (string)
 * @param  id           role name (string)
 * @param  description  role description (string/optional)
 * @param  privileges   associated privileges (array)
 * @param  roles        associated roles (array)
 *
 * @return              true/fale
 */

import groovy.json.JsonSlurper
import org.sonatype.nexus.security.user.UserNotFoundException
import static org.sonatype.nexus.security.user.UserManager.DEFAULT_SOURCE
import groovy.json.JsonSlurper

def args = new JsonSlurper().parseText(args)

// parameters
String action = args.action
String id = args.id
String name = args.name ?: id
String description = args.description ?: id + 'Role'
def privileges = args.privileges ?: []
def roles = args.roles ?: []
int errors = 0

// main
switch(action) {
    case 'create':
        security.addRole(id, name, description, privileges, roles)
        return(true)
        break
    case 'delete':
        security.securitySystem.getAuthorizationManager(DEFAULT_SOURCE).deleteRole(id)
        return(true)
        break
    default:
        log.error('undefined action')
        return(false)
        break
}
