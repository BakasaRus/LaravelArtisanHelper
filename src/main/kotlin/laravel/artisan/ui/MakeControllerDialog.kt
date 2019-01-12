package laravel.artisan.ui

import javax.swing.*
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VirtualFileManager
import laravel.artisan.TypeChooser
import laravel.artisan.runCommand

class MakeControllerDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var controllerName: JTextField? = null
    private var modelName: JTextField? = null
    private var kindComboBox: JComboBox<String>? = null
    private var kindHint: JLabel? = null

    init {
        init()
        kindComboBox!!.addItemListener {
            modelName!!.isEditable = (kindComboBox!!.selectedIndex == 2)
        }
        TypeChooser.setup(controllerName, kindComboBox, kindHint)
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return controllerName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyControllerName = controllerName!!.text == ""
        val emptyModelName = kindComboBox!!.selectedIndex == 2 && modelName!!.text == ""
        return when {
            emptyControllerName -> ValidationInfo("Controller name should be specified", controllerName)
            emptyModelName -> ValidationInfo("Model name should be specified if you create resource controller for the model", modelName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command: String = "php artisan make:controller --no-interaction " + controllerName!!.text
        when (kindComboBox!!.selectedIndex) {
            1 -> command += " --resource"
            2 -> command += " --model=" + modelName!!.text
        }
        val result = command.runCommand(project?.basePath)

        VirtualFileManager.getInstance().syncRefresh()
        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}