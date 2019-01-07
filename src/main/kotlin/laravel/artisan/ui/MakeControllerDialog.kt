package laravel.artisan.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import javax.swing.*
import laravel.artisan.runCommand

class MakeControllerDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var controllerName: JTextField? = null
    private var modelName: JTextField? = null
    private var typeComboBox: JComboBox<String>? = null

    init {
        init()
        typeComboBox!!.addItemListener {
            modelName!!.isEnabled = (typeComboBox!!.selectedIndex == 2)
        }
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return controllerName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyControllerName = controllerName!!.text == ""
        val emptyModelName = typeComboBox!!.selectedIndex == 2 && modelName!!.text == ""
        return when {
            emptyControllerName -> ValidationInfo("Controller name should be specified", controllerName)
            emptyModelName -> ValidationInfo("Model name should be specified if you create resource controller for the model", modelName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command: String = "php artisan make:controller --no-interaction " + controllerName!!.text
        when (typeComboBox!!.selectedIndex) {
            1 -> command += " --resource"
            2 -> command += " --model=" + modelName!!.text
        }
        command.runCommand(project?.basePath)
        super.doOKAction()
    }
}