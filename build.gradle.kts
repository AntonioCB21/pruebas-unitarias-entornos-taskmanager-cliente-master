plugins {
    id("org.jetbrains.dokka") version "1.9.10"
}

repositories {
    mavenCentral()
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("documentation/html"))

    moduleName.set("TaskManager")
    dokkaSourceSets {
        configureEach {
            includes.from("README.md") // Incluye documentación adicional
            samples.from("src/test/kotlin/es/prog2425/taskmanager/samples")

            // Mapeo de paquetes
            perPackageOption {
                matchingRegex.set("es\\.prog2425\\.taskmanager\\.dominio")
                suppress.set(false)
                reportUndocumented.set(true) // Alertas si falta KDoc
            }
        }
    }
}

// Tarea personalizada para generar y abrir docs
tasks.register("openDokka") {
    dependsOn("dokkaHtml")
    doLast {
        java.awt.Desktop.getDesktop().browse(
            File(buildDir, "documentation/html/index.html").toURI()
        )
    }
}
