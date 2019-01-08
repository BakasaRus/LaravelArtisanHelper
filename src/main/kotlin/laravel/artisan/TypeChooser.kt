package laravel.artisan

import com.intellij.lang.properties.ResourceBundle
import com.intellij.util.PlatformIcons
import java.awt.event.KeyEvent
import javax.swing.*
import com.intellij.uiDesigner.*

class TypeChooser (
    private var tf: JTextField?,
    private var cb: JComboBox<*>?,
    private var il: JLabel?) {

    init {
        il!!.icon = PlatformIcons.UP_DOWN_ARROWS
        il!!.toolTipText = "Pressing Up and Down arrows while in editor changed the kind"

        tf!!.registerKeyboardAction(
            { cb!!.selectedIndex = (cb!!.selectedIndex + 1) % cb!!.itemCount },
            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
            JComponent.WHEN_FOCUSED
        )

        tf!!.registerKeyboardAction(
            { cb!!.selectedIndex = (cb!!.selectedIndex + cb!!.itemCount - 1) % cb!!.itemCount },
            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
            JComponent.WHEN_FOCUSED
        )
    }

    companion object {
        @JvmStatic
        fun setup(tf: JTextField?, cb: JComboBox<*>?, il: JLabel?) {
            TypeChooser(tf, cb, il)
        }
    }
}