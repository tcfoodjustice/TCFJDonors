#!/usr/bin/env bash

# more bash-friendly output for jq
JQ="jq --raw-output --exit-status"

SHA1=$1
env=$2
configure_aws_cli(){
	aws --version
	aws configure set default.region us-west-2
	aws configure set default.output json
}

deploy_cluster() {

    family="TCFJDonorsTask"

    make_task_def
    register_definition
    if [[ $(aws ecs update-service --cluster TCFJCluster --service donorservice --task-definition $revision | \
                   $JQ '.services[0].taskDefinition') != $revision ]]; then
        echo "Error updating service."
        return 1
    fi

    # wait for older revisions to disappear
    # not really necessary, but nice for demos
    count=0;
    echo "$count"
    echo "$revision"
    echo "$SERVICE_ARN"
    currentTaskDefinition=$(aws ecs describe-services --cluster TCFJCluster --services donorservice --query 'services[?serviceArn=="$SERVICE_ARN"].{taskDefinition:taskDefinition}')
    echo "$currentTaskDefinition"
    primaryRunningCount=$( aws ecs describe-services --cluster TCFJCluster --services donorservice --query 'services[?serviceArn==`arn:aws:ecs:us-west-2:417615409974:service/donorservice`].[deployments[?status==`PRIMARY`].{runningCount:runningCount}]' --output text)
    echo "$primaryRunningCount"

    while [ $primaryRunningCount -le 1 ]; do
        #refactor this to
        primaryRunningCount=$( aws ecs describe-services --cluster TCFJCluster --services donorservice --query 'services[?serviceArn==`arn:aws:ecs:us-west-2:417615409974:service/donorservice`].[deployments[?status==`PRIMARY`].{runningCount:runningCount}]' --output text)
        echo "Waiting for stale deployments:"
        echo "$stale"
        count=$((count + 1))
        echo "$count"
        #we don't want to take too long
        if (( $count > 10 )); then
            echo "Deploy took too long, failing... =( "
            exit 1
        fi

        sleep 10
    done

    echo "Service updated succesfully"
}

make_task_def(){
	task_template="[
		{
			\"name\": \"TCFJDonorsContainer\",
			\"image\": \"larse514/tcfjdonors:$SHA1\",
			\"essential\": true,
			\"memory\": 512,
			\"cpu\": 10,
			\"portMappings\": [
				{
					\"containerPort\": 8080,
					\"hostPort\": 8080
				}
			]
		}
	]"
	task_def=$(printf "$task_template")
}

push_ecr_image(){
	eval $(aws ecr get-login --region us-west-2)
    docker push larse514/tcfjdonors:$SHA1
}

register_definition() {

    echo "$task_def"
    if revision=$(aws ecs register-task-definition --container-definitions "$task_def" --family $family | $JQ '.taskDefinition.taskDefinitionArn'); then
        echo "Revision: $revision"
    else
        echo "Failed to register task definition"
        return 1
    fi

}

configure_aws_cli
push_ecr_image
deploy_cluster