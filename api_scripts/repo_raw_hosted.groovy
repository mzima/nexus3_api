/**
 * Nexus 3 raw hosted repository management
 *
 * @param  action       create/delete (string)
 * @param  id           repository name (string)
 *
 * @return              true/fale
 */

import org.sonatype.nexus.common.entity.*
import org.sonatype.nexus.security.*
import org.sonatype.nexus.security.authz.*
import org.sonatype.nexus.selector.*
import groovy.json.JsonSlurper

def args = new JsonSlurper().parseText(args)

// params
String action = args.action
String id = args.id
int errors = 0

// main
switch(action) {
    case 'create':
        repository.createRawHosted(id, 'default')
        break
    case 'delete':
        repository.getRepositoryManager().delete(id)
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
