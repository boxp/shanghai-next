#!/bin/bash

# Exit on any error
set -e

for f in k8s/*.yml
do
	envsubst < $f > "generated-$(basename $f)"
done

sudo /opt/google-cloud-sdk/bin/gcloud docker -- push asia.gcr.io/${PROJECT_NAME}/shanghai:$CIRCLE_SHA1
sudo chown -R ubuntu:ubuntu /home/ubuntu/.kube
kubectl apply -f k8s/lego/00-namespace.yml
# kubectl apply -f k8s/nginx/00-namespace.yml
# kubectl apply -f k8s/nginx/default-deployment.yml
# kubectl apply -f k8s/nginx/default-service.yml
# kubectl apply -f k8s/nginx/configmap.yml
# kubectl apply -f k8s/nginx/service.yml
# kubectl apply -f k8s/nginx/deployment.yml
kubectl apply -f generated-configmap.yml
kubectl apply -f k8s/lego/deployment.yml
kubectl apply -f generated-deployment.yml --record
kubectl apply -f generated-service.yml
kubectl apply -f generated-ingress.yml
