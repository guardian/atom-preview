import { join } from "path";
import { CfnInclude } from "@aws-cdk/cloudformation-include";
import type { App } from "@aws-cdk/core";
import type { GuStackProps } from "@guardian/cdk/lib/constructs/core";
import { GuStack, GuStageParameter } from "@guardian/cdk/lib/constructs/core";

export class AtomPreview extends GuStack {
  constructor(scope: App, id: string, props: GuStackProps) {
    super(scope, id, props);
  }
}
