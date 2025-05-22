# Actividad: Documentación de Clases con KDoc y Dokka en Kotlin

**ID actividad:** 4.4.1

---

## 1. Comentarios KDoc
```
/**
 * Servicio principal para gestionar operaciones relacionadas con actividades (tareas y eventos).
 * 
 * Esta clase maneja la lógica de negocio para:
 * - Creación/modificación de actividades
 * - Filtrado y búsqueda
 * - Asignación a usuarios
 *
 * @property repositorio Instancia de [IActividadRepository] para acceso a datos
 * @property userRepository Instancia de [UserRepository] para gestión de usuarios
 */
class ActividadService(
    private val repositorio: IActividadRepository,
    private val userRepository: UserRepository
) {
    /**
     * Crea una nueva tarea con la descripción proporcionada.
     *
     * @param descripcion Texto descriptivo de la tarea (no vacío)
     * @param etiquetas Lista opcional de etiquetas para clasificación
     * @throws IllegalArgumentException Si la descripción está vacía
     * @sample ActividadServiceTest.crearTarea_DescripcionValida_GuardaEnRepositorio
     */
    fun crearTarea(descripcion: String, etiquetas: List<String> = emptyList()) {
        require(descripcion.isNotBlank()) { "La descripción no puede estar vacía" }
        // ... implementación
    }
}

/**
 * Representa una tarea en el sistema con su estado y subtareas.
 *
 * @property id Identificador único generado automáticamente
 * @property descripcion Detalle de la tarea
 * @property estado Estado actual de la tarea ([Estado.ABIERTA] por defecto)
 * @property subtareas Lista de subtareas asociadas (opcional)
 */
class Tarea(
    val id: Long,
    val descripcion: String,
    var estado: Estado = Estado.ABIERTA,
    val subtareas: List<Tarea> = emptyList()
) {
    /**
     * Verifica si la tarea puede cerrarse (todas las subtareas deben estar finalizadas).
     *
     * @return `true` si no tiene subtareas pendientes, `false` en caso contrario
     * @see cambiarEstado
     */
    fun puedeCerrarse(): Boolean {
        return subtareas.all { it.estado == Estado.FINALIZADA }
    }
}

/**
 * Utilidades para manejo de fechas y validaciones.
 * 
 * Objeto singleton que proporciona métodos estáticos para:
 * - Formateo de fechas
 * - Validación de cadenas
 */
object Utils {
    /**
     * Verifica si un email tiene formato válido.
     *
     * @param email Cadena a validar
     * @return `true` si cumple con el patrón user@domain.com, `false` en caso contrario
     * @sample UtilsTest.validarEmail_FormatoCorrecto_RetornaTrue
     */
    fun validarEmail(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@(.+)\$").matches(email)
    }
}
```

## 2. Generación con Dokka
```
// build.gradle.kts (Configuración Dokka)

plugins {
    id("org.jetbrains.dokka") version "1.9.10" // Última versión estable
}

repositories {
    mavenCentral()
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("documentation/html")) 
    
    moduleName.set("TaskManager")
    dokkaSourceSets {
        configureEach {
            includes.from("README.md") // Incluye documentación adicional
            samples.from("src/test/kotlin/es/prog2425/taskmanager/samples") 
            
            // Mapeo de paquetes
            perPackageOption {
                matchingRegex.set("es\\.prog2425\\.taskmanager\\.dominio")
                suppress.set(false)
                reportUndocumented.set(true) // Alertas si falta KDoc
            }
        }
    }
}

// Tarea personalizada para generar y abrir docs
tasks.register("openDokka") {
    dependsOn("dokkaHtml")
    doLast {
        java.awt.Desktop.getDesktop().browse(
            File(buildDir, "documentation/html/index.html").toURI()
        )
    }
}
```

Pasos para generación:
1. Ejecutar Dokka (terminal):
```
./gradlew dokkaHtml  # Genera HTML en build/documentation/html
# ó
./gradlew openDokka  # Genera y abre automáticamente
```

2. Estructura generada:
```
build/
└── documentation/
    └── html/
        ├── index.html          # Página principal
        ├── es.prog2425...     # Documentación por paquete
        └── styles.css         # Estilos personalizables
```

## 3. Capturas y procesos
### Código con los bloques KDoc en el IDE
![image](https://github.com/user-attachments/assets/9def2a3d-5d5c-4a30-b778-522f56a52215)

![image](https://github.com/user-attachments/assets/e810b421-116a-420f-976d-741d202eeff8)

![image](https://github.com/user-attachments/assets/243af1c8-b457-4767-9ec6-ce2e5400f5d2)

![image](https://github.com/user-attachments/assets/c4ae8f69-a5a3-4091-8559-9b64403179a0)

![image](https://github.com/user-attachments/assets/2b1c4467-6b36-4759-8496-9f1b227d7301)

### Navegador mostrando la documentación HTML
![image](https://github.com/user-attachments/assets/cf94b909-1d0d-4a1f-b30f-0b3a268babac)

## 4. Documentación de proceso
# Informe de Documentación con KDoc y Dokka

## 1. Proceso Seguido

### 1.1 Documentación con KDoc
**Pasos**:
1. Identificación de clases clave (`ActividadService`, `Tarea`, `Utils`).
2. Adición de bloques KDoc con:
   ```kotlin
   /**
    * Descripción de clase/método
    * @param nombre Descripción
    * @return Descripción
    */
   ```
3. Verificación en IDE (IntelliJ):
Uso de Ctrl + Q para previsualizar docs.

![image](https://github.com/user-attachments/assets/74cec3f0-2289-461b-a2b1-686d198638f0)

### 1.2 Generación con Dokka
Configuración (build.gradle.kts):
```
plugins {
    id("org.jetbrains.dokka") version "1.9.10"
}

tasks.dokkaHtml {
    outputDirectory.set(projectDir.resolve("docs")) // Ruta personalizada
}
```

Comandos:
```
# Generar HTML
./gradlew dokkaHtml

# Generar y abrir automáticamente (Linux/Mac)
./gradlew dokkaHtml && xdg-open docs/index.html
```

## 2. Resultados obtenidos

### Estructura de documentación
```
/docs
├── index.html          # Página principal
├── es-prog2425-taskmanager
│   ├── dominio        # Documentación por paquete
│   └── aplicacion
└── styles.css         # Estilos
```

![image](https://github.com/user-attachments/assets/6f797ad8-569d-4e4a-ac4a-1750eae70f6b)
