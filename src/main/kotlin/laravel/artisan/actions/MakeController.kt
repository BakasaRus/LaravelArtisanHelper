package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeController as Dialog

class MakeController : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Dialog.main(e.project?.basePath)
        //"php artisan make:controller TestController".runCommand(File(e.project?.basePath))
    }
}