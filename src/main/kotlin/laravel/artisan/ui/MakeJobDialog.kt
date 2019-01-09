package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand

class MakeJobDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var jobName: JTextField? = null
    private var syncCheckBox: JCheckBox? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return jobName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyJobName = (jobName!!.text == "")
        return when {
            emptyJobName -> ValidationInfo("Job name should be specified", jobName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:job --no-interaction " + jobName!!.text
        if (syncCheckBox!!.isSelected)
            command += " --sync" +
                    ""
        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
