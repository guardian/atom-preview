package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.Logging

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(cc: ControllerComponents, isDev: Boolean) extends AbstractController(cc) with Logging {
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
}
