# Actividad: Aplicación de Code Smells y Patrones de Refactorización en el Código del Task Manager

**ID actividad:** 4.3.2

---

# Análisis de Code Smells y Patrones de Refactorización

## 1. Code Smells Identificados

### 1.1 Long Method (Método Largo)
- **Archivo**: `ActividadService.kt`  
- **Ubicación**: Método `filtrarPorFecha()` (35 líneas)  
- **Problema**:  
  - Lógica compleja de fechas en un solo método  
  - Dificulta mantenimiento y testing  
- **Patrón sugerido**:  
  ```diff
  + Extraer a clases pequeñas (Strategy Pattern para filtros de fecha)
  ```

### 1.2 Primitive Obsession
- **Archivo**: `Tarea.kt`  
- **Ubicación**: Uso de String para estados (``"ABIERTA", "FINALIZADA"``)  
- **Problema**:  
  - Falta type-safety  
  - Validaciones manuales innecesarias
- **Solución**:
  ```
  // ✅ Usar enum class Estado (ya implementado pero subutilizado)
  ```

### 1.3 Duplicated Code
- **Archivo**: `ConsolaUI.kt`  
- **Ubicación**: Validaciones duplicadas en menús (``leerOpcion()``)  
- **Ejemplo**:
    ```
      // ❌ Duplicado en 3 métodos:
    try { opcion = readln().toInt() } catch(e: Exception) { ... }
    ```
- **Patrón sugerido**:  
  ```diff
  + Extraer a método común (Template Method)

### 1.4 Feature Envy
- **Archivo**: `Evento.kt`  
- **Ubicación**: Método ``obtenerDetalle()`` accede mucho a ``Utils``  
- **Problema**:  
  - Rompe encapsulamiento  
  - Alta dependencia de clase externa  
- **Refactor**:  
  ```diff
  + Mover lógica de formato a Utils (Delegate Pattern)
  ```

 ### 1.5 Large Class
- **Archivo**: `ActividadService.kt`   
- **Problema**:  
  - 400+ líneas  
  - Gestiona usuarios, tareas y eventos
- **Solución**:  
  ```diff
  + Dividir en: TareaService, EventoService (Single Responsibility)
  ```

## 2. Patrones de Refactorización Aplicables

| Code Smell           | Patrón             | Beneficio                             |
|----------------------|--------------------|----------------------------------------|
| Long Method          | Strategy Pattern   | Filtros de fecha intercambiables       |
| Duplicated Code      | Template Method    | Reducción de redundancia               |
| Feature Envy         | Delegate Pattern   | Mejor encapsulamiento                  |
| Large Class          | Facade/Services    | Separación de responsabilidades        |
| Primitive Obsession  | Type Objects       | Mayor seguridad de tipos               |

---

# Refactorización de Code Smells

## 1. Extracción de Método (Long Method)
**Archivo**: `ActividadService.kt`  
**Problema**: Método `filtrarPorFecha()` con 35 líneas y lógica compleja.

**Cambios**:
1. Seleccioné el bloque de validación de fechas (líneas 15-28)
2. **Refactor > Extract > Method** (IntelliJ)
3. Nombre del nuevo método: `validarYParsearFecha()`

```kotlin
// ✅ DESPUÉS
private fun validarYParsearFecha(fechaStr: String): LocalDate? {
    return try {
        LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    } catch (e: Exception) {
        Logger.warn("Fecha inválida: $fechaStr")
        null
    }
}

fun filtrarPorFecha(fecha: String): List<Evento> {
    val fechaObj = validarYParsearFecha(fecha) ?: return emptyList()
    // ... resto del método simplificado
}
```

## 2. Introducir Parámetro Objeto (Primitive Obsession)
**Archivo**: ``Tarea.kt``  
**Problema**: Múltiples parámetros primitivos en ``crearSubtarea()``.

**Cambios**:
1. Creé clase ``SubtareaConfig``:
```
data class SubtareaConfig(
    val parentId: Long,
    val descripcion: String,
    val prioridad: Int = 1
)
```

2. **Refactor > Change Signature** en el método:
```
// ✅ DESPUÉS
fun crearSubtarea(config: SubtareaConfig) { ... }
```

**Uso**:
```
// ❌ ANTES
crearSubtarea(123, "Comprar materiales")

// ✅ DESPUÉS
crearSubtarea(SubtareaConfig(
    parentId = 123,
    descripcion = "Comprar materiales"
))
```

## 3. Simplificar Condicional
**Archivo**: `ConsolaUI.kt`  
**Problema**: Condicional anidado en ``mostrarMenuPrincipal()``.

**Cambios**:
1. **Refactor > Replace Conditional with Polymorphism**
2. Creé interfaz Menu y clases hijas:
```
interface Menu {
    fun mostrar()
}

