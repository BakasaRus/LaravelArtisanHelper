package laravel.artisan

import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File) {
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

fun String.runCommand(workingDir: String?) {
    this.runCommand(File(workingDir))
}