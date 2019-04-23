#!/bin/bash

######TODO TODO TODO######
######Modify Below Contents Begin######
echo "######setting######"
DOCKER_SERVER=TODO
DOCKER_REPO=TODO
DOCKER_USERNAME=TODO
DOCKER_PASSWORD=TODO
DOCKER_EMAIL=TODO

TENANCY_OCID=TODO
USER_OCID=TODO
FINGERPRINT=TODO
DEFAULT_COMPARTMENT_ID=TODO
######Modify Below Contents End######


######IMPORTANT IMPORTANT IMPORTANT######
######DON'T Modify Below Contents before your understande them######

echo "######create app.yaml######"
DOCKER_SERVER=${DOCKER_SERVER} DOCKER_REPO=${DOCKER_REPO} TENANCY_OCID=${TENANCY_OCID} TENANCY_OCID=${TENANCY_OCID} USER_OCID=${USER_OCID} FINGERPRINT=${FINGERPRINT} DEFAULT_COMPARTMENT_ID=${DEFAULT_COMPARTMENT_ID} envsubst < app.tmpl > app.yaml

echo "######delete application######"
kubectl delete  -f app.yaml

echo "######delete secret private-pem-secret######"
kubectl delete secret private-pem-secret

echo "######delete secret ocirsecret######"
kubectl delete secret ocirsecret

echo "######mvn package######"
mvn package

echo "######build docker image######"
docker build -t ${DOCKER_SERVER}/${DOCKER_REPO}/helidon-api-gateway .

echo "######login docker repo######"
docker login --username=${DOCKER_REPO}/${DOCKER_USERNAME} --password=${DOCKER_PASSWORD} ${DOCKER_SERVER}

echo "######push docker image######"
docker push ${DOCKER_SERVER}/${DOCKER_REPO}/helidon-api-gateway

echo "######create secret ocirsecret######"
kubectl create secret docker-registry ocirsecret --docker-server=${DOCKER_SERVER} --docker-username=${DOCKER_REPO}/${DOCKER_USERNAME} --docker-password=${DOCKER_PASSWORD} --docker-email=${DOCKER_EMAIL}
 
echo "######create secret private-pem-secret######"
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Secret
metadata:
  name: private-pem-secret
  namespace: default
type: Opaque
data:
  private.pem: `cat private.pem | base64 -w0`
EOF

echo "######deploy application######"
kubectl apply -f app.yaml
