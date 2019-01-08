package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand

class MakeEventDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var eventName: JTextField? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return eventName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyEventName = (eventName!!.text == "")
        return when {
            emptyEventName -> ValidationInfo("Event name should be specified", eventName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:event " + eventName!!.text
        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Success", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
