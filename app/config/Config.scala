package config

import com.amazonaws.auth.{AWSCredentialsProvider, AWSCredentialsProviderChain, DefaultAWSCredentialsProviderChain, STSAssumeRoleSessionCredentialsProvider}
import software.amazon.awssdk.auth.credentials.{AwsCredentialsProvider, ProfileCredentialsProvider => AtomPreviewAwsCredentialsProvider }
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.gu.conf.{ConfigurationLoader, SSMConfigurationLocation}
import com.gu.{AppIdentity, AwsIdentity, DevIdentity}
import io.opencensus.common.ToDoubleFunction
import play.api.{ApplicationLoader, Mode}

import scala.util.Success


class Config(context: ApplicationLoader.Context) {
  val isDev: Boolean = context.environment.mode == Mode.Dev
  lazy val no2faUser: String = "composer.test@guardian.co.uk"
  lazy val domain: String = "local.dev-gutools.co.uk"
  lazy val host: String = s"https://atom-preview.$domain"
  lazy val pandaSystem: String = "atom-preview"
  lazy val pandaBucketName: String = "pan-domain-auth-settings"
  lazy val pandaSettingsFile: String = s"$domain.settings"
  lazy val credentialsProvider = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider("composer"),
    new DefaultAWSCredentialsProviderChain()
  )
  val pandaDomain: String = domain
  val pandaAuthCallback: String = s"https://atomworkshop.$domain/oauthCallback"
  lazy val region: Region = Region getRegion Regions.EU_WEST_1
  lazy val S3Client: AmazonS3 = {
    AmazonS3ClientBuilder
      .standard()
      .withCredentials(credentialsProvider)
      .withRegion(region.getName)
      .build()
  }

  val awsCredentialsProviderAtomPreview: AtomPreviewAwsCredentialsProvider = AtomPreviewAwsCredentialsProvider.builder()
      .profileName("composer")
      .build();
  val CredentialsProvider: AwsCredentialsProvider = {
    awsCredentialsProviderAtomPreview
  }

  val ssmConfig =
    for {
      identity <- if (isDev)
        Success(AwsIdentity("atom-preview", "Composer", "DEV", "eu-west-1"))
      else
        AppIdentity.whoAmI(defaultAppName = "support-frontend", CredentialsProvider)
      config = ConfigurationLoader.load(identity, CredentialsProvider) {
        case identity: AwsIdentity => SSMConfigurationLocation.default(identity)
      }
    } yield config

  // Todo - this is a bit of a hack, we should be using config.string
  val capiApiKey: String = ssmConfig.get.getString("capiApiKey")
  val capiPreviewIAMUrl: String = ssmConfig.get.getString("capiPreviewIAMUrl")
  val capiLiveUrl: String = ssmConfig.get.getString("capiLiveUrl")
  val capiPreviewRole: String = ssmConfig.get.getString("capiPreviewRole")
  val capiPreviewCredentials: AWSCredentialsProvider = {
    new AWSCredentialsProviderChain(
      new ProfileCredentialsProvider("composer"),
      new STSAssumeRoleSessionCredentialsProvider.Builder(capiPreviewRole, "capi-preview").build()
    )
  }
}
