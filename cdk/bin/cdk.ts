import 'source-map-support/register';
import { App } from 'aws-cdk-lib';
import { AtomPreview } from '../lib/atom-preview';

const app = new App();
new AtomPreview(app, 'AtomPreview-CODE', {
	stack: 'flexible',
	stage: 'CODE',
	minimumInstances: 1,
	domainName: 'atom-preview.code.dev-gutools.co.uk',
});
