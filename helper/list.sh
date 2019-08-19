#!/bin/bash
#
# List all Nexus Groovy API scripts
#

source ./settings.conf
nexus_uri="http://$nexus_host:$nexus_port/service/rest/v1/script"

printf "Listing Integration API Scripts\n"
curl -u $nexus_user:$nexus_pass "$nexus_uri"
echo 
