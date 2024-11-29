# Alianza-interview-backend

### Descripción:
Este proyecto es la parte del backend de la prueba técnica para ingresar a Alianza. Está desarrollado
en Java 17 y Spring Boot 3.4.0. A este proyecto se le da la visión de ser implementado en una arquitectura de microservicios, es por eso que en la entidad, su id no es de tipo Long, si no UUID. Por otro lado, consta de un crud el cuál nos permitará crear, listar, actualizar y eliminar clientes haciendo respectivas validaciones y usando diferentes estrategias para que el proyecto funcione de la mejor manera.

### A tener en cuenta:
* Para correr este proyecto se necesita postgresDB
y solamente es necesario reemplazar los valores de ***{DB_NAME}***, ***{DB_USERNAME}*** y ***{DB_PASSWORD}*** del archivo application.yml, ya que se integró un sistema de versionamiento de base de datos (Flyway) el cual automáticamente corre la migración inicial

### Pruebas
* Se hicieron diferentes pruebas unitarias y funcionales, las cuales usando Mockito y MockMvc dieron resultados positivos