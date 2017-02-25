package tests.services

import services.CompilerService

import org.scalatest.FlatSpec
import org.scalatest.Matchers

import scala.io.Source

class CompilerServiceSpec extends FlatSpec with Matchers {
  "The compile method" should "run without errors because I said so" in {
    val uri = s"C:/Users/Taylor/Programs/WhiteboardCompiler/tmp/files/javaFile.java"
    CompilerService.compileFromURI(uri)
  }
  /*it should "print a line starting with \"Error\"" in {
    val uri = s"C:/Users/Taylor/Programs/WhiteboardCompiler/tmp/files/badJavaFile.java"
    CompilerService.compileFromURI(uri)
    Source.fromFile(System.out).getLines.foreach { println }
  }*/
}
