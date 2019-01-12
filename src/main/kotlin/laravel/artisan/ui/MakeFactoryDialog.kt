package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VirtualFileManager
import laravel.artisan.runCommand

class MakeFactoryDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var factoryName: JTextField? = null
    private var modelName: JTextField? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return factoryName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyEventName = (factoryName!!.text == "")
        return when {
            emptyEventName -> ValidationInfo("Factory name should be specified", factoryName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:factory --no-interaction " + factoryName!!.text
        if (modelName!!.text != "")
            command += " --model=" + modelName!!.text

        val result = command.runCommand(project?.basePath)

        VirtualFileManager.getInstance().syncRefresh()
        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
