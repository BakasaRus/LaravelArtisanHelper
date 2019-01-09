package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeEventDialog as Dialog

class MakeEvent : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = Dialog(e.project)
        dialog.title = "New Laravel Event"
        dialog.show()
    }
}