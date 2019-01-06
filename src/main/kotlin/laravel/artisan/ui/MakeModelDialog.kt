package laravel.artisan.ui

import laravel.artisan.runCommand
import javax.swing.*
import java.awt.event.*

class MakeModelDialog : JDialog() {
    private var contentPane: JPanel? = null
    private var buttonOK: JButton? = null
    private var buttonCancel: JButton? = null
    private var modelName: JTextField? = null
    private var migrationCheckBox: JCheckBox? = null
    private var factoryCheckBox: JCheckBox? = null
    private var controllerCheckBox: JCheckBox? = null
    private var resControllerCheckBox: JCheckBox? = null
    private var pivotCheckBox: JCheckBox? = null
    private var forceCheckBox: JCheckBox? = null

    private var workingDir: String? = ""

    init {
        setContentPane(contentPane)
        isModal = true
        getRootPane().defaultButton = buttonOK

        buttonOK!!.addActionListener { onOK() }

        buttonCancel!!.addActionListener { onCancel() }

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
    }

    private fun onOK() {
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
            val dialog = MakeModelDialog()
            dialog.workingDir = workingDir
            dialog.title = "Make a model"
            dialog.pack()
            dialog.setLocationRelativeTo(null)
            dialog.isVisible = true
        }
    }
}
