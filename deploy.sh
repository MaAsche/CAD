#!/usr/bin/env bash

EC2_URL=$(cat config/url)
EC2_KEY="config/ec2.pem"

rm -rf backend/build/
./backend/gradlew bootJar -p ./backend
echo "Built backend"

cd frontend
rm -rf build
rm -rf public/images
npm install
npm run-script build
cd ..
echo "Built Frontend"

ssh -i $EC2_KEY ubuntu@$EC2_URL mkdir config
echo "Set up folder structure on EC2 instance"
scp -i $EC2_KEY execute_commands_on_ec2.sh ubuntu@$EC2_URL:/home/ubuntu
scp -i $EC2_KEY backend/build/libs/backend-0.0.1-SNAPSHOT.jar ubuntu@$EC2_URL:/home/ubuntu/server.jar
scp -i $EC2_KEY config/credentials ubuntu@$EC2_URL:/home/ubuntu/config/credentials
scp -i $EC2_KEY -r frontend/build ubuntu@$EC2_URL:/home/ubuntu/
ssh -i $EC2_KEY ubuntu@$EC2_URL sudo rm -rf static
ssh -i $EC2_KEY ubuntu@$EC2_URL mv build static
echo "Copied latest files to EC2 instance"

ssh -i $EC2_KEY ubuntu@$EC2_URL chmod 777 execute_commands_on_ec2.sh
ssh -i $EC2_KEY ubuntu@$EC2_URL sudo apt-get -y install openjdk-11-jdk
echo "Set up for start on EC2 instance"

ssh -i $EC2_KEY ubuntu@$EC2_URL ./execute_commands_on_ec2.sh &
echo "Started service on EC2 instance"
