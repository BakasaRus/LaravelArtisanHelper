package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeNotificationDialog as Dialog

class MakeNotification : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = Dialog(e.project)
        dialog.title = "New Laravel Notification"
        dialog.show()
    }
}