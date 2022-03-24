import type { App } from "@aws-cdk/core";
import type { GuStackProps } from "@guardian/cdk/lib/constructs/core";
import { GuStack } from "@guardian/cdk/lib/constructs/core";
import { GuEc2App} from "@guardian/cdk";
import {AccessScope, Stage} from "@guardian/cdk/lib/constants";
import {InstanceClass, InstanceSize, InstanceType} from "@aws-cdk/aws-ec2";

export class AtomPreview extends GuStack {
  constructor(scope: App, id: string, props: GuStackProps) {
    super(scope, id, props);
    new GuEc2App(this, {
      applicationPort: 1234,
      app: "atom-preview",
      access: { scope: AccessScope.PUBLIC },
      instanceType: InstanceType.of(InstanceClass.T4G, InstanceSize.SMALL),
      certificateProps:{
        [Stage.CODE]: {
          domainName: "atom-preview.code.dev-gutools.co.uk",
        },
        [Stage.PROD]: {
          domainName: "atom-preview.gutools.co.uk",
        },
      },
      monitoringConfiguration: {
        noMonitoring: true,
      },
      userData: {
        distributable: {
          fileName: "atom-preview.deb",
          executionStatement: `dpkg -i /atom-preview/atom-preview.deb`,
        }
      },
      scaling: {
        [Stage.CODE]: {
          minimumInstances: 1
        },
        [Stage.PROD]: {
          minimumInstances: 1
        }
      }
    });
  }
}
