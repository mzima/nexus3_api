/**
 * Nexus 3 user mangement
 *
 * @param  action       create/delete (string)
 * @param  id           user name (string)
 * @param  firstName    first name (string)
 * @param  lastName     last name (string)
 * @param  email        email address (string/optional)
 * @param  password     user password (string)
 * @param  active       set user state (boolean/optional/default: true)
 * @param  roles        user roles (array)
 *
 * @return              true/fale
 */

import org.sonatype.nexus.common.entity.*
import org.sonatype.nexus.security.*
import org.sonatype.nexus.security.authz.*
import org.sonatype.nexus.selector.*
import groovy.json.JsonSlurper

def args = new JsonSlurper().parseText(args)

// parameters
String action = args.action
String id = args.id
String firstName = args.firstname
String lastName = args.lastname
String email = args.email ?: 'no@email.com'
String password = args.password
Boolean active = args.active ?: true
def roles = args.roles
int errors = 0

// main
switch(action) {
    case 'create':
        security.addUser(id, firstName, lastName, email, active, password, roles)
        break
    case 'delete':
        security.securitySystem.deleteUser(id, 'default')
        break
    default:
        log.error('undefined action')
        errors = 1
        break
}


// result output
if (errors == 0) {
    log.info('Script completed successfully')
    return(true)
} else {
    log.info('Script failed')
    return(false)
}
