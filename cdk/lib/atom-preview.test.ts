import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { AtomPreview } from './atom-preview';

describe('The AtomPreview stack', () => {
	it('matches the snapshot', () => {
		const app = new App();
		const stack = new AtomPreview(app, 'AtomPreview', {
			stack: 'flexible',
			stage: 'PROD',
			minimumInstances: 1,
			domainName: 'atom-preview.gutools.co.uk',
		});
		const template = Template.fromStack(stack);
		expect(template.toJSON()).toMatchSnapshot();
	});
});
