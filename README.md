# Escuela Colombiana de Ingeniería Julio Garavito

## Arquitecturas de Software (ARSW)

### :pushpin: Daniel Felipe Hernández Mancipe

<br/>

# :hammer_and_wrench: Concurrent Web Server

[![Deployed to Heroku](https://www.herokucdn.com/deploy/button.png)](https://arsw-concurrent-webserver.herokuapp.com/)

Implementación propia de un servidor web concurrente usando pools de hilos.

Consulta sin especificar recurso:

![](../media/1.png?raw=true)

![](../media/2.png?raw=true)

Consulta a recurso html:

![](../media/3.png?raw=true)

![](../media/3.2.png?raw=true)

Consulta a recurso css y a recurso inexistente:

![](../media/4.png?raw=true)

![](../media/4.2.png?raw=true)

Consulta a recurso js y a a recurso jpg:

![](../media/5.png?raw=true)

![](../media/5.2.png?raw=true)

## Getting Started

### Prerequisites

- Java >= 11.x
- Maven >= 3.x
- Git >= 2.x
- JUnit 4.x

### Installing

Simplemente clone el repositorio:

```
git clone https://github.com/danielhndz/ARSW-concurrent-web-server.git
```

Luego compile el proyecto con maven:

```
mvn compile
```

Si salió bien, debería tener un **BUILD SUCCESS** verde.

### Using

```
mvn exec:java -Dexec.mainClass="edu.escuelaing.arsw.labs.net.HttpServer"
```

El descripción del proyecto se encuentran casos de uso.

## Built With

- [Maven](https://maven.apache.org/) - Dependency Management
- [Git](https://git-scm.com/) - Version Management
- [JUnit4](https://junit.org/junit4/) - Unit testing framework for Java

## Design Metaphor

- Autor: Daniel Hernández
- Última modificación: 21/06/2022

### Class Diagram

La clase [HttpServer](/src/main/java/edu/escuelaing/arsw/labs/concurrentServer/HttpServer.java) es la implementación del servidor web concurrente con pools de hilos, la clase [RequestProcessor](/src/main/java/edu/escuelaing/arsw/labs/concurrentServer/RequestProcessor.java) implementa la interfaz [Runnable](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Runnable.html) y procesa cada solicitud de cada cliente (permitiendo la concurrencia) y finalmente la clase [FilesReader](/src/main/java/edu/escuelaing/arsw/labs/concurrentServer/FilesReader.java) es la que se encarga de extraer los recursos solicitados.

![](../media/class_diagram.png?raw=true)

## Authors

- **Daniel Hernández** - _Initial work_ - [danielhndz](https://github.com/danielhndz)

## License

This project is licensed under the GPLv3 License - see the [LICENSE.md](LICENSE.md) file for details

## Javadoc

Para generar Javadocs independientes para el proyecto en la carpeta `/target/site/apidocs` ejecute:

```
mvn javadoc:javadoc
```
