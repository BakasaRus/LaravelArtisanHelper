package laravel.artisan.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.runCommand
import javax.swing.*
import java.awt.event.*

class MakeModelDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var modelName: JTextField? = null
    private var migrationCheckBox: JCheckBox? = null
    private var factoryCheckBox: JCheckBox? = null
    private var controllerCheckBox: JCheckBox? = null
    private var resControllerCheckBox: JCheckBox? = null
    private var pivotCheckBox: JCheckBox? = null
    private var forceCheckBox: JCheckBox? = null

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return modelName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyModelName = modelName!!.text == ""
        return when {
            emptyModelName -> ValidationInfo("Model name should be specified", modelName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command: String = "php artisan make:model --no-interaction " + modelName!!.text
        if (migrationCheckBox!!.isSelected)
            command += " --migration"
        if (factoryCheckBox!!.isSelected)
            command += " --factory"
        if (controllerCheckBox!!.isSelected)
            command += " --controller"
        if (resControllerCheckBox!!.isSelected)
            command += " --resource"
        if (pivotCheckBox!!.isSelected)
            command += " --pivot"
        if (forceCheckBox!!.isSelected)
            command += " --force"
        command.runCommand(project?.basePath)
        super.doOKAction()
    }
}
