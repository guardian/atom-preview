package config

import com.amazonaws.auth.{AWSCredentialsProviderChain, DefaultAWSCredentialsProviderChain}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import play.api.{ApplicationLoader, Mode}

class Config(context: ApplicationLoader.Context) extends {
  val isDev: Boolean = context.environment.mode == Mode.Dev
  lazy val no2faUser: String = "composer.test@guardian.co.uk"
  lazy val domain: String = stage match {
    case "PROD" => s"gutools.co.,uk"
    case "CODE" => s"code.dev-gutools.co.uk"
    case _ => s"local.dev-gutools.co.uk"
  }
  lazy val host: String = s"https://atom-preview.$domain"
  lazy val pandaSystem: String = "atom-preview"
  lazy val pandaBucketName: String = "pan-domain-auth-settings"
  lazy val pandaSettingsFile: String = s"$domain.settings"
  lazy val credentialsProvider = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider("composer"),
    new DefaultAWSCredentialsProviderChain()
  )
  lazy val region: Region = Region getRegion Regions.EU_WEST_1
  lazy val S3Client: AmazonS3 = {
    AmazonS3ClientBuilder
      .standard()
      .withCredentials(credentialsProvider)
      .withRegion(region.getName)
      .build()
  }
}
