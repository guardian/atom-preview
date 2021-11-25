package controllers

import javax.inject.Inject
import util.{AWSConfig}
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))

    val jsFileName = "atom-preview/public/frontend/static/js/my-atom-preview-app.js"
    val autotrackJsLocation = routes.Assets.versioned("atom-preview/public/frontend/static/js/my-atom-preview-app.js").toString

    val jsAssetHost = sys.env.get("JS_ASSET_HOST")

    val isHotReloading = jsAssetHost match {
      case Some(_) if awsConfig.isDev => true
      case _ => false
    }

    val jsLocation = if (isHotReloading) {
      jsAssetHost.get + jsFileName
    } else {
      routes.Assets.versioned(jsFileName).toString
    }
  }

}
