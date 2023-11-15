# Improving the UX of Atoms in Composer: `atom-preview`

This project aims to `improve the UX of embedding atoms into Composer, specifically reducing the number of clicks needed to understand how the atom will appear within an article once published.

Currently, once an atom is embedded, the defaultHtml is rendered. The defaultHtml is very basic, with no styling or JS, and looks like this:


<img width="400" alt="Screenshot 2023-11-15 at 18 57 38" src="https://github.com/guardian/atom-preview/assets/49187886/6e53abe2-e768-40cb-a5be-d465fa22d70f">

##### To see how it would render, we have to preview the article.

<img width="400" alt="Screenshot 2023-11-15 at 18 58 58" src="https://github.com/guardian/atom-preview/assets/49187886/01acee95-18b1-4ff9-b1a8-f4457aa51ee5">


#### What if Composer rendered the atom in a more WYSIWYG way?

Proposal
Create a separate service that, given an atom ID, it will use the atoms-rendering library to render the atom. Then have Composer embed an iframe of this service in place of the defaultHtml.

#### Why a separate service, vs. adding atoms-rendering as a dependency to Composer?
Composer’s code base is already complex.

Having this as a separate service, other tools can benefit, for example, Atom Workshop, or Media Atom Maker. Currently these do not render the atom at all, they just present some editable fields with minimal styling.

<img width="400" alt="Screenshot 2023-11-15 at 19 00 43" src="https://github.com/guardian/atom-preview/assets/49187886/61e3e432-52eb-4127-bcbf-caf6127665a7">

If one wants to understand how an atom would appear online, they have to embed it into an article.

#### How to build this?
The atom-preview repository starts to do this. It is a Scala Play app, with a React frontend. It’s still a prototype. The aim is to take an atom ID, fetch the atom from CAPI, then pass it through atom-preview.

This service should also authenticate users as it will be capable of showing preview content.

#### What changes would be needed in Composer (/Atom Workshop/Media Atom Maker)?
Not much! We think the changes would be minimal, changing srcdoc to src with src referring to our external service.

#### What’s left to do in atom-preview?
Atom Preview currently has some routes to return an atom’s JSON from CAPI. Outstanding work includes:
Given a CAPI response, render the atom using atom-rendering
Allow Composer, Atom Workshop etc. to embed atom-preview via an iframe
Demo to Editorial

Atom Preview is a Scala Play app, with a React frontend. It uses create-react-app, which has been deprecated. Before going to production, we should update all dependencies. This might involve swapping some out.

