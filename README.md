# BankApplication - Reto de Microservicios Spring Boot

## Resumen

Este proyecto es una solución para el reto técnico "**Aplicación Bancaria basada en Microservicios con Spring Boot**".  
La solución sigue estrictamente todas las directrices de desarrollo proporcionadas, y todo el trabajo fue realizado dentro del IDE integrado de la plataforma (**Echo**) sin usar herramientas externas para programar.

---

## Estructura de Microservicios

La aplicación está compuesta por dos microservicios independientes:

- **client** ➔ maneja Clientes (y Personas)
- **account** ➔ maneja Cuentas y Transacciones

Cada microservicio corre en:

- **Puerto del servicio de clientes:** `8001`
- **Puerto del servicio de cuentas:** `8000`

---

## Tecnologías Utilizadas

- Java 11
- Spring Boot 2.4.2
- Spring Data JPA
- Base de datos en memoria H2
- Maven
- MapStruct
- Lombok
- EchoAPI

---

## Funcionalidades Implementadas

- CRUD completo para Cliente, Cuenta y Transacción.
- Lógica de transacciones: actualización automática del saldo.
- Manejo de errores cuando el saldo es insuficiente.
- Reporte de Estado de Cuenta por Cliente y Rango de Fechas.
- Pruebas unitarias e integración (en `sampleTest.java`).

---

## Compilación del Proyecto

Para verificar que el proyecto compile correctamente, ejecutar:

```bash
cd Microservices/BankApplication/

# Compilar microservicio account
mvn -f account/pom.xml clean install -DskipTests

# Compilar microservicio client
mvn -f client/pom.xml clean install -DskipTests

---

## Endpoints de la API

POST   /api/clients
GET    /api/clients
GET    /api/clients/{id}
PUT    /api/clients/{id}
PATCH  /api/clients/{id}
DELETE /api/clients/{id}

POST   /api/accounts
GET    /api/accounts
GET    /api/accounts/{id}
PUT    /api/accounts/{id}
PATCH  /api/accounts/{id}
DELETE /api/accounts/{id}

POST   /api/transactions
GET    /api/transactions
GET    /api/transactions/clients/{clientId}/report?dateTransactionStart=YYYY-MM-DD&dateTransactionEnd=YYYY-MM-DD

---

## Colección Postman (Nota importante)

Las instrucciones solicitaban enviar una colección Postman.  
Sin embargo, **EchoAPI** (el cliente API integrado) **no permite exportar directamente**.  
Por tal motivo:

- Creé una cuenta externa en EchoAPI.
- Importé manualmente toda la colección.
- Procedí a exportarla y guardarla externamente.

✅ La colección está incluida en el repositorio de GitHub en el archivo:

```text
collection_bank_echoapi.json

---

## Estado Final
✅ La solución está completa, totalmente funcional y lista para ser evaluada.


