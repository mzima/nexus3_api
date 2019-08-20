# Sonatype Nexus 3.x API script collection

This is mostly a collection of Groovy scripts to automate things on Sonatype Nexus 3.x.

## api_scripts

* `content_selector.groovy` : API script for managing content_selectors
* `privilege.groovy` : API script for creating or deleting privileges
* `repo_docker_hosted.groovy` : API script for hosted docker repositories
* `repo_docker_group.groovy` : API script for docker group repositories
* `repo_raw_hosted.groovy` : API script for raw docker repositories
* `role.groovy` : API script for roles management
* `user.groovy` : API script for user management

You have to pass parameters to these API scripts. A description of these parameters can be found in the header of each file.

### Example

```
curl -X POST \
  http://localhost:8081/service/rest/v1/script/repo_docker_hosted/run \
  -u admin:admin \
  -H 'Content-Type: application/json' \
  -d '{
	"action": "create",
    "port": 5050,
    "id": "repo_name"
}'
```

## helper

It is necessary to upload the <code>api_scripts</code> to Nexus. There are some scripts, located within the <code>helper</code> directory, which might be helpful to master this task:

* `create_all` : Upload all API scripts to Nexus.
* `create.sh` : Upload a single API script to Nexus.
* `delete.sh` : Delete a single API script from nexus.
* `list.sh` : List all API scripts
* `run.sh` : Run a API script on Nexus (mostly useless because it does not pass any parameters)
* `settings.conf` : Account settings
  
### Example

Upload all files to the Nexus server:

```
cd helper
vi settings.conf
./create.all.sh
```

## tests

The directory <code>tests</code> contains a <code>all.sh</code> scripts which can be used throw test against a local Nexus installation.
