
# Helidon Api Gateway

This example implements a simple Hello World REST service using MicroProfile

## Prerequisites

1. Maven 3.5 or newer
2. Java SE 11
3. Docker 17 or newer (if you want to build and run docker images)
4. Kubectl 1.7.4 or newer for deploying to Kubernetes

Verify prerequisites
```
java -version
mvn --version
docker --version
kubectl version --short
```

## Build

### Step.1 
Copy your oci private.pem to override sample private.pem

```sh
cp YOUR_PRIVATE_PEM_FILE ./private.pem
```

### Step.2
Modify deploy2k8s.sh to input your information.

```sh
vi deploy2k8s.sh
```

### Step.3
Execute deploy2k8s.sh

```sh
./deploy2k8s.sh
```

### Step.4
Get your node ip and serivce port(default is 30080)

```sh
kubectl get nodes
```

```sh
kubectl get service
```

### Step.5
Try

```
curl YOUR_NODE_IP:YOUR_SERVICE_PORT/oci/headers -X POST
```