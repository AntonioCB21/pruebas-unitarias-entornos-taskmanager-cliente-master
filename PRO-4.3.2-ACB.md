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
