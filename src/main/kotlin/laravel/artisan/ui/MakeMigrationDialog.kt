package laravel.artisan.ui

import com.intellij.openapi.ui.Messages
import laravel.artisan.runCommand
import javax.swing.*
import java.awt.event.*

class MakeMigrationDialog : JDialog() {
    private var contentPane: JPanel? = null
    private var buttonOK: JButton? = null
    private var buttonCancel: JButton? = null
    private var migrationName: JTextField? = null
    private var actionSelect: JComboBox<String>? = null
    private var tableName: JTextField? = null

    private var workingDir: String? = ""

    init {
        setContentPane(contentPane)
        isModal = true
        getRootPane().defaultButton = buttonOK

        migrationName!!.requestFocus()

        buttonOK!!.addActionListener { onOK() }
        buttonCancel!!.addActionListener { onCancel() }
        actionSelect!!.addActionListener { onChangeAction() }

        // call onCancel() when cross is clicked
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                onCancel()
            }
        })

        // call onCancel() on ESCAPE
        contentPane!!.registerKeyboardAction(
            { onCancel() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        )

        migrationName!!.registerKeyboardAction(
            { onOK() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            JComponent.WHEN_FOCUSED
        )
    }

    private fun onChangeAction() {
        tableName!!.isEnabled = (actionSelect!!.selectedIndex != 0)
    }

    private fun onOK() {
        if (migrationName!!.text == "") {
            Messages.showMessageDialog(this, "Migration name should be specified", "Error", Messages.getErrorIcon())
            return
        }
        var command: String = "php artisan make:migration --no-interaction " + migrationName!!.text
        if (actionSelect!!.selectedIndex != 0 && tableName!!.text == "") {
            Messages.showMessageDialog(this, "Table name should be provided if you want to use table in migration or create a new table", "Error", Messages.getErrorIcon())
            return
        }
        when (actionSelect!!.selectedIndex) {
            1 -> command += " --table=" + tableName!!.text
            2 -> command += " --create=" + tableName!!.text
        }
        command.runCommand(workingDir)
        dispose()
    }

    private fun onCancel() {
        // add your code here if necessary
        dispose()
    }

    companion object {

        @JvmStatic
        fun main(workingDir: String?) {
            val dialog = MakeMigrationDialog()
            dialog.workingDir = workingDir
            dialog.title = "Make a migration"
            dialog.pack()
            dialog.setLocationRelativeTo(null)
            dialog.isVisible = true
        }
    }
}
