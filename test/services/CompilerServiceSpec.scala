package tests.services

import services.CompilerService

import org.scalatest.FlatSpec
import org.scalatest.Matchers

import scala.io.Source

class CompilerServiceSpec extends FlatSpec with Matchers {
  "The compile method" should "run without errors because I said so" in {
    val uri = s"tmp/files/javaFile.java"
    val compiler = new CompilerService
    compiler.compileFromURI(uri)
  }
  /*it should "print a line starting with \"Error\"" in {
    val uri = s"tmp/files/badJavaFile.java"
    CompilerService.compileFromURI(uri)
    Source.fromFile(System.out).getLines.foreach { println }
  }*/
}
