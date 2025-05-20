# Actividad: Analizadores de código estático: Linting

**ID actividad:** 4.3.1

---

## 1. Instalación de Ktlint
Paso 1 - Decidí instalar Ktlint usando el plugin de Gradle porque es el gestor de dependencias que ya estaba usando en el proyecto. Modifiqué el archivo build.gradle.kts para añadir la configuración necesaria:

![image](https://github.com/user-attachments/assets/31f03f16-f685-436e-a949-8d21e5cd0d1a)

Paso 2 - Después de guardar los cambios, sincronicé Gradle haciendo clic en el elefante azul que apareció en la esquina superior derecha del IDE.

![image](https://github.com/user-attachments/assets/8d592a4a-0e2a-4afc-af05-c429ff249cff)

Paso 3 - Para confirmar que se instaló correctamente, ejecuté en terminal:

![image](https://github.com/user-attachments/assets/c67e3ed6-fba1-4f64-b0c2-a3548aaab217)

## 2. Análisis

Añade esto en tu archivo de construcción (asegúrate de que la versión sea compatible con tu Gradle/Kotlin):

![image](https://github.com/user-attachments/assets/86b04493-43fb-441f-8623-854487ef65d2)

## 3. Errores
* Indentación Incorrecta  
``/path/to/file.kt:15:1: [indent] Unexpected indentation (expected 4, actual 2)``

* Imports No Ordenados  
``/path/to/file.kt:3:1: [import-ordering] Imports must be ordered alphabetically``

* Llaves Inconsistentes  
``/path/to/file.kt:8:1: [curly-spacing] Missing spacing before "{"``

* Wildcards Imports  
``/path/to/file.kt:2:1: [no-wildcard-imports] Wildcard import (com.example.*)``

* Final Newline Missing  
``/path/to/file.kt:20:1: [final-newline] File must end with a newline``

## 4. Documentación de errores
* Indentación Incorrecta  
Descripción:
Ktlint exige 4 espacios por nivel de indentación (estándar oficial de Kotlin).

Antes:
```
// ❌ ANTES (2 espacios)  
fun main() {
∙∙println("Hola") // Error: Expected 4, found 2  
}
```

Después:
```
// ✅ DESPUÉS (4 espacios)  
fun main() {
∙∙∙∙println("Hola") // Corregido  
}
```

* Imports No Ordenados  
Descripción:
Los imports deben ordenarse alfabéticamente y agruparse (stdlib primero).

Antes:
```
// ❌ ANTES (desordenado)  
import com.example.utils.*  
import kotlinx.coroutines.*
```

Después:
```
// ✅ DESPUÉS (ordenado)  
import kotlinx.coroutines.*  
import com.example.utils.Formatter
```

* Llaves Inconsistentes  
Descripción:
Debe haber un espacio después de ) y antes de {.

Antes:
```
// ❌ ANTES  
fun foo(){ // Error: Missing spacing before "{"  
    // ...  
}
```

Después:
```
// ✅ DESPUÉS  
fun foo() { // Corregido  
    // ...  
}
```

* Wildcards Imports  
Descripción:
Prohíbe imports genéricos (.*) para mantener explícito el origen de las clases.

Antes:
```
// ❌ ANTES  
import com.example.model.* // Error: Wildcard import
```

Después:
```
// ✅ DESPUÉS  
import com.example.model.User  
import com.example.model.Product
```

* Final Newline Missing  
Descripción:
Todos los archivos deben terminar con una línea vacía.

Antes:
```
// ❌ ANTES (sin línea final)  
class Test {  
    // ...  
}⏎  // Falta \n al final
```

Después:
```
// ✅ DESPUÉS  
class Test {  
    // ...  
}  
⏎  // Línea vacía añadida
```

## 5. Opción de configuración

## 6. Preguntas
### Respuestas - Uso de Ktlint en el Proyecto

### 1.a ¿Qué herramienta has usado, y para qué sirve?
**Ktlint**, un linter/formateador para Kotlin que:
- **Analiza** el código en busca de desviaciones del estilo oficial de Kotlin.
- **Corrige automáticamente** errores de formato (indentación, espacios, etc.).
- **Integra** con Gradle, IntelliJ y sistemas CI/CD.

### 1.b ¿Cuáles son sus características principales?
- **Cero configuraciones** por defecto sigue el estilo oficial de Kotlin.
- **Corrección automática** con `ktlintFormat`.
- **Extensible**: Añade reglas personalizadas o ignora las existentes.
- **Informes detallados** (HTML, JSON, etc.).

### 1.c ¿Qué beneficios obtienes al utilizarla?
- **Código consistente** en todo el proyecto.
- **Ahorro de tiempo** en debates de estilo.
- **Mejora la legibilidad** y mantenibilidad.
- **Evita errores comunes** (ej: imports no usados).

---

### 2.a ¿Qué error mejoró más tu código?
**Wildcard imports** (`import com.example.*`).  
- **Mejora**: Hace explícitas las dependencias, evitando confusiones y conflictos.

### 2.b ¿La solución te pareció correcta?
Sí. Reemplazar `.*` por imports específicos:
```kotlin
// ❌ Antes
import com.example.model.*

// ✅ Después
import com.example.model.User
import com.example.model.Product
```

### 2.c ¿Por qué se produjo este error?
- Causa: Uso de atajos durante desarrollo rápido.
- Problema: Dificulta el rastreo de dependencias y puede causar colisiones de nombres.

---

### 3.a ¿Qué posibilidades de configuración tiene?
- Habilitar/deshabilitar reglas: Ej: no-wildcard-imports.
- Ajustar indentación: Por archivo o tipo de bloque.
- Ignorar archivos: Mediante .ktlintignore.
- Reglas experimentales: Como trailing-comma.

### 3.b ¿Qué configuraste distinto a lo predeterminado?
Habilité comas finales en listas multilínea:
```
ktlint {
    editorspecConfig.set(mapOf("ij_kotlin_allow_trailing_comma" to true))
}
```

### 3.c Ejemplo de impacto en el código
Antes (sin coma final):
```
val colors = listOf(
    "rojo",
    "verde",
    "azul"  // ← Nueva línea añadida genera conflicto en Git
)
```

Después (con coma final):
```
val colors = listOf(
    "rojo",
    "verde",
    "azul",  // Facilita añadir nuevos items
)
```

---

### 4. ¿Qué conclusiones sacas después del uso de estas herramientas?
- Estandarización: Ktlint unifica el estilo del equipo sin discusiones subjetivas.
- Prevención de errores: Detecta problemas temprano (ej: imports duplicados).
- Flexibilidad: Configurable para adaptarse a necesidades específicas.
- Flujo de trabajo: Integrable en CI/CD para garantizar calidad constante.
