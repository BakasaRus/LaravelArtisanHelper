package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeController as Dialog
import java.io.File
import java.util.concurrent.TimeUnit

class MakeController : AnAction() {
    fun String.runCommand(workingDir: File) {
        ProcessBuilder(*split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor(60, TimeUnit.MINUTES)
    }

    override fun actionPerformed(e: AnActionEvent) {
        Dialog.main(null)
        "php artisan make:controller TestController".runCommand(File(e.project?.basePath))
    }
}