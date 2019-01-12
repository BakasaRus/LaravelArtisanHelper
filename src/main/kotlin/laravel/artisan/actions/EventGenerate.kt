package laravel.artisan.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFileManager
import laravel.artisan.runCommand

class EventGenerate : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val command = "php artisan event:generate --no-interaction"
        val result = command.runCommand(e.project?.basePath)

        VirtualFileManager.getInstance().syncRefresh()
        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
    }
}