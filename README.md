La rama donde se encuentra el código es esta, la "master" (no esta puesta como default)

--- 

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/jdvPLTuk)
# Actividad: Desarrollo de Proyecto Software en Kotlin

**ID actividad:** 2425_PRO_u4u5u6_taskManager

**Agrupamiento de la actividad**: Individual

---

## **Gestor de Tareas y Eventos para Proyectos Colaborativos**

### **Contexto y Objetivo**

Desarrolla una aplicación de consola en Kotlin que permita gestionar **actividades** en un proyecto colaborativo. Estas actividades se dividen en dos tipos: **Tareas** y **Eventos**. Ambas derivan de una superclase o interfaz denominada **Actividad**.

La aplicación debe seguir una **arquitectura en capas**, separando claramente:

- **La capa de presentación (UI):** se encarga de la interacción con el usuario a través de la consola.
- **La capa de lógica de aplicación (Servicios):** gestiona la lógica de negocio (creación, almacenamiento y manejo de actividades).
- **La capa de acceso a datos:** aunque en este ejercicio utiliza un repositorio en memoria, se debe abstraer su acceso mediante interfaces, aplicando el principio de inversión de dependencias (DIP).
- **La capa de dominio (Modelo)**: contendrá los modelos de negocio y lógica central.

### **Requerimientos Funcionales y No Funcionales**

1. **Arquitectura en Capas y Principio de Inversión de Dependencias**

    - La lógica de negocio debe depender de abstracciones y no de implementaciones concretas.
    - La comunicación entre la interfaz de usuario y la lógica de negocio debe estar claramente separada.

3. **Modelo de Dominio: Actividad, Tarea y Evento**

    - **Actividad:**
        - Contendrá la lógica común a todas las actividades, aunque no se permitirá la creación de una instancia de la misma.
        - Posee un **id** (Int). Se asigna automáticamente al crear la instancia. No se puede modificar.
        - Posee una **fechaCreacion** (String). Se asigna automáticamente al crear la instancia. No se puede modificar.
        - Posee un **descripción** (String). No puede estar vacía.
        - Las propiedades no serán visibles desde fuera de la clase `Actividad`.
        - Debe incluir un método, `obtenerDetalle(): String`, que utilice la lógica común para concatenar el *id* y la descripción (`<id> + " - " + <descripcion>`).

    - **Tarea:**
        - Hereda las propiedades de Actividad.
        - Posee una propiedad **estado**, por defecto `ABIERTA`. Que toma valores de la enum class `Estado = {ABIERTA, CERRADA}`
        - Tiene un método **obtenerDetalle** que se genera dinámicamente, sobreescribiendo el método de la superclase, pero también usándolo en la info: `Tarea <id> + " - " + <descripcion> + "[Estado: <estado>]"`.
        - Su constructor es **privado**. Se debe disponer de un método de clase (companion object) llamado `creaInstancia` para generar una nueva instancia.
        - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo.

    - **Evento:**
        - Hereda las propiedades de Actividad.
        - Tiene la propiedad **fecha**.
        - Tiene la propiedad **ubicación**.
        - Tiene un método **obtenerDetalle** que se genera dinámicamente, sobreescribiendo el método de la superclase, pero también usándolo en la info: `Evento <id> + " - " + <descripcion> + "[Fecha: <fecha>, Ubicación: <ubicacion>]"`.
        - Similar a **Tarea** en cuanto a tener un constructor privado y el método `creaInstancia`.
        - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo.

    - **Cálculo automático del id:**
        - La generación del id será común para Tarea y Evento.
        - Debe ser un valor automático y se generará a partir de la `fechaCreacion` en formato `YYYYMMDD` y un contador.
        - Debéis tener un contador por cada fechaCreacion, lo más sencillo es usar un diccionario o mapa con la fecha como la clave y el contador como valor `<String, Int>`.
        - Podéis crear un método estático `generarId()` que devuelva un nuevo id. Por si os sirve de guía os dejo la documentación del método: [Documentación del método generarId()](GenerarID.md)

