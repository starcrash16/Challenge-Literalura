Literalura

Literalura es una aplicación hecha en Java que permite consultar información de libros a través de la API de Gutenberg y almacenarlos en una base de datos local con PostgreSQL 17.

El objetivo principal es combinar el consumo de API y el almacenamiento en bases de datos dentro de un mismo proyecto.

🧰 Tecnologías utilizadas

Java 17+

API REST: Gutendex

Base de datos: PostgreSQL 17

Librerías externas:

Gson → Parseo de JSON

PostgreSQL Driver

Spring Data JPA

🧪 Funcionalidades del Menú

Al ejecutar la aplicación, se muestra un menú interactivo en consola con las siguientes opciones:

Buscar libro en el API por título.

Listar libros de la base de datos.

Listar autores de la base de datos.

Listar autores vivos en un determinado año.

Listar libros de la base de datos por idioma.

Finalizar programa.

🗃️ Base de Datos

Se utiliza PostgreSQL 17 como almacenamiento local.

Tabla creada:

libros

id

titulo

lenguajes

autores

anio_nacimiento

anio_muerte

✅ Requisitos para correr el proyecto

Java 17 o superior

Cualquier IDE con soporte para Maven o Gradle (ej: IntelliJ IDEA)

Conexión a internet para consumir la API de Gutendex

Base de datos PostgreSQL instalada y configurada correctamente

🚀 Cómo ejecutar

Clona este repositorio:

git clone https://github.com/tuusuario/Literalura.git
cd Literalura


Abre el proyecto en tu IDE de preferencia.

Configura la conexión a la base de datos:

Edita la clase LibroDB con los datos de tu PostgreSQL (usuario, contraseña, puerto, etc.).

Compila y ejecuta el archivo Demo.java para iniciar el programa.
