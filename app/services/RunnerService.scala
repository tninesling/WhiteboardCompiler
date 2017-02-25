package services

import java.io.File
import scala.io._
import scala.util.{Try, Success, Failure}

/** Class used to run compiled Java programs and capture the output
  * generated during Runtime
  */
class RunnerService {
  def runProgram(programName: String) = {
    val cmd = "java -classpath . " + programName

    val proc = Try(Runtime.getRuntime().exec(cmd, null, new File("tmp/files/")))

    proc match {
      case Success(s) =>
        captureRunOutput(s)
      case Failure(e) =>
        e.getMessage
    }
  }

  def captureRunOutput(proc: Process): String = {
    val bre = Try(Source.fromInputStream(proc.getErrorStream))
    val bri = Try(Source.fromInputStream(proc.getInputStream))

    (bre, bri) match {
      case (Success(s1: BufferedSource), Success(s2: BufferedSource)) =>
        proc.waitFor
        getReaderOutput(s1, s2)
      case _ =>
        "Error getting reader for process execution"
    }
  }

  def getReaderOutput(br1: BufferedSource, br2: BufferedSource): String = {
    if (!br1.isEmpty)
      br1.getLines.mkString("\n")
    else
      br2.getLines.mkString("\n")
  }
}
