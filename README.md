#   Hello ICP

- Create hello namespace
```sh
$ docker tag dmoutinho/hello-icp:latest mycluster.icp:8500/server/hello-icp
```

- Tag image
```sh
$ docker tag dmoutinho/hello-icp:latest mycluster.icp:8500/hello/hello-icp
```

- Login
```sh
$ docker login mycluster.icp:8500
```

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

- Ingress
```sh
{
  "apiVersion": "extensions/v1beta1",
  "kind": "Ingress",
  "metadata": {
    "name": "hello-icp",
    "namespace": "default",
    "resourceVersion": "71355",
    "annotations": {
      "icp.management.ibm.com/rewrite-target": "/",
      "kubernetes.io/ingress.class": "ibm-icp-management"
    }
  },
  "spec": {
    "rules": [
      {
        "http": {
          "paths": [
            {
              "path": "/hello-icp",
              "backend": {
                "serviceName": "hello-icp",
                "servicePort": 9080
              }
            }
          ]
        }
      }
    ]
  }
}
```

# Docker Hub Flow
<p align="center">
  <img src="https://docs.google.com/drawings/d/e/2PACX-1vTMJaz2F3zNTzpcvSBMegVJFsxC3I66xfwET_xpV55I9hv2Zx8-iaui1CjW47Z0pFYP3MJB0JcLwqBn/pub?w=606&h=174">
</p>

# Gitlab Flow
<p align="center">
  <img src="https://docs.google.com/drawings/d/e/2PACX-1vTLeM2VcsZdQY-hQUfXBzVhD6AiGieBNaa_Bqo1i_B0uymrf5PmEMj5QaI-l85cu6r6Ink1tzPmA_YZ/pub?w=796&h=188">
</p>
