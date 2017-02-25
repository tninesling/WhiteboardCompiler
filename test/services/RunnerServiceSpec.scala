package tests.services

import services.RunnerService

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class RunnerServiceSpec extends FlatSpec with Matchers {
  "The runner service" should "produce output" in {
    val runner = new RunnerService
    println(runner.runProgram("javaFile"))
  }
}
