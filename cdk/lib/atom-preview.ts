import { GuEc2App } from '@guardian/cdk';
import { AccessScope } from '@guardian/cdk/lib/constants';
import type { GuStackProps } from '@guardian/cdk/lib/constructs/core';
import { GuStack } from '@guardian/cdk/lib/constructs/core';
import { GuCname } from '@guardian/cdk/lib/constructs/dns';
import type { App } from 'aws-cdk-lib';
import { Duration } from 'aws-cdk-lib';
import { InstanceClass, InstanceSize, InstanceType } from 'aws-cdk-lib/aws-ec2';
import {bootstrapCommand} from "@guardian/cdk/lib/bin/commands/bootstrap";
import {GuAllowPolicy} from "@guardian/cdk/lib/constructs/iam";

interface AtomProps extends GuStackProps {
	domainName: string;
	minimumInstances: number;
}
export class AtomPreview extends GuStack {
	constructor(scope: App, id: string, props: AtomProps) {
		super(scope, id, props);
		const { loadBalancer } = new GuEc2App(this, {
			applicationPort: 9000,
			app: 'atom-preview',
			access: { scope: AccessScope.PUBLIC },
			instanceType: InstanceType.of(InstanceClass.T4G, InstanceSize.SMALL),
			certificateProps: {
				domainName: props.domainName,
			},
			monitoringConfiguration: {
				noMonitoring: true,
			},
			applicationLogging: {
				enabled: true
			},
			userData: {
				distributable: {
					fileName: 'atom-preview.deb',
					executionStatement: `dpkg -i /atom-preview/atom-preview.deb`,
				},
			},
			scaling: {
				minimumInstances: props.minimumInstances,
			},
			roleConfiguration: {
				additionalPolicies: [
					new GuAllowPolicy(this, "PanDomainPolicy", {
						resources: ["arn:aws:s3:::pan-domain-auth-settings/*"],
						actions: ["s3:GetObject"],
					}),
				]
			}
		});
		new GuCname(this, 'dns', {
			app: 'atom-preview',
			domainName: props.domainName,
			resourceRecord: loadBalancer.loadBalancerDnsName,
			ttl: Duration.hours(1),
		});
	}
}
