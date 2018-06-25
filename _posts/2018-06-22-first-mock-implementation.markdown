---
layout: post
published: true
title: First REST Server implementation
date: '2018-06-22'
---
On this 7th week, I have been working on implementing the REST services that will support all the web application. I have mock documentation of each one of the services on this [document](https://docs.google.com/document/d/1iR06rMcFrY0SMPCbucXmjl-plx7YQuaG9KwVTHXBtBM/edit?usp=sharing). Sorry, it is in Spanish, but I use it as a guide. I plan to publish more detailed documentation with examples of each endpoint.

I have also done an Entity Relation diagram, as we will use a relational database like MySQL to store the user annotations. This process is essential, as we need to plan this kind of things before to start coding and discovering a problem tough to solve without changing the whole project foundations.

![Entity-relation diagram](https://raw.githubusercontent.com/vfrico/dbpedia-gsoc-18/gh-pages/img/img/entity-relation.png)

The whole service is being coded with Java, as is an open source language, is easy to use and has many useful libraries that can be connected easily with our project. I expect that this circumstance also helps us to evaluate each mapping with a classifier that depends on Weka. You can check out the code on the specific Github [repository](https://github.com/vfrico/mapping-predictor-backend) of this project.
