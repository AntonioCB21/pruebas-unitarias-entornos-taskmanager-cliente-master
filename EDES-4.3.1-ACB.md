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
/path/to/file.kt:15:1: [indent] Unexpected indentation (expected 4, actual 2)

* Imports No Ordenados

* Llaves Inconsistentes

* Wildcards Imports

* Final Newline Missing

## 4. Documentación de errores

## 5. Opción de configuración

## 6. Preguntas
