import com.gu.pandomainauth.PanDomainAuthSettingsRefresher
import play.api._
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import config.Config
import controllers._

import play.api.libs.ws.ahc.AhcWSComponents

class MyApplicationLoader extends ApplicationLoader {
  private var components: MyComponents = _

  def load(context: ApplicationLoader.Context): Application = {
    components = new MyComponents(context)
    components.application
  }
}

class MyComponents(context: ApplicationLoader.Context) 
  extends BuiltInComponentsFromContext(context)
  with play.filters.HttpFiltersComponents
    with AhcWSComponents
  with _root_.controllers.AssetsComponents {
  private val disabledFilters: Set[EssentialFilter] = Set(allowedHostsFilter)
  override def httpFilters: Seq[EssentialFilter] = super.httpFilters.filterNot(disabledFilters.contains)

  val config = new Config(context)
  val panDomainRefresher = new PanDomainAuthSettingsRefresher(
    domain = config.domain,
    system = config.pandaSystem,
    bucketName = config.pandaBucketName,
    settingsFileKey = config.pandaSettingsFile,
    s3Client = config.S3Client
  )
  lazy val homeController = new _root_.controllers.HomeController(controllerComponents, config.isDev)
  lazy val login = new _root_.controllers.Login(config, controllerComponents, wsClient, panDomainRefresher )
  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, homeController, assets, login)

}
