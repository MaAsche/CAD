rm -rf backend/build/
./backend/gradlew bootJar -p ./backend
echo "Built backend"

cd frontend
rm -rf build
rm -rf public/images
npm install
npm run-script build
cd ..
echo "Built Frontend"

mkdir deploy -p
rm -rf deploy/*
mkdir deploy/config -p
cp backend/build/libs/backend-0.0.1-SNAPSHOT.jar deploy/app.jar
cp config/credentials deploy/config/credentials
cp frontend/build deploy/static -r
