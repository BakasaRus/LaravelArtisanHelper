package laravel.artisan.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.runCommand

class EventGenerate : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val command = "php artisan event:generate --no-interaction"
        val result = command.runCommand(e.project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Success", result!!, NotificationType.INFORMATION)
        )
    }
}