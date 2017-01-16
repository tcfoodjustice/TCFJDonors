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
                   $JQ '.service.taskDefinition') != $revision ]]; then
        echo "Error updating service."
        return 1
    fi

    # wait for older revisions to disappear
    # not really necessary, but nice for demos
    for attempt in {1..300}; do
        if stale=$(aws ecs describe-services --cluster TCFJCluster --services TCFJDonorsTask | \
                       $JQ ".services[0].deployments | .[] | select(.taskDefinition != \"$revision\") | .taskDefinition"); then
            echo "Waiting for stale deployments:"
            echo "$stale"
            sleep 5
        else
            echo "Deployed!"
            return 0
        fi
    done
    echo "Service update took too long."
    return 1
}

make_task_def(){
	task_template='[
		{
			"name": "TCFJDonorsTask",
			"image": "larse514/tcfjdonors:$SHA1",
			"essential": true,
			"memory": 200,
			"cpu": 10,
			"portMappings": [
				{
					"containerPort": 8080,
					"hostPort": 8080
				}
			]
		}
	]'
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