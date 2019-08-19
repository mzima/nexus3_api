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

content=`cat $groovy_file`
content_esc=`jq -nc --arg str "$content" '$str'`
template="{ \"name\": \"$groovy_name\", \"type\": \"groovy\", \"content\": $content_esc}"

printf "Upload script $groovy_file_base : "
http=$(curl -s -o /dev/null -w "%{http_code}" -u $nexus_user:$nexus_pass --header "Content-Type: application/json" "$nexus_uri" --data "$template")
if [ "$http" = "204" ]; then
    printf "ok\n"
    exit 0
else
    printf "fail\n"
    exit 1
fi
