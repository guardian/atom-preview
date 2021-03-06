package controllers

import com.gu.pandomainauth.action.AuthActions
import com.gu.pandomainauth.model.AuthenticatedUser
import config.Config
import play.api.{Logger, Logging}
import play.api.mvc._

trait PanDomainAuthActions extends AuthActions with Results with Logging {
  def config: Config

  override def validateUser(authedUser: AuthenticatedUser): Boolean = {
    (authedUser.user.emailDomain == "guardian.co.uk") &&
      (authedUser.multiFactor || (config.no2faUser.length > 0 && config.no2faUser == authedUser.user.email))
  }

  override def authCallbackUrl: String = config.host + "/oauthCallback"

  override def showUnauthedMessage(message: String)(implicit request: RequestHeader): Result = {
    logger.info(message)
    Ok((message))
  }

  override def invalidUserMessage(claimedAuth: AuthenticatedUser): String = {
    if( (claimedAuth.user.emailDomain == "guardian.co.uk") && !claimedAuth.multiFactor) {
      s"${claimedAuth.user.email} is not valid for use with config.pandaSystem as you need to have two factor authentication enabled." +
        s" Please contact the Helpdesk by emailing 34444@theguardian.com or calling 34444 and request access to Composer CMS tools"
    } else {
      s"${claimedAuth.user.email} is not valid for use with config.pandaSystem. You need to use your Guardian Google account to login. Please sign in with your Guardian Google account first, then retry logging in"
    }
  }
}

