#!/usr/bin/env bash

rm -rf backend/build/
./backend/gradlew bootJar -p ./backend
echo "Built backend"

cd frontend
rm -rf build
npm install
npm run-script build
cd ..
echo "Built Frontend"

echo "Set up folder structure on EC2 instance"
scp -i "~/Downloads/exercise2.pem" execute_commands_on_ec2.sh ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com:/home/ubuntu
scp -i "~/Downloads/exercise2.pem" backend/build/libs/backend-0.0.1-SNAPSHOT.jar ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com:/home/ubuntu/server.jar
scp -i "~/Downloads/exercise2.pem" -r frontend/build ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com:/home/ubuntu/
ssh -i "~/Downloads/exercise2.pem" ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com mv build static
echo "Copied latest files to EC2 instance"

ssh -i "~/Downloads/exercise2.pem" ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com chmod 777 execute_commands_on_ec2.sh
ssh -i "~/Downloads/exercise2.pem" ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com sudo apt-get -y install openjdk-11-jdk
echo "Set up for start on EC2 instance"

ssh -i "~/Downloads/exercise2.pem" ubuntu@ec2-52-28-151-166.eu-central-1.compute.amazonaws.com ./execute_commands_on_ec2.sh &
echo "Started service on EC2 instance"
