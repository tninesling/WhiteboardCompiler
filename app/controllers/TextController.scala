package controllers

import services.{CompilerService, RunnerService}
import java.io._
import javax.inject._
import play.api._
import play.api.mvc._
import scala.util.{Try, Success, Failure}

@Singleton
class TextController @Inject()(compiler: CompilerService, runner: RunnerService) extends Controller {
  def compileText(programText: String) = Action {
    val maybeFile = textToJavaFile(programText)
    val compileResult = compileFile(maybeFile)

    if (compileResult.isEmpty)
      Ok(runner.runProgram("Test"))
    else
      Ok(compileResult)
  }

  def textToJavaFile(programText: String): Option[File] = {
    val maybeFile = Try(new File("tmp/files/Test.java"))

    maybeFile match {
      case Success(file) => {
        writeTextToFile(file, programText)
      }
      case Failure(e) =>
        None
    }
  }

  def writeTextToFile(file: File, text: String): Option[File] = {
    val bw = Try(new BufferedWriter(new FileWriter(file)))

    bw match {
      case Success(writer) => {
        writer.write(text)
        writer.close()
        Some(file)
      }
      case Failure(e) =>
        None
    }
  }

  def compileFile(maybeFile: Option[File]) = {
    maybeFile match {
      case Some(f) =>
        compiler.compile(Set(f))
      case None =>
        "No file"
    }
  }
}
