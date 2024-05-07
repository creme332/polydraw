# Usage

## Run project

Navigate to the root directory of the project:

```bash
cd polydraw
```

Run the project:

```bash
mvn exec:java -Dexec.mainClass=com.github.creme332.App
```

> [!TIP]
> Alternatively, you can use an IDE with Maven configured to run the `App.java` file in the `src/main/java/com/github/creme332/App.java` folder.

## Generate jar
To create a jar file:

```bash
mvn clean compile assembly:single
```

## Generate code coverage report

To generate jacoco code coverage report:

```bash
mvn jacoco:prepare-agent test install jacoco:report
```