package config

import com.amazonaws.auth.{AWSCredentialsProviderChain, DefaultAWSCredentialsProviderChain}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import play.api.{ApplicationLoader, Mode}

class Config(context: ApplicationLoader.Context) {
  val isDev: Boolean = context.environment.mode == Mode.Dev
  lazy val no2faUser: String = "composer.test@guardian.co.uk"
  lazy val domain: String = "local.dev-gutools.co.uk"
  lazy val host: String = s"https://atom-preview.$domain"
  lazy val pandaSystem: String = "workflow"
  lazy val pandaBucketName: String = "pan-domain-auth-settings"
  lazy val pandaSettingsFile: String = s"$domain.settings"
  lazy val credentialsProvider = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider("workflow"),
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
