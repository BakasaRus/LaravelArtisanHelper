package laravel.artisan.ui

import javax.swing.*
import java.awt.event.*
import laravel.artisan.runCommand

class MakeController : JDialog() {
    private var contentPane: JPanel? = null
    private var buttonOK: JButton? = null
    private var buttonCancel: JButton? = null
    private var controllerName: JTextField? = null
    private var isResController: JCheckBox? = null
    private var modelName: JTextField? = null

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
        var command: String = "php artisan make:controller --no-interaction " + controllerName!!.text
        if (isResController!!.isSelected)
            if (modelName!!.text != "")
                command += " --model=" + modelName!!.text
            else
                command += " --resource"
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
            val dialog = MakeController()
            dialog.workingDir = workingDir
            dialog.pack()
            dialog.isVisible = true
            // System.exit(0)
        }
    }
}