4. **Buenas Prácticas y Principios SOLID**

    - Utiliza el principio de **inversión de dependencias**: la lógica de negocio no debe depender de clases concretas para el almacenamiento de las actividades.
    - Documenta y comenta el código de forma clara, y sobre todo aquellas aportaciones que no están indicadas en la descripción de la actividad.
    - Separa los métodos estáticos y asegúrate de que la creación de instancias se haga mediante el método `creaInstancia`.

6. **Interfaz de Usuario (Consola)**

    - La aplicación debe interactuar con el usuario a través de la consola, mostrando un menú que permita:
        - Crear una nueva actividad (seleccionando entre Tarea o Evento).
        - Listar todas las actividades registradas. Aplicando polimorfismo, se debe mostrar el detalle de cada actividad (id y descripción).
    - La capa de presentación debe comunicarse con la lógica de negocio a través de interfaces o abstracciones.
    - `ActividadService` debería implementar un interfaz, de manera que podamos usar esta abstracción para inyectarla en `ConsolaUI` (DIP).

7. **Lógica de Aplicación**
    - Implementa un servicio (por ejemplo, `ActividadService`) que gestione la creación, almacenamiento (en memoria) y consulta de actividades.
    - Este servicio debe depender de una interfaz de repositorio (por ejemplo, `IActividadRepository`), permitiendo cambiar la implementación del almacenamiento sin afectar la lógica de negocio.

8. Paquete extra para las Utilidades.
    - Crear el paquete utilidades para agrupar funciones auxiliares.
    - Implementar `Utils`, que debe seguir un **patrón Singleton**.
    - `Utils` contendrá una propiedad y dos métodos: [Contenido de Utils](Utils.md)
        * Utilizar el método `obtenerFechaActual` en la generación automática del valor de la propiedad `fechaCreacion` al crear una instancia de una actividad, es decir, una `Tarea` o `Evento`.
        * Utilizar el método `esFechaValida` para validar la correcta introducción de la `fecha` de un `Evento`.
          package es.prog2425.taskmanager

### **Trabajo a realizar**

El que se deriva de la descripción anterior. No obstante, te listo algunas cosas que te pueden ayudar a resolverlo:

1. **Definición de Clases y Estructura del Proyecto**
    - Crea un paquete para cada capa:
        - `presentacion` para la interfaz de usuario.
        - `aplicacion` o `servicios` para la lógica de negocio.
        - `datos` para la implementación del repositorio (en memoria).
        - `dominio` para definir las clases **Actividad**, **Tarea** y **Evento**.

2. **Paquete de Utilidades**
    - Crea un paquete más, `utilidades` para agrupar las funciones auxiliares reutilizables en la aplicación.

3. **Implementación del Modelo de Dominio**
    - Define la superclase o interfaz **Actividad** que incluya:
        - Cualquier lógica que consideres común a Tareas y Eventos.
        - Recuerda que sus propiedades no serán accesibles desde fuera de la clase.
    - Implementa **Tarea** y **Evento**:
        - Constructores privados: Asegúrate de que los constructores sean privados y se acceda a ellos únicamente mediante el método `creaInstancia`.
        - Propiedades: Implementa las propiedades no comunes y específicas de cada clase.
        - Método obtenerDetalle(): Sobreescribe y modifica su comportamiento de manera específica.

4. **Comprobación de errores en ls información introducida por el usuario:**
    - Realiza el control dentro de las clases del Modelo de Dominio.
    - Utiliza `require` para evitar que la `descripcion` y `ubicacion` esten vacías y la `fecha` del evento no sea válida.
    - Recuerda gestionar de manera correcta las excepciones que se lanzarán.

5. **Modificadores de acceso:**
    - Al terminar tu programa, intenta establecer los modificadores de acceso adecuados a la lógica final, tanto en métodos como en propiedades.
    - Las indicaciones del IDE te ayudarán bastante para esta modificación final.

6. **Desarrollo de la Lógica de Aplicación**
    - Implementa un servicio (`ActividadService`) que:
        - Utilice una interfaz de repositorio (`IActividadRepository`) para almacenar y recuperar actividades.
        - Permita la creación de nuevas actividades mediante métodos que invoquen `creaInstancia` de cada clase.
    - Recuerda aplicar el **principio de inversión de dependencias**: el servicio debe depender de la abstracción, no de una implementación concreta.

