# id_text_extractor
extractor de texto de cedulas colombianas

1. La solución fue creada en VSCode, utilizando Spring Boot 3.2.12 y Java 17, junto con React y Node.js v22.14.0. La base de datos utilizada fue MySQL v. 8.0. Los puertos usados son: backend 8080, frontend 3000, base de datos 3306. En cuanto a Document intelligence se usó su versión 2024-11-30 (4.0) y se creó un modelo personalizado el cual se entrenó para admitir cedulas Colombianas.

2. La aplicación puede desplegarse en Spring Boot directamente, sin necesidad de utilizar un servidor externo.

3. Para ejecutar la aplicación primero ejecuta en MySQL el script compartido llamado Script_id_reader necesitarás tener el esquema: iddata y la tabla id_data, para que los datos puedan guardarse correctamente. Ahora descarga el archivo JAR que se encuentra en la carpeta target, guárdalo en una carpeta local, abre VSCode y, usando la terminal, navega hasta la carpeta donde guardaste el archivo JAR.
Luego, ejecuta el siguiente comando:

	java -jar id_reader-3.jar

Después, accede al navegador y escribe en la barra de direcciones:

http://localhost:8080/
La aplicación debería funcionar, pero si por alguna razón no lo hace, podría deberse a dos posibles causas:
a. El puerto ya está en uso, por lo que debes finalizar el proceso que lo está ocupando.
b. La aplicación podría no estar reconociendo la URL; en ese caso, intenta con la siguiente:


http://localhost:8080/index.html

4. Si necesitas descargar todo el proyecto y ejecutarlo, solo ten en cuenta las versiones que utilicé para construir la aplicación.
