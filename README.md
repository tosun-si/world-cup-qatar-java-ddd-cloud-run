# world-cup-qatar-java-ddd-cloud-run

Real World use case with an application using DDD and use Serverless deployment with Cloud Run and GCP.


## Deploy the app in Cloud Run

```bash
gcloud builds submit \
  --project=$PROJECT_ID \
  --region=$LOCATION \
  --config deploy-app-cloud-run.yaml \
  --substitutions _REPO_NAME="$REPO_NAME",_SERVICE_NAME="$SERVICE_NAME",_IMAGE_TAG="$IMAGE_TAG",_SERVICE_ACCOUNT="$SERVICE_ACCOUNT" \
  --verbosity="debug" .
```