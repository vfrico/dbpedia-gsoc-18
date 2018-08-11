---
layout: post
published: true
title: Adding languages to web application
date: '2018-08-10'
---

On this week, the web app has gone through many changes. One of them is the addition of roles. One user can have four different functions:

* The first role is the default role, which has any real power. An admin user must upgrade his role to one with more power.
* The second is the mapper role. It can read the annotations and the votes that all the users have done, but he can't add any vote. Instead, he can lock the edition of mapping to solve on the [DBpedia mappings](http://mappings.dbpedia.org/index.php/Main_Page) wiki page, and release after the edition is done.
* The third role is used to add more votes to the annotations. It is essential that a user understand what an annotation is, so its role must be upgraded manually.
* The last role is the admin role. It can train the annotations on the system and add the classification for the unvoted mappings.

The second important change is related to the languages. The application from the first conception was thought to be used for many languages, not only English - Spanish, so to ensure that the app works with this approach, I've added three more languages: Greek, Dutch and German.

In addition to roles and languages, the web app has suffered some subtle changes to provide more information to annotator users, such as finding related entities or UI improvements.

The web application can be tested from this URI. It may change on the following days, though:

[http://35.187.57.43:3000/](http://35.187.57.43:3000/)

On the next week, I plan to document everything and deliver a document to my GSoC page.
