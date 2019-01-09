package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeModelDialog as Dialog

class MakeModel : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = Dialog(e.project)
        dialog.title = "New Laravel Eloquent Model"
        dialog.show()
    }
}