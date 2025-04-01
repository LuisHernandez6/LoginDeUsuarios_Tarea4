### LoginDeUsuarios_Tarea4 
### Luis Alonso Hernandez Perdomo / 2023-1804  
## Video: https://youtu.be/qk2AJ06DhxY

Login de usuarios el cual puede conectarse a una BD de MySQL y registrar, actualizar o eliminar los usuarios registrados. 

La conexión a la BD se hace a través del drive JDBC **Connector/J** (incluido en el codigo fuente). Si quiere conectarse con su BD debe de colocar la información de esta en los atributos correspondientes en la clase **ConexionMySQL**. Una vez se establezca la conexión, se creara la tabla **usuarios** en la BD si esta no existe, en el caso de que no se pueda establecer conexión con la BD el programa se podra seguir utilizando pero los usuarios que se registren tan solo permanecerán en la memoria temporal del programa.

Se aplicaron los pilares de la programación orientada a objetos:

-**Abstracción:** Se creó una interfaz la cual fue implementada posteriormente, así aplicando la abstracción a través de interfaces.  
-**Encapsulamiento:** Las clases tienen sus atributos privados para aislar estos y poder gestionar el acceso a ellos.  
-**Herencia:** Se heredó de la clase JFrame para las distintas ventanas.  
-**Polimorfismo:** Se hizo uso de múltiples constructores y métodos con parámetros distintos, así aplicando el polimorfismo de sobrecarga.  

También se hizo uso de las bibliotecas **Swing** y **Awt** para el desarrollo de las interfaces gráficas de usuario, además, se aplicó el patrón de diseño **Singleton**, siendo utilizado con, por ejemplo, la clase encargada de realizar las conexiones con la BD de MySQL. También se creó un **CRUD** para poder enviarle queries a la BD y poder trabajar con la tabla **usuarios**.
