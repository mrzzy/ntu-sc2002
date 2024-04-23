# ntu-sc2002
NTU SC2002 Project: Fastfood Ordering and Management System (FOMS).
- [Class Diagram](docs/class_diagram.png)
- [Javadoc Documentation](https://mrzzy.github.io/ntu-sc2002/)

## Installation
1. Ensure Java JDK 21 & Maven are installed first.
2. Clone this repository.
3. Build 
```
mvn compile
```

## Usage
Run FOMS:
```sh
mvn exec:exec
```

- **Program Arguments** Open pom.xml and add `<argument>` tags to `exec-maven-plugin`'s `<arguments>` tag. Pass `-h` as an argument to show usage information.

## Contributing
- **Project Structure** See [Maven Docs](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) on project layout.
- **Tests** Run unit tests with `mvn test`
- **Formatting** Apply code formatting with `mvn spotless:apply`
- **Documentation** Generate [Javadoc Documentation](https://mrzzy.github.io/ntu-sc2002/) with `mvn javadoc:javadoc`

Before pushing your stuff:
1. Write javadoc docstrings to document your code.
2. Write tests for your code.
3. Format your code.
4. Push.
