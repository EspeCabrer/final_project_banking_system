# PROYECTO FINAL BOOTCAMP BACKEND IRONHACK
## Banking system

Proyecto realizado con spring boot dónde se crea una API simple de un sistema de bancos incorporando seguridad básica y testing.
Cómo dependencias a destacar se han usado:
- spring-boot-starter-web
- spring-boot-devtools
- mysql-connector-java
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-security
- lombok
- jackson-datatype-jsr310
- springdoc-openapi-ui

- spring-boot-starter-test
- spring-security-test

### INICIO
Al iniciar la aplicación se crean automáticamente usando CommandLineRunner:

#### Roles
- ADMIN
- ACCOUNT_HOLDER

#### Usuarios
- 1 User tipo Admin con role "ADMIN"
  - Nombre: maria
  - Contraseña: password*
  
- 2 User accountHolder con role "ACCOUNT_HOLDER"
  - User1
    - Nombre: pepe
    - Contraseña: password*
  - User2
    - Nombre: antonia
    - Contraseña: password*
  
*Se ha puesto la misma contraseña a los tres usuarios para simplificar.

#### Cuentas
- 1 Cuenta de Savings vinculada al user1
  - Balance: 3000
  - Secret Key: "secretKey"*
- 1 Cuenta de Savings vinculada al user2
  - Balance: 1500
  - Secret Key: "secretKey"*

*Se ha puesto la misma contraseña a las dos cuentas para simplificar.

### FUNCIONAMIENTO

Existen dos roles, el rol admin se vincula a los usuarios de typo admin y tienen acceso a los siguientes endpoints:
- Endpoint1
- Endpoint2

El rol account holder se vincula a los usuarios de typo accountHolder y tienen acceso a los siguientes endpoints:
- Endpoint1
- Endpoint2


## API DOCUMENTATION

http://localhost:8080/swagger-ui/index.html


PENDING TESTS WITH SECURITY
- get_getBalanceByAccountId_WorksOK()

