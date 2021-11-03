// This file was autogenerated using @guardian/cdk-cli

import "@aws-cdk/assert/jest";
import { SynthUtils } from "@aws-cdk/assert";
import { App } from "@aws-cdk/core";
import { AtomPreview } from "./atom-preview";

describe("The AtomPreview stack", () => {
  it("matches the snapshot", () => {
    const app = new App();
    const stack = new AtomPreview(app, "atom-preview", { stack: "flexible" });
    expect(SynthUtils.toCloudFormation(stack)).toMatchSnapshot();
  });
});
