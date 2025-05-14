# Actividad: Pruebas unitarias de un servicio

**ID actividad:** 3.2

---

## 1. Selección del servicio
Para la primera parte, seleccionaré el servicio: ``ActividadService`` (del archivo aplicacion/ActividadService.kt), que cumple con los requisitos:
* Dependencia inyectada:
```
class ActividadService(
    private val repositorio: IActividadRepository,  // <- Dependencia 1
    private val userRepository: UserRepository      // <- Dependencia 2
)
```

* Lógica de negocio: Gestiona actividades (tareas/eventos), usuarios, filtros, cambios de estado, etc.
* Métodos clave: ``crearTarea()``, ``asignarTarea()``, ``filtrarPorEstado()``, ``cambiarEstadoTarea()``, etc.

## 2. Identificación de métodos
### 1. crearTarea()
* Parámetros:
    - descripcion: String
    - etiquetas: List<String> = emptyList()

* Efecto:
    - Crea una nueva Tarea y la agrega al repositorio (repositorio.agregar()).
    - Validación: Lanza IllegalArgumentException si la descripción está vacía.

### 2. asignarTarea()
* Parámetros:
    - descripcion: String
    - etiquetas: List<String> = emptyList()

* Efecto:
    - Crea una nueva Tarea y la agrega al repositorio (repositorio.agregar()).
    - Validación: Lanza IllegalArgumentException si la descripción está vacía.
