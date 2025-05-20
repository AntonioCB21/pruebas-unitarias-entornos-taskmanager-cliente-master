# Actividad: Analizadores de código estático: Linting

**ID actividad:** 4.3.1

---

## 1. Instalación de Ktlint
Decidí instalar Ktlint usando el plugin de Gradle porque es el gestor de dependencias que ya estaba usando en el proyecto. Modifiqué el archivo build.gradle.kts para añadir la configuración necesaria:

## 2. Identificación de métodos

## Métodos de Gestión de Tareas

### `crearTarea(descripcion: String, etiquetas: List<String> = emptyList())`
- **Parámetros**:
  - `descripcion`: Descripción de la tarea
  - `etiquetas`: Lista de etiquetas
- **Efecto**:
  - Crea nueva tarea en repositorio
- **Validaciones**:
  - Lanza `IllegalArgumentException` si descripción está vacía
