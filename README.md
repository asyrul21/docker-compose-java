# Some notes on using Docker Compose with Java Desktop App

## Notes:
Deploying GUI Java App on docker compose is very complicated, hence we are not going to use docker compose for the Java App.

Just build the JAR, and user can just double click it on their file explorers and it will run.

We do however, need to publish our AI Engine as a web service.

## 1. Creating the JAR file
1. Compile all your java classes
```
javac *.java
```

2. To run your Java app using command, navigate to parent directory, then run
```bash
java main.Main
```

3. In parent directory, create a manifest.txt file

ref: https://stackoverflow.com/questions/21223015/updating-jar-manifest-file-java-io-ioexception-invalid-manifest-format

https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html

```txt
Manifest-Version: 1.0
Created-By: AsyrulAhmad
Main-Class: main.Main
```

4. Create a jar file, which bundles all your packages in 1 file - using the Manifest.txt
```bash
jar cfm compiled.jar Manifest.txt MyPackage/*.class
```

5. Test run the jar
```bash
java -jar compiled.jar
```

# WARNING: Docker deployment below only works if your Java App does not use any GUI libraries. Otherwise, you will get this error:

Exception in thread "main" java.awt.HeadlessException: No X11 DISPLAY variable was set

To fix this error will complicate things and hence, we will deploy the Java app using the JAR file approach only.

## 1. Creating Dockerfile
Make sure your java version when compiling the jar file matches the java version in your Dockerfile
```bash
docker pull openjdk
```

```dockerfile
# select java image
FROM openjdk:13

# specify workinf directory
WORKDIR /usr/src/app/chatbot-poc/src/

# add the JAR file
ADD src/compiled.jar compiled.jar

# run command
CMD java -jar compiled.jar
```

## 2. Test the Docker Image
Check to see docker works with app

```bash
# build docker image
docker build -t "chatbot-poc" .
# --> Successfully built df3751a3cfa2

# view all images
docker images

# run image as container
docker run chatbot-poc

# view all running containers
docker ps
```

# Refereneces
https://dzone.com/articles/run-simple-jar-application-in-docker-container-1

https://stackoverflow.com/questions/29821045/cannot-find-symbol-error-in-cmd-but-not-in-ide/29821828

https://codefresh.io/docker-tutorial/create-docker-images-for-java/

http://archive.oreilly.com/oreillyschool/courses/java3/java314.html
