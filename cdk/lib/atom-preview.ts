// This file was autogenerated using @guardian/cdk-cli

import type { App } from "@aws-cdk/core";
import type { GuStackProps } from "@guardian/cdk/lib/constructs/core";
import { GuStack } from "@guardian/cdk/lib/constructs/core";
import {AccessScope, GuEc2App} from "@guardian/cdk";
import {InstanceClass, InstanceSize, InstanceType} from "@aws-cdk/aws-ec2";
import {Stage} from "@guardian/cdk/lib/constants";

export class AtomPreview extends GuStack {
  constructor(scope: App, id: string, props: GuStackProps) {
    super(scope, id, props);
    new GuEc2App(this, {
      applicationPort: 1234,
      app: "app-name",
      access: { scope: AccessScope.PUBLIC },
      instanceType: InstanceType.of(InstanceClass.T4G, InstanceSize.MEDIUM),
      certificateProps:{
        [Stage.CODE]: {
          domainName: "code-guardian.com",
          hostedZoneId: "id123",
        },
        [Stage.PROD]: {
          domainName: "prod-guardian.com",
          hostedZoneId: "id124",
        },
      },
      monitoringConfiguration: {
        snsTopicName: "alerts-topic-for-my-team",
        http5xxAlarm: {
          tolerated5xxPercentage: 1,
        },
        unhealthyInstancesAlarm: true,
      },
      userData: {
        distributable: {
          fileName: "app-name.deb",
          executionStatement: `dpkg -i /app-name/app-name.deb`,
        }
      },
    });
  }
}
