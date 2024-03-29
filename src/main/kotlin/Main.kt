import java.io.File
import java.net.URL
import java.net.URLClassLoader
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.exists
import kotlin.reflect.full.createInstance

fun main(args: Array<String>) {
    println("Hello, world!")
    println("We assume that there is no ./build/classes/kotlin/main/TestClass.class")
    println("Program arguments: ${args.joinToString()}")

    // Setting things up
    val dir = Path(args[0])
    if (!dir.exists()) {
        println("Path ${dir.absolutePathString()} does not exist. Quiting!")
        return
    }

    val parent = Thread.currentThread().getContextClassLoader()
    val local = URLClassLoader(arrayOf(URL(File(dir.absolutePathString()).toURI().toASCIIString())), parent)
    local.urLs.forEach { println(it) }

    // Loading that should fail
    try {
        val maybe = Class.forName("TestClass", true, local).kotlin
        println("This should not print!")
        val actual = maybe.createInstance() as TestInterface
    }
    catch (e: Exception) {
        println("This message should print, indeed!")
    }

    println("Now move TestClass.class to $dir")
    readln()

    // Loading that should succeed
    try {
        val maybe = Class.forName("TestClass", true, local).kotlin
        val actual = maybe.createInstance() as TestInterface
        actual.printTheMessage("Hello from dependency injected class!")
    }
    catch (e: Exception) {
        println("This message should not print!")
    }
}