7. **Interfaz de Usuario (Consola)**
    - Crea una interfaz de usuario sencilla que muestre un menú:
        - **Opción 1:** Crear nueva actividad (se debe preguntar al usuario si desea crear una Tarea, un Evento o Cancelar la operación, si eliges 1 o 2 deberá solicitar los datos requeridos).
        - **Opción 2:** Listar todas las actividades, mostrando el detalle de cada una.
    - La capa de presentación debe invocar los métodos del servicio para realizar las operaciones solicitadas.
    - Una solución sencilla es que la clase `ConsolaUI` tenga un método dónde se muestre el menú. En tal caso, debes inyectar a `ConsolaUI` la `ActividadService` mediante una abstracción.
    - Otra posibilidad es desarrollar en la capa de aplicación una clase `GestorActividades` y mediante DIP, inyectar en ella `ConsolaUI` y `ActividadService`. Entonces esta clase será la principal y deberá tener el menú que gestiona las actividades.

8. **Documentación y Comentarios**
    - Asegúrate de comentar el código, explicando las decisiones de diseño, la aplicación de los principios SOLID (especialmente la inversión de dependencias) y el funcionamiento general del sistema.

9. **Prueba y Depuración:** Realiza pruebas para asegurarte de que tu aplicación funciona como se espera y depura cualquier error encontrado.

10. **MUY IMPORTANTE** => **Contesta a las preguntas** (Ver el punto **Preguntas para la Evaluación**)

---

### **📌 AYUDA EXTRA: ORGANIZACIÓN DEL CÓDIGO**

Existen **dos posibles enfoques** para estructurar la aplicación. Puedes elegir el que prefieras:

#### **Opción 1 - `ConsolaUI` es la clase principal**
Aquí, `ConsolaUI` maneja toda la interacción y llama a `ActividadService`.

**Posibles métodos de `ConsolaUI`:**
- `mostrarMenu()`
- `mostrar()`
- `pedirInfo()`
- `menu()`
- `crearActividad()`
- `listarActividades()`

#### **Opción 2 - `GestorActividades` es la clase principal**
En este caso, creamos `GestorActividades`, que orquesta la aplicación inyectando `ConsolaUI` y `ActividadService`.

**Posibles métodos de `GestorActividades`:**
- `menu()`
- `crearActividad()`
- `listarActividades()`

**Posibles métodos de `ConsolaUI`:**
- `mostrarMenu()`
- `mostrar()`
- `pedirInfo()`

Si decides utilizar `GestorActividades`, **colócala en el paquete `servicios`**, ya que actúa como intermediaria entre `ConsolaUI` y `ActividadService`.

---

### Recursos

- Apuntes dados en clase sobre programación orientada a objetos, Kotlin, uso de IDEs, y manejo de librerías.
- Documentación de Kotlin y guías de uso de librerías.

### Evaluación y calificación

**RA y CE evaluados**: Resultados de Aprendizaje 2, 4, 6, 7 y Criterios de Evaluación asociados.

**Conlleva presentación**: SI

### Entrega

> **La entrega tiene que cumplir las condiciones de entrega para poder ser calificada. En caso de no cumplirlas podría calificarse como no entregada.**
>
- **Conlleva la entrega de URL a repositorio:** El contenido se entregará en un repositorio GitHub.
- **Respuestas a las preguntas:** Deben contestarse, de manera clara y detallada en este fichero, README.md.
- **MUY IMPORTANTE!!** Incluir un subapartado ("Respuestas a las preguntas planteadas") dónde se resuelvan las preguntas de evaluación que os realizamos a continuación. De forma clara y detallada, incluyendo **OBLIGATORIAMENTE** enlaces a fragmentos de código que justifiquen vuestras respuestas.

---

# PREGUNTAS PARA LA EVALUACIÓN

Este conjunto de preguntas está diseñado para ayudarte a reflexionar sobre cómo has aplicado los criterios de evaluación en tu proyecto. Al responderlas, **asegúrate de hacer referencia y enlazar al código relevante**, facilitando así la evaluación de tu trabajo.

