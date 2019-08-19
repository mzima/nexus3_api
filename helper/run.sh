#!/bin/bash
#
# Execute a Nexus Groovy API scripts
#

[ -z $1 ] && echo "argument missing" && exit 1

source ./settings.conf
nexus_uri="http://$nexus_host:$nexus_port/service/rest/v1/script"
name=`echo $1 | cut -f1 -d.`

echo "Running Integration API Script $name: "
curl -X POST -u $nexus_user:$nexus_pass --header "Content-Type: text/plain" "$nexus_uri/script/$name/run"
echo
