#!/usr/bin/env bash

# Setup Backend
rm -rf backend/build/
echo "Deleted build/ folder"

backend/./gradlew bootJar
echo "Generating jar file"

#Copy execute_commands_on_ec2.sh file which has commands to be executed on server... Here we are copying this file
# every time to automate this process through 'deploy.sh' so that whenever that file changes, it's taken care of
scp -i "~/Downloads/exercise2.pem" execute_commands_on_ec2.sh ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com:/home/ubuntu
echo "Copied latest 'execute_commands_on_ec2.sh' file from local machine to ec2 instance"

scp -i "~/Downloads/exercise2.pem" backend/build/libs/backend-0.0.1-SNAPSHOT.jar ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com:/home/ubuntu
echo "Copied jar file from local machine to ec2 instance"

# change permission of execute_commands_on_ec2.sh
# install java on vm

echo "Connecting to ec2 instance and starting server using java -jar command"
ssh -i "~/Downloads/exercise2.pem" ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com ./execute_commands_on_ec2.sh



# Setup Frontend