### **Criterio global 1: Instancia objetos y hacer uso de ellos**
- **(2.a, 2.b, 2.c, 2.d, 2.f, 2.h, 4.e, 4.f)**: Describe cómo has instanciado y utilizado los objetos en tu proyecto. ¿Cómo has aplicado los constructores públicos y constructores privados? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código donde usas constructores publicos y privados y les pasas parámetros.

- **Sobre los objetos**, en la parte de las utiliades, he puesto todo en un objeto. Para poder llamar a las funciones de forma correcta. El utils es una clase y no un objeto porque no tiene parametros, en las utilidades y cosas de este estilo, se utiliza muchisimo objetos para tipos de clases en las que no hagan faltas parametros y cosas por el estilo.

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/utilidades/Utils.kt#L7-L10

- **Constructores privados:** Usados en `Tarea` y `Evento` para forzar la creación mediante `creaInstancia` dentro del companion object.

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/dominio/Evento.kt#L6-L12

- **Constructores públicos:** No tengo, pero `ActividadService` en `aplicacion/ActividadService.kt` estoy creando instancias usando métodos estáticos

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/aplicacion/ActividadService.kt#L10-L15

### **Criterio global 2: Crear y llamar métodos estáticos**
- **(4.h)**: ¿Has definido algún método/propiedad estático en tu proyecto? Enumera TODOS los métodos y propiedades estáticas de tu proyecto. ¿Cuál era el objetivo y por qué consideraste que debía ser estático en lugar de un método/propiedad de instancia? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.

Singleton con todos sus metodos estaticos
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/utilidades/Utils.kt#L7
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/utilidades/Utils.kt#L17
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/utilidades/Utils.kt#L28

Propiedad y metodo estatico
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/dominio/Actividad.kt#L20-L24

Si he definido,

1. **`Utils.obtenerFechaActual()`**  
   - **Objetivo:** Con esto consigo obtener la fecha actual en formato estandarizado (`dd-MM-yyyy`) para toda la aplicacion.  
   - **Por qué es un metodo de tipo estático:**
     - Para lograr una funcionalidad genérica que no depende del estado de ningún objeto.  
     - Acceso global sin necesidad de instanciar `Utils` (patrón Singleton)

2. **`Utils.esFechaValida()`**  
   - **Objetivo:** Validar formato de fechas de forma consistente en toda la aplicación.  
   - **Por qué es un metodo de tpo estático:**  
     - Con esto puedo reutilizar el codigo de forma sencilla de una forma que no requiere almacenar estado interno.  
     - Me evito duplicar codigo de validación en múltiples clases.  

3. **`Actividad.generarId()`**  
   - **Objetivo:** Generar IDs únicos basados en fecha y contador.  
   - **Por qué es un metodo de tipo estático:**  
     - Puedo mantener un contador compartido (`contadorPorFecha`) entre todas las instancias.  
     - Garantizar secuencia única global para evitar colisiones de IDs.  

4. **`Actividad.contadorPorFecha`**  
   - **Objetivo:** Almacenar conteo de IDs por fecha como estado compartido.  
   - **Por que es un metodo estatico:**  
     - Debe persistir entre todas las instancias de actividades para mantener coherencia.
     - Evita reinicios del contador al crear nuevas instancias.

Con esto puede acceder a funcionalidad de fecha sin necesidad de crear instancias de `Utils`, optimizando recursos.

- **Beneficio:** esto me permite centralizar la lógica de manejo de fechas en un único punto de verdad

En Evento.kt llamo al metodo estatico de la utilidad para obtener la fecha actual.
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/dominio/Evento.kt#L27

- **(2.e)**: ¿En qué parte del código se llama a un método estático o se utiliza la propiedad estática? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.

### **Criterio global 3: Uso de entornos**
- **(2.i)**: ¿Cómo utilizaste el IDE para el desarrollo de tu proyecto? Describe muy brevemente la estructura de tu proyecto.

Tenemos la carpeta src (source) adentro el main y la carpeta kotlin (lo tipico como siempre para hacer un proyecto kotlin).

