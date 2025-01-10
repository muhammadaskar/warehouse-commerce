gcloud auth login
gcloud config set project neon-framing-440111
gcloud auth configure-docker asia-southeast2-docker.pkg.dev
# Tag and push image to registry

## tag image warehouse and push to registry
echo -e "tag image warehouse and push to registry"
docker tag com.ecommerce.app/warehouse.service:1.0-SNAPSHOT asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/warehouse.service:1.0-SNAPSHOT
docker push asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/warehouse.service:1.0-SNAPSHOT

## tag image user and push to registry
echo -e "tag image user and push to registry"
docker tag com.ecommerce.app/user.service:1.0-SNAPSHOT asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/user.service:1.0-SNAPSHOT
docker push asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/user.service:1.0-SNAPSHOT

## tag image product and push to registry
echo -e "tag image product and push to registry"
docker tag com.ecommerce.app/product.service:1.0-SNAPSHOT asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/product.service:1.0-SNAPSHOT
docker push asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/product.service:1.0-SNAPSHOT

## tag image order and push to registry
echo -e "tag image order and push to registry"
docker tag com.ecommerce.app/order.service:1.0-SNAPSHOT asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/order.service:1.0-SNAPSHOT
docker push asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/order.service:1.0-SNAPSHOT


## tag image payment and push to registry
echo -e "tag image payment and push to registry"
docker tag com.ecommerce.app/payment.service:1.0-SNAPSHOT asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/payment.service:1.0-SNAPSHOT
docker push asia-southeast2-docker.pkg.dev/neon-framing-440111-n3/com-warehouse-app/payment.service:1.0-SNAPSHOT
