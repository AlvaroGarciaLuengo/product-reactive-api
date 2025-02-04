# Product Reactive API

Este es un servicio API reactivo basado en Spring Boot que gestiona productos, utiliza R2DBC para la conexión con la base de datos PostgreSQL y Redis para almacenamiento en caché. Además, se utilizan migraciones de base de datos con Liquibase.

## Requisitos

Antes de ejecutar la aplicación, asegúrate de tener las siguientes herramientas instaladas:

- **Docker**: [Instalar Docker](https://www.docker.com/get-started)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)
- **Maven**: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

## Pasos para ejecutar la aplicación

### 1. Clonar el repositorio

Si aún no tienes el repositorio, clónalo usando Git:

```bash
git clone https://github.com/alvarogarcialuengo/product-reactive-api.git
cd product-reactive-api
```
### 2. Crear la imagen Docker
Primero compilaremos la aplicación (Así comprobaremos que los test se ejecutan correctamente):
```bash
mvn clean install
```
Para construir la imagen Docker de la aplicación, ejecuta el siguiente comando desde la raíz del proyecto (donde se encuentra el Dockerfile):

```bash
docker build -t product-reactive-api .
```
Este comando creará la imagen Docker con el nombre product-reactive-api.

### 3. Configurar Docker Compose
Asegúrate de que tienes configurado el archivo docker-compose.yml para levantar los servicios que necesita la aplicación. Aquí se definen los contenedores de la aplicación (app), PostgreSQL (postgres) y Redis (redis).

### 4. Levantar los contenedores
Una vez que la imagen Docker se ha construido, puedes levantar todos los servicios (base de datos, Redis, y la aplicación) usando Docker Compose. Ejecuta:
Adicionalmente se utiliza liquibase para crear el modelo de BBDD.
```bash
docker-compose up --build
```
#### Esto hará lo siguiente:

Construirá y levantará los contenedores definidos en el archivo docker-compose.yml.
Iniciará la aplicación y hará la migración de base de datos utilizando Liquibase.
(La ejecucuión del contenedor de Liquibase es lenta.)

### 5.Acceder a la aplicación
Una vez que los contenedores estén levantados, podrás acceder a la API en:

#### Puerto de la API: 
- http://localhost:8080
#### Interfaz Swagger: 
- http://localhost:8080/swagger-ui.html
### 7. Detener los contenedores
Para detener los contenedores de la aplicación, ejecuta:

```bash
docker-compose down
```
### 8. Importar coleccion en postman
Para ello deberemos ir a:
- http://localhost:8080/v3/api-docs

Aqui copiaremos el JSON y lo importaremos dentro de POSTMAN.

tambien tenemos la opcion importar el JSON que hay en el proyecto en la raiz:
- OpenAPI definition.postman_collection.json

### 9. Comandos útiles
Construir la imagen de nuevo:

```bash
docker build -t product-reactive-api .
```

Ver logs de la aplicación:

```bash
docker logs product-reactive-api
```
Acceder al contenedor de la base de datos PostgreSQL:

```bash
docker exec -it postgres_local psql -U user -d products_database
```
Estructura del Proyecto
- src/: Código fuente de la aplicación.
- src/main/resources/db/changelog/: Archivos de migración de base de datos Liquibase.
- pom.xml: Archivo de configuración de Maven.