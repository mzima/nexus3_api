#!/bin/bash
#
# Some tests ...
#

source ../helper/settings.conf

user=$nexus_user
pass=$nexus_pass
uri="http://$nexus_host:$nexus_port/service/rest/v1/script"
test=$1
errors=0

function http_send(){
    printf "Run script $1 : "
    http=$(curl -s -o /dev/null -w "%{http_code}\n" -X POST $uri/$1/run -u $user:$pass -H 'Content-Type: application/json' -d $2)
    if [ "$http" = "200" ]; then
        printf "ok\n"
    else
        printf "fail ($http)\n"
        errors=1
    fi
}

function upload_api(){
    cd ../helper
    ./create_all.sh || errors=1
}

function create(){
    http_send 'repo_docker_hosted' '{"action":"create","port":12345,"id":"00_test_docker"}'
    http_send 'repo_raw_hosted' '{"action":"create","id":"00_test_raw"}'
    http_send 'repo_docker_group' '{"action":"create","port":54321,"id":"00_test_docker_group","members":["00_test_docker"]}'
    http_send 'content_selector' '{"action":"create","id":"00_test","expression":"format==\"docker\""}'
    http_send 'privilege' '{"action":"create","id":"00_test","properties":{"contentSelector":"00_test","repository":"00_test_docker","actions":"browse,read,edit"}}'
    http_send 'role' '{"action":"create","id":"00_test","privileges":["00_test"],"roles":["00_test"]}'
    http_send 'user' '{"action":"create","id":"00_test","firstname":"test1","lastname":"test2","password":"test","roles":["00_test"]}'
}

function delete(){
    http_send 'user' '{"action":"delete","id":"00_test"}'
    http_send 'role' '{"action":"delete","id":"00_test"}'
    http_send 'privilege' '{"action":"delete","id":"00_test"}'
    http_send 'content_selector' '{"action":"delete","id":"00_test"}'
    http_send 'repo_docker_group' '{"action":"delete","id":"00_test_docker_group"}'
    http_send 'repo_docker_hosted' '{"action":"delete","id":"00_test_docker"}'
    http_send 'repo_raw_hosted' '{"action":"delete","id":"00_test_raw"}'
}

echo "--- API upload ---"
upload_api
echo "--- API create ---"
create
echo "--- API delete ---"
delete

exit $errors
