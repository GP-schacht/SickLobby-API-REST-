# Guía de Problemas Pendientes y Soluciones

## Problemas Solucionados en esta sesión

- [x] Implementados `edit()` y `remove()` en CitasServices
- [x] Agregados endpoints PUT `/citas/edit` y DELETE `/citas/delete`
- [x] Validación `@Valid` en todos los controllers
- [x] `GlobalExceptionHandler` para respuestas de error estandarizadas
- [x] Credenciales Firebase configurables por variable de entorno
- [x] Corregido `grupoEdad` en PacienteDTO (ya no lanza excepción)
- [x] Agregado `AuthController` con endpoints `/auth/verify` y `/auth/users`

---

## Problemas que requieren atención manual

### 1. Seguridad - Autenticación JWT/Firebase completa

**Problema:** Todos los endpoints están en `permitAll()` en `Security.java`.

**Cómo solucionarlo:**

1. Crear un filtro JWT que verifique tokens de Firebase:

```java
// src/main/java/com/example/sickslobby/Config/FirebaseAuthFilter.java
public class FirebaseAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            try {
                FirebaseToken decoded = FirebaseAuth.getInstance()
                    .verifyIdToken(token.substring(7));
                // Set authentication in SecurityContext
            } catch (FirebaseAuthException e) {
                response.setStatus(401);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
```

2. Actualizar `Security.java` para requerir autenticación:
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/auth/**", "/docs/**").permitAll()
    .anyRequest().authenticated()
)
.addFilterBefore(new FirebaseAuthFilter(), UsernamePasswordAuthenticationFilter.class);
```

3. Agregar roles (admin, especialista, paciente) usando Firebase Custom Claims:
```java
FirebaseAuth.getInstance().setCustomUserClaims(uid, Map.of("role", "admin"));
```

---

### 2. Tests Unitarios e Integración

**Problema:** Solo existe el test vacío por defecto.

**Cómo solucionarlo:**

1. Crear tests de servicio (mockeando Firestore):

```java
// src/test/java/com/example/sickslobby/Services/PacientesServicesTest.java
@ExtendWith(MockitoExtension.class)
class PacientesServicesTest {
    @Mock private SharedMethods sharedMethods;
    @InjectMocks private PacientesServices service;

    @Test
    void addPaciente_deberiaRetornarTrue() {
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setEdad(30);
        // ... mock Firestore response
        assertTrue(service.add(paciente));
    }
}
```

2. Crear tests de integración con Testcontainers:
```java
@SpringBootTest
@AutoConfigureMockMvc
class CitasControllerTest {
    @Autowired MockMvc mockMvc;

    @Test
    void addCita_conDatosValidos_deberiaRetornar200() throws Exception {
        mockMvc.perform(post("/citas/addCita")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"pacienteId\":\"1\",\"especialistaId\":\"2\",\"cita\":{...}}"))
            .andExpect(status().isOk());
    }
}
```

3. Ejecutar tests: `./gradlew test`

---

### 3. Paginación y Filtros en Listados

**Problema:** `list()` trae todos los registros sin paginación.

**Cómo solucionarlo:**

1. Agregar parámetros de paginación en controllers:
```java
@GetMapping("/ListPaciente")
public ResponseEntity listPacientes(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size) {
    // Implementar paginación en Firestore con startAfter()
}
```

2. Firestore soporta paginación con `startAfter()` y `limit()`:
```java
collection.orderBy("nombre")
    .startAfter(lastDocument)
    .limit(size)
    .get();
```

---

### 4. Notificaciones (Email/SMS) para Recordatorio de Citas

**Problema:** No hay sistema de notificaciones.

**Cómo solucionarlo:**

1. Usar Spring Mail para emails:
```java
// En build.gradle.kts:
implementation("org.springframework.boot:spring-boot-starter-mail")

// En application.properties:
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-app-password
```

2. Crear un scheduled job:
```java
@Scheduled(cron = "0 0 8 * * ?") // Cada día a las 8am
public void enviarRecordatorios() {
    List<CitasDTO> citasDeHoy = citasServices.getCitasDeHoy();
    citasDeHoy.forEach(cita -> {
        emailService.enviarRecordatorio(cita);
    });
}
```

3. Para SMS, usar Twilio SDK:
```java
implementation("com.twilio.sdk:twilio:9.2.0")
```

---

### 5. Historial Médico por Paciente

**Problema:** No existe modelo para historial médico.

**Cómo solucionarlo:**

1. Crear DTO:
```java
@Data
public class HistorialMedicoDTO {
    private String id;
    private String pacienteId;
    private String diagnostico;
    private String tratamiento;
    private String fecha;
    private String especialistaId;
    private String notas;
}
```

2. Crear colección en Firestore bajo `Pacientes/{id}/Historial`

3. Crear servicio y controller siguiendo el patrón existente

---

### 6. Agenda/Disponibilidad de Especialistas

**Problema:** No hay control de horarios disponibles.

**Cómo solucionarlo:**

1. Crear DTO de disponibilidad:
```java
@Data
public class DisponibilidadDTO {
    private String especialistaId;
    private String diaSemana; // "LUNES", "MARTES", etc.
    private String horaInicio; // "09:00"
    private String horaFin;    // "17:00"
    private List<String> slotsOcupados; // ["09:00", "10:00"]
}
```

2. Al crear cita, verificar disponibilidad antes de permitir

---

### 7. Dashboard con Métricas

**Problema:** No hay endpoints para estadísticas.

**Cómo solucionarlo:**

1. Crear controller de métricas:
```java
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return Map.of(
            "totalPacientes", pacientesServices.list().size(),
            "citasHoy", citasServices.getCitasDeHoy().size(),
            "citasPorEspecialidad", citasServices.getCitasPorEspecialidad()
        );
    }
}
```

2. Para gráficos, usar Firebase con consultas agregadas o integrar con herramientas como Grafana

---

### 8. Swagger/OpenAPI para Documentación

**Problema:** No hay documentación de API interactiva.

**Cómo solucionarlo:**

1. Agregar dependencia en `build.gradle.kts`:
```kotlin
implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
```

2. Acceder a: `http://localhost:8080/swagger-ui.html`

3. Anotar controllers:
```java
@Operation(summary = "Lista todos los pacientes")
@GetMapping("/ListPaciente")
public ResponseEntity listPacientes() { ... }
```

---

### 9. Mejorar Manejo de Logs

**Problema:** Uso de `e.printStackTrace()` en producción.

**Cómo solucionarlo:**

1. Reemplazar todos los `e.printStackTrace()` con logger:
```java
private static final Logger log = LoggerFactory.getLogger(MyService.class);

// En lugar de e.printStackTrace():
log.error("Error al procesar paciente: {}", e.getMessage(), e);
```

2. Configurar `application.properties`:
```properties
logging.level.com.example.sickslobby=DEBUG
logging.file.name=logs/app.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

---

### 10. Roles y Permisos

**Problema:** No hay diferenciación de roles.

**Cómo solucionarlo:**

1. Usar Firebase Custom Claims para asignar roles:
```java
FirebaseAuth.getInstance().setCustomUserClaims(uid, Map.of("role", "admin"));
```

2. Crear annotation personalizada:
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN')")
public @interface RequiresAdmin {}
```

3. Usar en controllers:
```java
@DeleteMapping("/deletePaciente")
@RequiresAdmin
public ResponseEntity deletePaciente(@RequestParam String id) { ... }
```
