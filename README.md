# world-cup-qatar-java-ddd-cloud-run

Real World use case with an application using DDD and use Serverless deployment with Cloud Run and GCP.

```bash
gcloud compute firewall-rules create allow-alloydb-ingress-serverless \
    --network default \
    --allow tcp:5432 \
    --source-ranges 10.8.0.0/28 \
    --description "Allow VPC connector traffic to AlloyDB for Serverless Components"
```

## Deploy the app in Cloud Run with the standard way

Deploy the image

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/standard/deploy-cloud-run-image-standard.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_STANDARD="$SERVICE_NAME_STANDARD",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

Deploy the Service

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/standard/deploy-cloud-run-service-standard.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_STANDARD="$SERVICE_NAME_STANDARD",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

With Docker 

Build 

```bash
docker build -f cicd/standard/Dockerfile -t "$SERVICE_NAME_STANDARD" .
```

Run 

```bash
docker run -it \
    -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -e ALLOY_DB_CLUSTER_NAME="$ALLOY_DB_CLUSTER_NAME"  \
    -e ALLOY_DB_INSTANCE_NAME="$ALLOY_DB_INSTANCE_NAME" \
    -e ALLOY_DB_USERNAME="$ALLOY_DB_USERNAME" \
    -e ALLOY_DB_PASSWORD="$ALLOY_DB_PASSWORD" \
    -v $HOME/.config/gcloud:/root/.config/gcloud \
    $SERVICE_NAME_STANDARD
```

## Deploy the app in Cloud Run with native compilation and GraalVM

Deploy the Image

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=global \
    --config cicd/native-graalvm/deploy-cloud-run-image-graalvm.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_NATIVE_GRAAL_VM="$SERVICE_NAME_NATIVE_GRAAL_VM",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

Deploy the Service

```bash
gcloud builds submit \
    --project=$PROJECT_ID \
    --region=$LOCATION \
    --config cicd/native-graalvm/deploy-cloud-run-service-graalvm.yaml \
    --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME_NATIVE_GRAAL_VM="$SERVICE_NAME_NATIVE_GRAAL_VM",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
    --verbosity="debug" .
```

With Docker


Build

```bash
docker build -f cicd/native-graalvm/Dockerfile -t $SERVICE_NAME_NATIVE_GRAAL_VM .
```

Run

```bash
docker run -it \
    -p 8080:8080 \
    -e PROJECT_ID=$PROJECT_ID \
    -e LOCATION=$LOCATION \
    -e ALLOY_DB_CLUSTER_NAME="$ALLOY_DB_CLUSTER_NAME"  \
    -e ALLOY_DB_INSTANCE_NAME="$ALLOY_DB_INSTANCE_NAME" \
    -e ALLOY_DB_USERNAME="$ALLOY_DB_USERNAME" \
    -e ALLOY_DB_PASSWORD="$ALLOY_DB_PASSWORD" \
    -v $HOME/.config/gcloud:/root/.config/gcloud \
    $SERVICE_NAME_NATIVE_GRAAL_VM
```