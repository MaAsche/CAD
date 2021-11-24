#!/usr/bin/env bash

EC2_URL=$(cat config/url)
EC2_KEY="config/ec2.pem"

bash build.sh

ssh -i $EC2_KEY ubuntu@$EC2_URL sudo rm -rf /home/ubuntu/*
scp -i $EC2_KEY deploy ubuntu@$EC2_URL:/home/ubuntu/
scp -i $EC2_KEY execute_commands_on_ec2.sh ubuntu@$EC2_URL:/home/ubuntu
echo "Copied latest files to EC2 instance"

ssh -i $EC2_KEY ubuntu@$EC2_URL chmod 777 execute_commands_on_ec2.sh
ssh -i $EC2_KEY ubuntu@$EC2_URL sudo apt-get -y install openjdk-11-jdk
echo "Set up for start on EC2 instance"

ssh -i $EC2_KEY ubuntu@$EC2_URL ./execute_commands_on_ec2.sh &
echo "Started service on EC2 instance"
