npm run build

rm -rf _s3-upload
mkdir _s3-upload
cp index.html _s3-upload/
mkdir _s3-upload/src
mkdir _s3-upload/src/assets
mkdir _s3-upload/dist
cp -R dist/ _s3-upload/dist/
cp -R src/assets/ _s3-upload/src/assets/

export AWS_PROFILE=sandbox
aws --region=us-west-2 s3 rm s3://phx-paywall-outlook$1 --recursive
aws --region=us-west-2 s3 cp _s3-upload/ s3://phx-paywall-outlook$1 --recursive
