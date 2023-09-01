# Wordle 3.0 

## Running Instructions

1. git clone this repository;
2. cd into the repository;
3. run the following commands in the terminal:
```
javac -d bin -cp "libs/*" -sourcepath src/main/java src/main/java/server/WordleServerMain.java
jar cfm compile/server/server.jar compile/server/ManifestServer.txt -C bin . -C libs .
java -jar compile/server/server.jar
```

4. once the server has started, run as many client as desired with:
```
javac -d bin -cp "libs/*" -sourcepath src/main/java src/main/java/client/WordleClientMain.java
jar cfm compile/client/client.jar compile/client/ManifestClient.txt -C bin . -C libs .
java -jar compile/client/client.jar
```
