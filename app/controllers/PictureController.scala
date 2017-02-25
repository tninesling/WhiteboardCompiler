package controllers

import java.io.File
import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class PictureController @Inject() extends Controller {
  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      val filename = picture.filename
      picture.ref.moveTo(new File(s"/tmp/picture/$filename"))
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.HomeController.index).flashing(
        "error" -> "Missing file"
      )
    }
  }
}
