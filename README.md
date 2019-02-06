# hello-icp

- Create packge
```sh
$ mvn clean install
```

- Dockerfile
```sh
FROM dmoutinho/openjdk 
COPY . /usr/src/myapp 
WORKDIR /usr/src/myapp 
CMD java -Dserver.port=9080 -jar hello-icp-0.0.1-SNAPSHOT.jar
EXPOSE 9080
```

- Create image
```sh
$ docker build -t dmoutinho/hello-icp .
```

- Tag image
```sh
$ docker tag dmoutinho/hello-icp:latest mycluster.icp:8500/server/hello-icp
```

- Create new namespace server

- Login

- Push image
```sh
$ docker push mycluster.icp:8500/server/hello-icp:latest
```

- Deployment
```sh
{
  "apiVersion": "apps/v1",
  "kind": "Deployment",
  "metadata": {
    "name": "hello-icp",
    "namespace": "default",
    "resourceVersion": "920490",
    "labels": {
      "app": "hello-icp"
    },
    "annotations": {
      "deployment.kubernetes.io/revision": "1"
    }
  },
  "spec": {
    "replicas": 10,
    "selector": {
      "matchLabels": {
        "app": "hello-icp"
      }
    },
    "template": {
      "metadata": {
        "labels": {
          "app": "hello-icp"
        }
      },
      "spec": {
        "containers": [
          {
            "name": "hello-icp",
            "image": "mycluster.icp:8500/server/hello-icp",
            "ports": [
              {
                "containerPort": 9080,
                "protocol": "TCP"
              }
            ],
            "resources": {},
            "terminationMessagePath": "/dev/termination-log",
            "terminationMessagePolicy": "File",
            "imagePullPolicy": "IfNotPresent"
          }
        ],
        "restartPolicy": "Always",
        "terminationGracePeriodSeconds": 30,
        "dnsPolicy": "ClusterFirst",
        "securityContext": {},
        "schedulerName": "default-scheduler"
      }
    },
    "strategy": {
      "type": "RollingUpdate",
      "rollingUpdate": {
        "maxUnavailable": "25%",
        "maxSurge": "25%"
      }
    },
    "revisionHistoryLimit": 10,
    "progressDeadlineSeconds": 600
  }
}
```

- Service
```sh
{
  "apiVersion": "v1",
  "kind": "Service",
  "metadata": {
    "name": "hello-icp",
    "namespace": "default",
    "resourceVersion": "916416",
    "labels": {
      "app": "hello-icp"
    }
  },
  "spec": {
    "ports": [
      {
        "name": "web",
        "protocol": "TCP",
        "port": 9080,
        "targetPort": 9080,
        "nodePort": 31983
      }
    ],
    "selector": {
      "app": "hello-icp"
    },
    "clusterIP": "10.0.125.134",
    "type": "NodePort",
    "sessionAffinity": "None",
    "externalTrafficPolicy": "Cluster"
  }
}
```
