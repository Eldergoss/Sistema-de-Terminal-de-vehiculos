# Sistema de Control de Terminal 02

API REST para gestionar las unidades de transporte de una terminal, registrar sus entradas y salidas, administrar destinos y consultar el historial de movimientos.

## 1. Descripción

Sistema de Control de Terminal 02 es una aplicación backend desarrollada en Java que permite llevar un control básico de las unidades que ingresan y salen de una terminal.

El sistema utiliza una API REST para recibir solicitudes y devolver respuestas en formato JSON. La información se almacena en una base de datos SQLite.

Este proyecto corresponde a una versión MVP (Producto Mínimo Viable), enfocada en las funcionalidades esenciales de operación.

## 2. Tecnologías utilizadas

- **Java 25:** lenguaje de programación.
- **Maven:** gestión de dependencias y construcción del proyecto.
- **Jooby 4.5.4:** framework para desarrollar la API REST.
- **SQLite:** sistema de gestión de base de datos.
- **Jackson:** procesamiento de solicitudes y respuestas JSON.

## 3. Arquitectura

El proyecto utiliza una arquitectura por capas que separa las responsabilidades de cada componente.

```text
Cliente HTTP
     |
     v
    App
     |
     v
 Controller
     |
     v
  Service
     |
     v
 Repository
     |
     v
   SQLite
```

### Responsabilidades

| Capa | Responsabilidad |
|---|---|
| App | Inicialización y configuración de rutas HTTP. |
| Controller | Recibir solicitudes y devolver respuestas. |
| Service | Aplicar las reglas de negocio y validaciones. |
| Repository | Ejecutar las consultas SQL. |
| Model | Representar las entidades del sistema. |
| Database | Gestionar las conexiones con SQLite. |

## 4. Funcionalidades

### Unidades

- Registrar unidades.
- Consultar todas las unidades.
- Modificar la placa de una unidad.
- Validar datos y evitar duplicados.

### Movimientos

- Registrar entradas.
- Registrar salidas.
- Generar automáticamente la fecha y hora del movimiento.
- Validar la secuencia de entradas y salidas.
- Consultar el historial general.
- Consultar el historial de una unidad específica.

### Destinos

- Registrar destinos.
- Consultar los destinos registrados.

### Historial

- Consultar todos los movimientos.
- Consultar los movimientos asociados a una unidad.
- Ordenar los registros desde el más reciente al más antiguo.

## 5. Endpoints disponibles

La API utiliza los siguientes endpoints:

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/` | Comprobar que la aplicación funciona. |
| GET | `/db-test` | Comprobar conexión con SQLite y claves foráneas. |
| GET | `/unidades` | Consultar unidades. |
| POST | `/unidades` | Registrar una unidad. |
| PUT | `/unidades/{id}/placa` | Actualizar placa. |
| POST | `/movimientos` | Registrar entrada o salida. |
| GET | `/movimientos` | Consultar historial general. |
| GET | `/movimientos/unidad/{id}` | Consultar historial por unidad. |
| POST | `/destinos` | Registrar un destino. |
| GET | `/destinos` | Consultar destinos. |

## 6. Ejemplos de solicitudes

### Registrar una unidad

`POST /unidades`

```json
{
    "numero": "U001",
    "placa": "M67890"
}
```

### Actualizar una placa

`PUT /unidades/1/placa`

```json
{
    "placa": "M12345"
}
```

### Registrar una entrada

`POST /movimientos`

```json
{
    "idUnidad": 1,
    "tipoMovimiento": "ENTRADA",
    "idDestino": null
}
```

### Registrar una salida

`POST /movimientos`

```json
{
    "idUnidad": 1,
    "tipoMovimiento": "SALIDA",
    "idDestino": 1
}
```

La fecha y hora son generadas automáticamente por el backend. El cliente no necesita enviarlas.

### Registrar un destino

`POST /destinos`

```json
{
    "nombre": "Managua"
}
```

## 7. Reglas de negocio

El sistema aplica las siguientes reglas para los movimientos:

1. Una unidad debe estar registrada antes de generar movimientos.
2. Solo se permiten movimientos de tipo `ENTRADA` o `SALIDA`.
3. No se permite registrar dos entradas consecutivas.
4. No se permite registrar dos salidas consecutivas.
5. No se permite registrar una salida si no existe una entrada previa.
6. La fecha y hora se generan automáticamente al registrar el movimiento.
7. Los destinos son opcionales al registrar un movimiento.

## 8. Base de datos

El proyecto utiliza SQLite para almacenar la información.

Las tablas principales utilizadas por el MVP son:

- `unidades`: información de las unidades de transporte.
- `movimientos`: registro de entradas y salidas.
- `destinos`: destinos asociados a los movimientos.

También se conservan las tablas `usuarios` y `auditoria` de la estructura original de la base de datos, aunque no forman parte de las funcionalidades activas del MVP.

Las relaciones entre tablas utilizan claves foráneas para mantener la integridad referencial.

## 9. Ejecución del proyecto

### Requisitos previos

- Java JDK 25.
- Apache Maven.
- Acceso a una terminal.

### Compilar el proyecto

Desde el directorio raíz:

```bash
mvn clean compile
```

### Ejecutar la aplicación

Utilizar el comando de ejecución configurado en el proyecto Maven.

Por defecto, la API se encuentra configurada para atender solicitudes en:

```text
http://localhost:8080
```

## 10. Alcance del MVP

Esta versión está enfocada exclusivamente en el control básico de unidades y sus movimientos.

No incluye:

- Interfaz gráfica.
- Sistema de autenticación.
- Usuarios y roles operativos.
- Gestión de permisos.
- Auditoría de operaciones.
- Reportes estadísticos.
- Gestión de equipos o partidos.

## 11. Estado del proyecto

**Versión:** MVP 1.0

**Estado:** Funcionalidades principales implementadas y verificadas.

El proyecto cuenta con pruebas manuales de los endpoints, validaciones de movimientos, consultas de historial y conexión con SQLite.

---

*Sistema de Control de Terminal 02 — API REST desarrollada en Java.*
