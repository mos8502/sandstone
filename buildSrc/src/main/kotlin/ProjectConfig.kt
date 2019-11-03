import org.gradle.api.Project
import java.io.FileReader
import java.util.*

val Project.baseUrl: String
    get() {
        val properties = Properties()
        FileReader(project.rootProject.file("local.properties")).use { reader ->
            properties.load(reader)
        }
        return properties.getProperty("sandstone_base_url")
    }