class MenuPrincipal : Menu {
    override fun mostrar() { ... }
}
```

**Resultado**:
```
// ✅ DESPUÉS
when (tipoMenu) {
    "principal" -> MenuPrincipal().mostrar()
    "usuarios" -> MenuUsuarios().mostrar()
}
```

---

# Verificación con Pruebas Unitarias
**Archivo**: ``ActividadServiceTest.kt``
**Aseguramos que ``filtrarPorFecha`` mantiene su comportamiento**:
```
@Test
fun `filtrarPorFecha con formato inválido retorna lista vacía`() {
    val resultado = servicio.filtrarPorFecha("31/02/2023")
    assertTrue(resultado.isEmpty())
}
```

**Resultado**:
✔️ Todas las pruebas pasan después de la refactorización.

---

# Pruebas Unitarias para Refactorizaciones

## 1. Pruebas para Método Extraído (`validarYParsearFecha`)

**Archivo**: `ActividadServiceTest.kt`

### Prueba ANTES de refactorizar (cobertura original):
```kotlin
@Test
fun `filtrarPorFecha con fecha inválida retorna vacío`() {
    val servicio = ActividadService(mockRepo, mockUserRepo)
    every { mockRepo.listar() } returns listOf(eventoValido)
    
    val resultado = servicio.filtrarPorFecha("31-02-2023")
    
    assertTrue(resultado.isEmpty())
}
```

### Pruebas NUEVAS para método extraído:
class DateUtilsTest {
    @Test
    fun `validarYParsearFecha con formato correcto retorna LocalDate`() {
        val fecha = validarYParsearFecha("15-01-2023")
        assertEquals(LocalDate.of(2023, 1, 15), fecha)
    }

    @Test
    fun `validarYParsearFecha con formato incorrecto retorna null`() {
        val fecha = validarYParsearFecha("2023/01/15")
        assertNull(fecha)
    }
}

## 2. Pruebas para Parámetro Objeto (SubtareaConfig)
**Archivo**: ``TareaServiceTest.kt``
@Test
fun `crearSubtarea con config válida actualiza repositorio`() {
    val config = SubtareaConfig(
        parentId = 1L,
        descripcion = "Subtarea test",
        prioridad = 2
    )
    
    servicio.crearSubtarea(config)
    
    verify(exactly = 1) { mockRepo.agregar(any()) }
}

## 3. Pruebas para Menú Polimórfico
**Archivo**: ``MenuTest.kt``
class MenuPrincipalTest {
    @Test
    fun `mostrar imprime opciones correctas`() {
        val output = captureSystemOut { MenuPrincipal().mostrar() }
        
        assertTrue(output.contains("1. Gestión de usuarios"))
        assertTrue(output.contains("4. Salir"))
    }
}

// Utilidad para capturar System.out
private fun captureSystemOut(block: () -> Unit): String {
    val buffer = StringWriter()
    System.setOut(PrintWriter(buffer))
    block()
    System.setOut(System.out)
    return buffer.toString()
}

---

# Preguntas

### 1.a
| Code Smell            | Patrón Aplicado          | Archivo              
|-----------------------|--------------------------|----------------------
| Long Method           | Extracción de Método     | `ActividadService.kt`
| Primitive Obsession   | Parameter Object         | `Tarea.kt`           
| Duplicated Code       | Template Method          | `ConsolaUI.kt`       

### 1.b
**Código Antes**:  
[`ActividadService.kt`]  
```kotlin
// ❌ Método monolítico
fun filtrarPorFecha(fecha: String): List<Evento> {
    // 30 líneas de lógica mezclada
}
```

**Código Después**:
ActividadService.kt (Refactorizado)
```
// ✅ Métodos extraídos
fun filtrarPorFecha(fecha: String) = filtrarEventos(parsearFecha(fecha))

private fun parsearFecha(fecha: String): LocalDate? { ... }
private fun filtrarEventos(fecha: LocalDate?): List<Evento> { ... }
```

### 2.a
1. Pruebas Existente:
Ejecuté ./gradlew test antes de refactorizar.

2. Refactor con IDE:
Usé Refactor > Extract Method.

3. Verificación:
Corrí las pruebas nuevamente tras cada cambio.

Ejemplo:
```
bash
./gradlew test --tests "es.prog2425.taskmanager.service.ActividadServiceTest"
```

4. Cobertura:
Aseguré que los tests cubrían el 100% de los métodos extraídos.

## 3.a
Para Extracción de Método:

1. Seleccioné el bloque de código (15 líneas en ``filtrarPorFecha``).

2. Click Derecho > Refactor > Extract > Method.

3. Configuré el nuevo método.

4. Resultado:
IDE ajustó automáticamente todas las llamadas al método antiguo.

El IDE garantiza que los cambios son seguros y consistentes en todo el proyecto.
