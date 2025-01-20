# Docker login
#docker login -u dockermuhammadaskar

services=("warehouse" "user" "product" "order" "payment")

for service in "${services[@]}"; do
  echo -e "tag image $service and push to registry"
  docker tag com.ecommerce.app/$service.service:1.0-SNAPSHOT dockermuhammadaskar/$service.service:1.0-SNAPSHOT
  docker push dockermuhammadaskar/$service.service:1.0-SNAPSHOT
done