Luego tenemos diferentes carpetas/modulos donde vamos diferenciando por como el codigo tiene que funcionar. Asi organizamos el codigo por responsabilidades (dominio, datos, aplicacion, presentacion y utilidades)

**Uso de IDE IntelliJDEA para kotlin**
- Gracias al IDE he podido depurar fallos, realmente me ayuda en vez de tener que imprimir por consola todo, de esta forma puedo directamente ver el error y el flujoo del programa, retroceder y avanzar.
Otra ventaja que tiene el IntelliJ IDEA, es que te organiza y importa solo en cada archivo de kotlin, para ruta en donde te encuentras, el "package" te lo pone solo, esto es util para que no haya errores y que siempre este claro.

### **Criterio global 4: Definir clases y su contenido**
- **(4.a, 4.b, 4.c, 4.d, 4.g)**: Explica sobre un fragmento enlazado a tu código, cómo definiste las clases en tu proyecto, es decir como identificaste las de propiedades, métodos y constructores y modificadores del control de acceso a métodos y propiedades, para representar al objeto del mundo real. ¿Cómo contribuyen estas clases a la solución del problema que tu aplicación aborda, es decir a que paquete pertenecen y porque?

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/dominio/Tarea.kt#L5-L10

**Contribución al problema:**
- `Tarea` y `Evento` (paquete `dominio`) encapsulan la logica de actividades del proyecto.

### **Criterio global 5: Herencia y uso de clases abstractas e interfaces**
- **(4.g, 7.a, 7.b, 7.c, 7.i, 7.j)**: Explica sobre un fragmento enlazado a tu código cómo has implementado la herencia y/o utilizado interfaces en tu proyecto. ¿Por qué elegiste este enfoque y cómo beneficia a la estructura de tu aplicación? ¿De qué manera has utilizado los principios SOLID para mejorar el diseño de tu proyecto? Mostrando tu código, contesta qué principios has utilizado y qué beneficio has obtenido. Recuerda los que hay, porque seguro que has utilizado más de uno.

Para este caso decidi utilizar **herencia** con la clase abstracta `Actividad` para centralizar un poco la logica comun (id, fechaCreacion, descripcion) y asi tambien me evito duplicar el codigo en Tarea y Evento. A parte tambien fuerzo la implementacion del metodo obtenerDetaller, en cada subclase.

Las **interfaces** como `IActividadRepository` por ejemplo, me permitieron poder desacoplar la capa de datos de la lógica de negocio, gracias a esto me facilito cambios en el futuro (como por ejemplo tner que usar una base de datos en vez de memoria)
y tambien puedo definir contratos que sean claros como para operaciones basicas asi como por ejemplo poder agregar o listar actividades sin depender de implementaciones

**Principios SOLID APLICADOS**

- **DIP (Principio de Inversión de Dependencias)**

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L8

En este caso ActividadService depende de una abstracción (interfaz)
  
- **Beneficio:** Me permite cambiar el repositorio (como por ejemplo memoria a SQL) sin necesidad de modificar `ActividadService`, pudiendo mejorar un poco la flexibilidad y poder testear el codigo de fomra mas sencilla.

- **Liskov (Principio de Sustitución)**
List<Actividad> acepta Tarea y Evento (subtipo)
fun listarActividades(): List<Actividad> {
    return repositorio.listar()
}

- **Beneficio:** Las subclases se comportan como la superclase en la que esta heradando, permitiendo asi operaciones polimorficas como mostrar detalles de cualquier actividad.

- **SRP (Principio de Responsabilidad Única)**

Este principio es realmente util para poder organizar el codigo de forma efectiva. Y tambien ayuda muchisimo cuando un desarrollador tenga que mantener el codigo o hacer depuraciones para errores y solucionar asi cualquier tipo de error que pueda ocurrir.

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L8
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L11
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L18

