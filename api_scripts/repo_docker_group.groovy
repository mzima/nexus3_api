/**
 * Nexus 3 docker group repository management
 *
 * @param  action       create/delete (string)
 * @param  id           repository group name (string)
 * @param  port         repository group port (int)
 * @param  members      repository group members (array)
 *
 * @return              true/fale
 */

import org.sonatype.nexus.common.entity.*
import org.sonatype.nexus.security.*
import org.sonatype.nexus.security.authz.*
import org.sonatype.nexus.selector.*
import org.sonatype.nexus.repository.config.Configuration
import groovy.json.JsonSlurper

def args = new JsonSlurper().parseText(args)

// parameters
String action = args.action
String id = args.id
String active = args.active ?: true
String blobstore = args.blobstore ?: "default"
int port = args.port ?: 5050
def members = args.members ?: []
int errors = 0

// main
switch(action) {
    case 'list':
        repositories = repository.repositoryManager.browse().collect { it.name }
        return(repositories)
        break
    case 'create':
        repository.createDockerGroup(id, null, port, members)
        return(true)
        break
    case 'delete':
        repository.getRepositoryManager().delete(id)
        return(true)
        break
    default:
        log.error('undefined action')
        return(false)
        break
}
