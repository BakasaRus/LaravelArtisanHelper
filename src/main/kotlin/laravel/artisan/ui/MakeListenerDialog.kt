package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand

class MakeListenerDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var listenerName: JTextField? = null
    private var eventName: JTextField? = null
    private var queuedCheckBox: JCheckBox? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return listenerName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyListenerName = (listenerName!!.text == "")
        val emptyEventName = (eventName!!.text == "")
        return when {
            emptyListenerName -> ValidationInfo("Listener name should be specified", listenerName)
            emptyEventName -> ValidationInfo("Event name should be specified for the listener", eventName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:listener " + listenerName!!.text + " --event=" + eventName!!.text
        if (queuedCheckBox!!.isSelected)
            command += " --queued"

        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Success", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}