package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand

class MakeMiddlewareDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var middlewareName: JTextField? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return middlewareName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyMiddlewareName = (middlewareName!!.text == "")
        return when {
            emptyMiddlewareName -> ValidationInfo("Middleware name should be specified", middlewareName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:middleware --no-interaction " + middlewareName!!.text
        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
