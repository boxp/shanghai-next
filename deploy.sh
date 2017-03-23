#!/bin/bash

# Exit on any error
set -e

sudo /opt/google-cloud-sdk/bin/gcloud docker -- push asia.gcr.io/${PROJECT_NAME}/shanghai:latest
sudo chown -R ubuntu:ubuntu /home/ubuntu/.kube
kubectl apply -f k8s/deployment.yml
kubectl apply -f k8s/service.yml
kubectl apply -f k8s/ingress.yml
