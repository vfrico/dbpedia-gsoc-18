---
layout: post
published: true
title: Web app is alive!
date: '2018-08-03'
---

It has not been easy, but, finally, we got a web app available online. It is still a work in progress, because the development has not yen finished, but it can be used to make yourself an idea of how the app would work.

You can check it out online [here](http://35.187.57.43:3000/).

Currently, the user management part is completely finished. The system that tracks and stores all the votes that a user makes to an annotation is also working, but it may need some visual improvements.

![Web app screenshot]( https://raw.githubusercontent.com/vfrico/dbpedia-gsoc-18/gh-pages/img/webapp-1.png "Web app screenshot")


I am still working on the data that will be shown for each template. I am not really sure what is the most useful information that any user could be interested on.

Behind the scenes, I am creating the service that is trained with the user annotations and classifies each mapping as correct or wrong. It will be based on a Random Forest classifier, built on top of Weka, as it shows to be the most performant algorithm [\[1\]](Predicting Incorrect Mappings: A Data-Driven Approach Applied to DBpedia) [\[2\]](https://vfrico.github.io/dbpedia-gsoc-18/2018-06-07-review-of-first-month/)
