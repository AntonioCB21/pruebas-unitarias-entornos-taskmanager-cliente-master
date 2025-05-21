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
