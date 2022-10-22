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

Existen dos roles con diferentes accesos permitidos:

El rol admin se vincula a los usuarios tipo Admin y el role de AccountHolder a los tipo AccountHolder.
#### Admin
- Endpoint1
  - Acceso al balance de todas las cuentas.
- Endpoint2
  - Modificación del balance de todas la cuentas. 

#### Account holder
- Endpoint1
  - Acceso al balance de sus propias cuentas. 
- Endpoint2
  - Transacciones


## API DOCUMENTATION

http://localhost:8080/swagger-ui/index.html


PENDING TESTS WITH SECURITY
- get_getBalanceByAccountId_WorksOK()

## Descripción de los tipos de cuentas

### Savings
- Interest rate: cómo máximo permitido 0.5. Por defecto es de 0.0025.
- Balance mínimo: rango entre 100 y 1000. Por defecto es 1000.
- PenaltyFee: 40

### CreditCards
- Límite de crédito: rango entre 100 y 100000. Por defecto es 100.
- Interest rate: rango entre 0.1 y 0.2. Por defecto es 0.2
- PenaltyFee: 40

### CheckingAccounts
- Si el titular de la cuenta es menor de 24 años se crea una "StudentCheckingAccount" automáticamente, de lo contrario se crea una CheckingAccount general.
- Balance mínimo: 250.
- Fee de mantenimiento mensual: 12.
- PenaltyFee: 40

Cuando el balance de una cuenta cae por debajo del balance mínimo se 
deduce el penaltyFee automáticamente cada vez que se usa (transacciones, ver balance). 

## Aclaraciones
El método checkBalance de Account, aplica el penalty fee cada vez que el usuario accede a su balance y 
aplica el interest rate en savings anual. 
el método withdraw aplica el penalty fee en caso necesario cada vez que se retira dinero de la cuenta.