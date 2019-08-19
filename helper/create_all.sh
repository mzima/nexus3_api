#!/bin/bash
#
# Upload all ../api_scripts/*groovy API scripts to Nexus
#
ret=0

for each in `ls ../api_scripts/*groovy`
do
	./delete.sh $each || ret=1
	./create.sh $each || ret=1
done

exit $ret
