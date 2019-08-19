#!/bin/bash
#
# Upload a Groovy API scripts to Nexus
#

source ./settings.conf

[ -z $1 ] && echo "argument missing" && exit 1

groovy_file=$1
groovy_file_base=$(basename $groovy_file)
groovy_name=`basename $groovy_file | cut -f1 -d. | tr '[:upper:]' '[:lower:]'`
nexus_uri="http://$nexus_host:$nexus_port/service/rest/v1/script"

printf "Delete script $groovy_file_base : "
http=$(curl -s -o /dev/null -w "%{http_code}\n" -X DELETE -u $nexus_user:$nexus_pass  "$nexus_uri/$groovy_name")
if [ "$http" = "204" ]; then
    printf "ok\n"
    exit 0
else
    printf "fail\n"
    exit 1
fi
