package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.TypeChooser
import laravel.artisan.runCommand

class MakeNotificationDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var notificationName: JTextField? = null
    private var pathName: JTextField? = null
    private var kindComboBox: JComboBox<String>? = null
    private var forceCheckBox: JCheckBox? = null
    private var kindHint: JLabel? = null

    init {
        init()
        kindComboBox!!.addItemListener {
            pathName!!.isEditable = (kindComboBox!!.selectedIndex == 1)
        }
        TypeChooser.setup(notificationName, kindComboBox, kindHint)
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return notificationName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyMailName = notificationName!!.text == ""
        val emptyPathName = kindComboBox!!.selectedIndex == 1 && pathName!!.text == ""
        return when {
            emptyMailName -> ValidationInfo("Notification name should be specified", notificationName)
            emptyPathName -> ValidationInfo("Path for Markdown template should be specified if you create Markdown notification", pathName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command: String = "php artisan make:notification --no-interaction " + notificationName!!.text
        if (kindComboBox!!.selectedIndex == 1)
            command += " --markdown=" + pathName!!.text
        if (forceCheckBox!!.isSelected)
            command += " --force"

        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}