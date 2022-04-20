import play.api._
import play.api.mvc.EssentialFilter
import play.api.routing.Router

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
  with _root_.controllers.AssetsComponents {
  val isDev: Boolean = context.environment.mode == Mode.Dev
  private val disabledFilters: Set[EssentialFilter] = Set(allowedHostsFilter)
  override def httpFilters: Seq[EssentialFilter] = super.httpFilters.filterNot(disabledFilters.contains)

  lazy val homeController = new _root_.controllers.HomeController(controllerComponents, isDev)

  lazy val router: Router = new _root_.router.Routes(httpErrorHandler, homeController, assets)
}