Utilizar los principios SOLID es realmente un gran invento porque al menos para mi, noto que el codigo puede ser escalable, permitiendome añadir nuevos tipos de actividades en el futuro (por ejemplo una reunion), esto es muy facil de hacer simplemente heredando de Actividad. 
Tabmbien gracicas a esto el codigo es mantenible, ya que el codigo se separa por modulos o diferentes capas (dominio, datos, presentacion) asi me permite modificar una parte sin afectar al resto.
A su vez permite que el codigo sea extensible ya que puedo implementar nuevos repositorios, por ejemplo uno en el que pueda guardar archivos, de esta forma solo se requiere crear una clase que implemente IActividadRepository

Clase abstracta Actividad
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/dominio/Actividad.kt#L3-L7

Interface
https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/165a8ce978a3281b185845d4df2694f6d6dd8eb1/src/main/kotlin/datos/IActividadRepository.kt#L5-L8 

**Los principios SOLID que he aplicado son los siguientes:**
- **DIP (Inversión de Dependencias):**
El DIP (inversion de dependencias)
Podemos ver un ejemplo del proyecto que `ActividadService` depende de `IActividadRepository` (interfaz), no de la implementación concreta.
- **Liskov (Subtipo):**
Y podemos verlo como en `Tarea` y `Evento` pueden usarse donde se espera una `Actividad` (polimorfismo en `listarActividades()`).

---

### **Criterio global 6: Diseño de jerarquía de clases**
- **(7.d, 7.e, 7.f, 7.g)**: Presenta la jerarquía de clases que diseñaste, proporciona obligatoriamente ejemplos específicos de fragmentos de tu código. ¿Cómo probaste y depuraste esta jerarquía para asegurar su correcto funcionamiento? ¿Qué tipo de herencia has utilizado: Especificación, Especialización, Extensión, Construcción? (Ver [Tipo de Herencia](TipoHerencia.md))

**Jerarquía de clases:**
Voy a poner el ejemplo de la relacion entre Actividad, Tarea y Evento
- Actividad.kt es una clase abstracta. Nos sirve de molde para TAREA y EVENTO.
- Tarea.kt digamos que esta heredando la clase abstracta de Actividad para generar esa clase Tarea en especifica
- Evento.kt tambien estamos hereando de la clase abstracta de Actividad para generar esa clase Actividad en especifica.

Estamos haciendo un tipo de herencia "Especialización", lo que hace es basicamente ampliar la funcionalidad de la clase base (en este caso Actividad), y como estamos definiendo funciones abstractas en la clase abstracta Actividad, podemos facilmente en cada subclase heredada de la clase Actividad (en este caso Tarea y Evento), podemos hacer un `override fun`, en cada funcion abstracta que queramos sobrescribir. De esta forma podemos generar un comportamiento customizado para cada subcalse.

---

### **Criterio global 7: Documentado**
- **(7.h)**: Muestra enlaces a fragmentos de tu código en donde se vean ejemplos de cómo has documentado y comentado tu código. Explica la diferencia entre documentar y comentar. ¿Siempre es necesario documentar una aplicación o es más limpio y claro no hacerlo? ¿Realizarías **comentarios** para todas las líneas de código para que se entendiera mejor? o ¿Qué código comentarías y por qué?

La diferencia entre documentacion y comentarios es que la documentacion sirve para explicar a otros desarrolladores como funciona el codigo, asi como por se suele hacer en librerias como Mordant de kotlin para la terminal, ellos documentan su proyecto para que otra gente, otros desarrolladores puedan utilizar el codigo o que otra gente nueva y desarrolladores pueda entenderlo de forma sencilla.

Sin embargo los comentarios, sirve como su nombre indica, para comentar el codigo y añadir comentarios en cada parte de codigo que queramos explicar alguna funcionalidad de como funciona.

En ActividadService por ejemplo comento un poco el codigo:

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L10-L16

Aqui especifico que sera una funcion para crear una tarea y luego digo como esta tarea se añade al repositorio

https://github.com/IES-Rafael-Alberti/daw1b-2425-u4u5u6-taskmanager-obezeq/blob/40c63dda408bc7e05591a9faa3d531dc3476d852/src/main/kotlin/aplicacion/ActividadService.kt#L23-L27

En este fragmento del codigo, basicamente lo que hago es obtener todas las actividades del repositorio y menciono que es retornado.

