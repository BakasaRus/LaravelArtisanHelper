package laravel.artisan.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import laravel.artisan.TypeChooser
import laravel.artisan.runCommand
import javax.swing.*

class MakeMigrationDialog(private var project: Project?) : DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var migrationName: JTextField? = null
    private var kindComboBox: JComboBox<String>? = null
    private var tableName: JTextField? = null
    private var kindHint: JLabel? = null

    init {
        init()
        kindComboBox!!.addItemListener {
            tableName!!.isEditable = (kindComboBox!!.selectedIndex != 0)
        }
        TypeChooser.setup(migrationName, kindComboBox, kindHint)
    }

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return migrationName
    }

    override fun doValidate(): ValidationInfo? {
        val emptyMigrationName = migrationName!!.text == ""
        val emptyTableName = kindComboBox!!.selectedIndex != 0 && tableName!!.text == ""
        return when {
            emptyMigrationName -> ValidationInfo("Migration name should be specified", migrationName)
            emptyTableName -> ValidationInfo("Table name should be specified if you create migration for the table", tableName)
            else -> null
        }
    }

    override fun doOKAction() {
        var command: String = "php artisan make:migration --no-interaction " + migrationName!!.text
        when (kindComboBox!!.selectedIndex) {
            1 -> command += " --table=" + tableName!!.text
            2 -> command += " --create=" + tableName!!.text
        }
        command.runCommand(project?.basePath)
        super.doOKAction()
    }
}
