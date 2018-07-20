---
layout: post
published: true
title: Adding docs
date: '2018-07-13'
---

On this second month of coding, as some endpoints are already working, I've started documenting the API using ReST and Sphinx, which has a very useful module that allows to add detailed information about every endpoint and examples, as well as different status codes that must respond.

This documenting framework allows to *compile* the documentation to multiple formats, as HTML or PDF. I could use any service like Read the docs to publish this information, but I've decided to use GitHub pages to make the HTML files available as a static web page.

Although this could be the most signigicant change, I've been also working on improving the voting methods, as well as all the needed endpoints to "install" the application, that is, add all the needed tables on the database and basic information that makes possible the application to work, like voting types.
