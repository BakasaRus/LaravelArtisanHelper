package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand

class MakeSeederDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var seederName: JTextField? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return seederName
    }

    override fun doValidate(): ValidationInfo? {
        val emptySeederName = (seederName!!.text == "")
        return when {
            emptySeederName -> ValidationInfo("Seeder name should be specified", seederName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:seeder --no-interaction " + seederName!!.text
        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
