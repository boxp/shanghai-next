#!/bin/bash

# Exit on any error
set -e

sudo /opt/google-cloud-sdk/bin/gcloud docker -- push asia.gcr.io/${PROJECT_NAME}/shanghai:latest
sudo chown -R ubuntu:ubuntu /home/ubuntu/.kube
kubectl apply -f k8s/lego/00-namespace.yml
kubectl apply -f k8s/nginx/00-namespace.yml
kubectl apply -f k8s/nginx/default-deployment.yml
kubectl apply -f k8s/nginx/default-service.yml
kubectl apply -f k8s/nginx/configmap.yml
kubectl apply -f k8s/nginx/service.yml
kubectl apply -f k8s/nginx/deployment.yml
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/lego/deployment.yml
kubectl apply -f k8s/deployment.yml
kubectl apply -f k8s/service.yml
kubectl apply -f k8s/ingress.yml
