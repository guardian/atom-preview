// This file was autogenerated when creating cdk.ts using @guardian/cdk-cli
// It is a starting point for migration to CDK *only*. Please check the output carefully before deploying

import "source-map-support/register";
import { App } from "@aws-cdk/core";
import { AtomPreview } from "../lib/atom-preview";

const app = new App();
//  TODO: Add stack name
//   e.g. { stack: "SomeStack" }
new AtomPreview(app, "Flexible", { stack: "flexible" });
