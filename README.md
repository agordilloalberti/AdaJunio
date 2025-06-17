----
Nombre del proyecto: GosuApps
----

----
Idea del projecto:
----

Se busca crear una api usando springboot Security para la gestión de trabajadores y proyectos.

----
Tablas:
----

Existiran tres tablas:

**Usuarios**

Sera las personas registradas encargadas de añadir, administrar y terminar proyectos

**Proyectos**

Seran los proyectos los cuales deben completar los trabajadores

**Asignaciones**

Esta tabla contiene los trabajadores y los projectos a los que pertenecen

----
Endpoints:
----

***Tabla: Usuarios***

Se creran 6 endpoints.

  1-GetAll: se envia una peticion get son argumentos y se reciben todos los usuarios registrados.

  2-GetByID: se envia una petición get con el id como parametro y se recibe el usuario al que le corresponda o un código 404 si no es encontrado, genera una ResponseStatusExceptión.

  3-Login: se envia una petición post con un JSON para los datos del usuario y se devuelve el token de usuario o un código 404 si el usuario no existe, lanza una NotFoundException.

  4-create: se envia una petición post con un JSON para los datos del usuario y se devuelve un codigo 201 o un 400 si no ocurre, lanzando una IllegalArgumentException

  5-update: se envia una petición put con un id como parametro y un JSON con los nuevos datos se devuleve un codigo 200 si es correcto y si no un código 400, lanzando una IllegalArgumentException

  6-delete: se envia una petición delete con un id como parametro y se devuelve un código 204 si es correcto y un código 400 si falla, lanzando una IllegalArgumentException
***Tabla: Proyectos***













  
