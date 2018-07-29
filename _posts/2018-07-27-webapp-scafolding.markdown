---
layout: post
published: true
title: Web app scaffolding
date: '2018-07-27'
---

At this advanced stage of development, I've started to create the web application. It is built using react and redux, starting with a boilerplate.

React is a modern library to manage and update the view components on a web application, and redux is a library used to manage the state of the app. The boilerplate comes with many other libraries that allows routing or manage async calls to API's.

Along this week, the work has been focused on creating the skeleton of the web and making the interaction between components and actions. The api backend has been also changed, mainly to use a connection pool to make queries to the database, instead of opening and closing one connection once. This approach is expected to reduce the query times, as the connections are reused and the overhead by opening and closing multiple times is heavily reduced.
