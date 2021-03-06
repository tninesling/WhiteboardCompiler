package services

import java.io.File
import javax.tools._

import scala.collection.JavaConversions._
import scala.util.{Try, Success, Failure}

/** The Compiler Service offers in-program file compilation using the
  * Java Compiler API
  */
class CompilerService {

  def compileFromURI(uri: String): String = {
    val file = Try(new File(uri))

    file match {
      case Success(s) =>
        compile(Set(s))
      case Failure(e) =>
        e.getMessage
    }
  }

  /** Returns a string with the error message from the compiler. If the file
    * is compiled successfully, an empty string will be returned
    */
  def compile(files: Iterable[File]): String = {
    val compiler = ToolProvider.getSystemJavaCompiler()
    val fileManager = compiler.getStandardFileManager(null, null, null)
    val compilationUnit = fileManager.getJavaFileObjectsFromFiles(asJavaIterable(files))
    val diagnostics = new DiagnosticCollector[JavaFileObject]()

    compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnit)
            .call()

    val strBld = new StringBuilder

    diagnostics.getDiagnostics foreach { d =>
      strBld.append("Error on line ")
            .append(d.getLineNumber)
            .append(" in ")
            .append(d.getSource.toUri)
            .append("\n")
            .append(d.getMessage(null))
    }

    fileManager.close()

    strBld.mkString
  }
}
