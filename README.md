# SicksLobby - Sistema de Gestion de Citas Medicas

## Concepto

SicksLobby es un sistema de gestion de citas medicas desarrollado con **Spring Boot** y **Firebase**. Su objetivo principal es digitalizar y optimizar el proceso de administracion de consultas medicas, permitiendo a clinicas y centros de salud gestionar de manera eficiente la relacion entre pacientes y especialistas.

El sistema proporciona una API REST completa que permite:

- **Gestion de Pacientes**: Registro, consulta, actualizacion y eliminacion de pacientes con informacion personal detallada (nombre, edad, estado civil, telefono, ocupacion, etc.) y clasificacion automatica por grupo etario.
- **Gestion de Especialistas**: Administracion del cuerpo medico con datos de contacto y especialidad asignada.
- **Gestion de Citas**: Creacion, consulta, modificacion y cancelacion de citas medicas, vinculando pacientes con especialistas. Las citas se almacenan como sub-colecciones dentro de cada especialista en Firestore, lo que permite una consulta eficiente por profesional.
- **Autenticacion**: Integracion con **Firebase Auth** para verificar tokens de usuario y gestionar el acceso seguro a la plataforma.

La arquitectura utiliza **Firestore** como base de datos NoSQL, aprovechando su modelo de documentos y sub-colecciones para organizar los datos de forma jerarquica y escalable.

---

## Estructura del Proyecto

```
SicksLobby/
├── src/
│   ├── main/
│   │   ├── java/com/example/sickslobby/
│   │   │   ├── Config/
│   │   │   │   ├── FirebaseConfig.java        # Inicializacion de Firebase y Firestore
│   │   │   │   └── Security.java              # Configuracion de Spring Security
│   │   │   ├── Controllers/
│   │   │   │   ├── AuthController.java        # Endpoints de autenticacion (/auth/verify, /auth/users)
│   │   │   │   ├── CitasController.java       # Endpoints de citas (CRUD)
│   │   │   │   └── PostController.java        # Endpoints de pacientes y especialistas (CRUD)
│   │   │   ├── Dto/
│   │   │   │   ├── CitasDTO.java              # DTO para datos de citas
│   │   │   │   ├── CrearCitaDTO.java          # DTO para creacion de citas (combina cita + IDs)
│   │   │   │   ├── EspecialistaDTO.java       # DTO para especialistas
│   │   │   │   └── PacienteDTO.java           # DTO para pacientes (con logica de grupo etario)
│   │   │   ├── Exception/
│   │   │   │   └── GlobalExceptionHandler.java # Manejo global de errores y validaciones
│   │   │   ├── Services/
│   │   │   │   ├── CitasServicesI.java        # Interface del servicio de citas
│   │   │   │   ├── ServicesInterface.java     # Interface generica para CRUD (T)
│   │   │   │   ├── SharedMethods.java         # Metodos compartidos (acceso a Firestore, validaciones)
│   │   │   │   └── use/
│   │   │   │       ├── CitasServices.java     # Implementacion de servicio de citas
│   │   │   │       ├── EspecialistasServices.java # Implementacion de servicio de especialistas
│   │   │   │       └── PacientesServices.java     # Implementacion de servicio de pacientes
│   │   │   └── SicksLobbyApplication.java     # Punto de entrada de la aplicacion
│   │   └── resources/
│   │       ├── firebase/
│   │       │   └── serviceAccountKey.json     # Credenciales de Firebase (NO incluir en git)
│   │       └── application.properties         # Configuracion de la aplicacion
│   └── test/
│       └── java/com/example/sickslobby/
│           └── SicksLobbyApplicationTests.java # Tests de la aplicacion
├── build.gradle.kts                           # Dependencias y configuracion de Gradle
├── settings.gradle.kts                        # Configuracion del proyecto
├── Dockerfile                                 # Imagen Docker para despliegue
├── .gitignore                                 # Archivos excluidos del repositorio
└── README.md                                  # Documentacion del proyecto
```

---

## Modelo de Datos en Firestore

```
Firestore
├── Pacientes/
│   └── {pacienteId}/
│       ├── nombre: string
│       ├── apellido: string
│       ├── edad: number
│       ├── estadoCivil: string
│       ├── sexo: string
│       ├── grupoEdad: string (calculado automaticamente)
│       ├── telefono: string
│       └── ocupacion: string
│
├── Especialistas/
│   └── {especialistaId}/
│       ├── nombre: string
│       ├── apellido: string
│       ├── edad: number
│       ├── especialidad: string
│       │
│       └── Citas/ (sub-coleccion)
│           └── {citaId}/
│               ├── fechaCita: string
│               ├── estado: string
│               ├── pacienteId: string
│               └── nombrePaciente: string
```

---

## Endpoints de la API

### Pacientes y Especialistas (`/post`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/post/ListPaciente` | Lista todos los pacientes |
| POST | `/post/addPaciente` | Registra un nuevo paciente |
| PUT | `/post/editPaciente?id={id}` | Actualiza un paciente |
| DELETE | `/post/deletePaciente?id={id}` | Elimina un paciente |
| GET | `/post/ListEspecialista` | Lista todos los especialistas |
| POST | `/post/addEspecialista` | Registra un nuevo especialista |
| PUT | `/post/editEspecialista?id={id}` | Actualiza un especialista |
| DELETE | `/post/deleteEspecialista?id={id}` | Elimina un especialista |

### Citas (`/citas`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/citas/addCita` | Crea una nueva cita |
| GET | `/citas/list` | Lista todas las citas |
| GET | `/citas/id?id={id}&especialistaId={id}` | Obtiene una cita especifica |
| PUT | `/citas/edit?id={id}` | Actualiza una cita |
| DELETE | `/citas/delete?id={id}&especialistaId={id}` | Elimina una cita |

### Autenticacion (`/auth`)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/auth/verify` | Verifica un token de Firebase Auth |
| GET | `/auth/users` | Lista todos los usuarios de Firebase Auth |

---

## Requisitos

- Java 17+
- Gradle 8+
- Cuenta de Firebase con Firestore y Auth habilitados

## Configuracion

1. **Firebase**: Coloca tu archivo `serviceAccountKey.json` en `src/main/resources/firebase/`
   - O usa la variable de entorno `FIREBASE_CREDENTIALS_JSON` con el contenido JSON

2. **Mail** (opcional): Configura las variables de entorno:
   - `MAIL_USERNAME=tu-email@gmail.com`
   - `MAIL_PASSWORD=tu-app-password`

3. **Ejecutar**:
   ```bash
   ./gradlew bootRun
   ```

4. **Documentacion API**:
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

## Docker

```bash
docker build -t sickslobby .
docker run -e FIREBASE_CREDENTIALS_JSON="$(cat serviceAccountKey.json)" sickslobby
```
