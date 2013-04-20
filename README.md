TwitterLogin
============

Steps to connect with twitter using oauth authentication

1. Crear una aplicacion en dev.twitter.com y cambiar los siguientes parametros:
	- Consumer key
	- Consumer secret
2. Cambiar la ruta de redireccionamiento a la app en el archivo manifiesto, para este ejemplo en particular utiliza: kunfoodtesting://kerpie.com
3. Descargar twitter4j y aniadirlo a la carpeta libs en el proyecto
4. Se usan varios hilos para la conexion con twitter, para este caso se utiliza asynctask, procurar mostrar algun mensaje que se esta conectando de modo que el usuario sepa que la aplicacion no se ha detenido 
5. Se utiliza un primer hilo(TwitterLogin) para generar el token de peticion y pueda lanzar la autenticacion por navegador
6. Una vez terminada esta parte y redireccione a la app, los datos obtenidos se trabajan en onNewIntent() y se ejecuta un nuevo hilo para obtener los datos del usuario que se ha autenticado. FYI: La API de twitter no regresa el correo del usuario, politicas de seguridad.
7. En el segundo hilo se almacenan los datos en las preferencias compartidas, si quieres mas datos puedes utilizar el objeto twitter(a traves de sus getters), y al finalizar inicia la siguiente actividad.
