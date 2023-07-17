package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.Logging
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.libs.ws.WSRequest
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(cc: ControllerComponents, isDev: Boolean, ws: WSClient) extends AbstractController(cc) with Logging {
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    logger.info("Application is ready")
    Ok(views.html.index("Your new application is ready.", isDev))
  }

  def capiFinder(id: String) = Action {
    Ok(s"The ID searched: $id")
  }

  def logTestString(testId: String) = Action.async { implicit request =>
    val request = ws.url("https://composer.code.dev-gutools.co.uk/support/content-api/atom/qanda/5295aa48-f10f-448c-9326-79e365b24ed9?show-fields=isLive,firstPublicationDate,headline,trailText,byline,thumbnail,score,standfirst")
    val testResult = request.get().map { response =>
      val json = response.json
      val logMessage = s"Logged test string: $testId, $json"
      logger.info(logMessage)
      Ok(json)
    }

    testResult.map(result => result)
  }
}
