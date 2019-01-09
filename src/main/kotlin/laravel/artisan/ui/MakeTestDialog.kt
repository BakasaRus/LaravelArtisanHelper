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

class MakeTestDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var testName: JTextField? = null
    private var kindComboBox: JComboBox<String>? = null
    private var kindHint: JLabel? = null

    init {
        init()
        TypeChooser.setup(testName, kindComboBox, kindHint)
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return testName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyTestName = (testName!!.text == "")
        return when {
            emptyTestName -> ValidationInfo("Test name should be specified", testName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command = "php artisan make:test --no-interaction " + testName!!.text
        if (kindComboBox!!.selectedIndex == 0)
            command += " --unit"

        val result = command.runCommand(project?.basePath)

        Notifications.Bus.notify(
            Notification("laravel", "Laravel Artisan", result!!, NotificationType.INFORMATION)
        )
        super.doOKAction()
    }
}
