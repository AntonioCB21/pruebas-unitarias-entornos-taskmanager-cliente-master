package es.prog2425.taskmanager.dominio

import es.prog2425.taskmanager.utilidades.Logger
import es.prog2425.taskmanager.utilidades.Utils

/**
 * Representa una tarea en el sistema con su estado y subtareas.
 *
 * @property id Identificador único generado automáticamente
 * @property descripcion Detalle de la tarea
 * @property estado Estado actual de la tarea ([Estado.ABIERTA] por defecto)
 * @property subtareas Lista de subtareas asociadas (opcional)
 */
class Tarea private constructor(
    id: Long,
    fechaCreacion: String,
    descripcion: String,
    var estado: Estado = Estado.ABIERTA,
    val parent: Tarea? = null,
    var asignadoA: Usuario? = null,
    etiquetas: List<String> = emptyList()
) : Actividad(id, fechaCreacion, descripcion, etiquetas) {
    val subtareas: List<Tarea> = mutableListOf()

    data class RegistroHistorial(
        val fecha: String,
        val accion: String,
        val detalle: String
    )

    private val historial: MutableList<RegistroHistorial> = mutableListOf()

    init {
        registrarAccion("Creación", "Tarea creada")
    }

    override fun obtenerDetalle(): String {
        val detallePadre = parent?.let { " (Subtarea de #${it.id})" } ?: ""
        val estadoStr = when (estado) {
            Estado.FINALIZADA -> "✅"
            Estado.EN_PROGRESO -> "⏳"
            else -> "❌"
        }
        val asignadoStr = asignadoA?.let { " 👤${it.nombre}" } ?: ""
        val subtareasStr = if (subtareas.isNotEmpty()) "\n  └ Subtareas: ${subtareas.joinToString { "#${it.id}" }}" else ""
        val etiquetasStr = if (etiquetas.isNotEmpty()) " 🏷️[${etiquetas.joinToString()}]" else ""
        return "Tarea #$id$detallePadre: $descripcion $estadoStr$asignadoStr$etiquetasStr$subtareasStr"
    }
    /**
     * Verifica si la tarea puede cerrarse (todas las subtareas deben estar finalizadas).
     *
     * @return `true` si no tiene subtareas pendientes, `false` en caso contrario
     * @see cambiarEstado
     */
    fun puedeCerrarse(): Boolean {
        return subtareas.all { it.estado == Estado.FINALIZADA }
    }

    fun cambiarEstado(estado: Estado) {
        Logger.info("Cambiando estado tarea:$id | De:${this.estado} → A:$estado")
        try {
            if (estado == Estado.FINALIZADA && !puedeCerrarse()) {
                Logger.error("Intento de cerrar tarea $id con subtareas pendientes")
                throw IllegalStateException("Subtareas no finalizadas")
            }

            registrarAccion("CambioEstado", "${this.estado} → $estado")
            this.estado = estado
            Logger.debug("Estado actualizado correctamente")
        } catch (e: Exception) {
            Logger.error("Error cambiando estado tarea $id: ${e.message}")
            throw e
        }
    }

    fun asignarUsuario(usuario: Usuario?) {
        registrarAccion(
            "Asignación",
            "Asignado a: ${usuario?.nombre ?: "Sin asignar"}"
        )
        this.asignadoA = usuario
    }

    fun obtenerHistorial(): List<RegistroHistorial> = historial.toList()

    private fun registrarAccion(accion: String, detalle: String) {
        historial.add(
            RegistroHistorial(
                fecha = Utils.obtenerFechaActual(),
                accion = accion,
                detalle = detalle
            )
        )
    }
    fun agregarSubtarea(subtarea: Tarea) {
        (subtareas as MutableList).add(subtarea)
    }

    companion object {
        fun creaInstancia(descripcion: String, parent: Tarea? = null, etiquetas: List<String> = emptyList()): Tarea {
            val fechaActual = Utils.obtenerFechaActual()
            return Tarea(
                Actividad.generarId(fechaActual),
                fechaActual,
                descripcion,
                parent = parent,
                etiquetas = etiquetas
            )
        }
    }
}
