package laravel.artisan.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import laravel.artisan.ui.MakeMigrationDialog as Dialog

class MakeMigration : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Dialog.main(e.project?.basePath)
    }
}