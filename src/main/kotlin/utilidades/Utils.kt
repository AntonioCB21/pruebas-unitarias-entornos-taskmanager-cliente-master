package es.prog2425.taskmanager.utilidades

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Utilidades para manejo de fechas y validaciones.
 *
 * Objeto singleton que proporciona métodos estáticos para:
 * - Formateo de fechas
 * - Validación de cadenas
 */
object Utils {
    // Formato: día-mes-año (ej: 25-10-2023)
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")


    /**
     * Obtiene la fecha actual.
     *
     * @return Un `String` con la fecha actual en formato `"dd-MM-yyyy"`.
     */
    fun obtenerFechaActual(): String {
        return LocalDate.now().format(formatter)
    }


    /**
     * Verifica si una fecha proporcionada es válida según el formato "dd-MM-yyyy".
     *
     * @param fecha La fecha a validar en formato `"dd-MM-yyyy"`.
     * @return `true` si la fecha tiene el formato correcto y es válida en el calendario; `false` en caso contrario.
     */
    fun esFechaValida(fecha: String): Boolean {
        return try {
            LocalDate.parse(fecha, formatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
    /**
     * Verifica si un email tiene formato válido.
     *
     * @param email Cadena a validar
     * @return `true` si cumple con el patrón user@domain.com, `false` en caso contrario
     * @sample UtilsTest.validarEmail_FormatoCorrecto_RetornaTrue
     */
    /* Caracteristica adicional añadida para validar emails*/
    fun validarEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
        return regex.matches(email)
    }
}
