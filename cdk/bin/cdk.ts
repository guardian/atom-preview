import "source-map-support/register";
import { App } from "@aws-cdk/core";
import { AtomPreview } from "../lib/atom-preview";

const app = new App();
const cloudFormationStackName = process.env.GU_CFN_STACK_NAME;
new AtomPreview(app, "AtomPreview", {
  stack: "flexible",
  cloudFormationStackName,
});
