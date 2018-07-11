---
layout: post
published: false
title: Combining MySQL and Jersey API
date: '2018-07-06'
---

On this week I have centered my efforts on connecting MySQL to the backend, including the creation of all the required tables. Given that this system is expected to run into a container and connected with MySQL inside another container, I have also started to parametrize constants related to MySQL access, that can be modified through a configuration file or using system variables.

I have started to cleaning the code on the Java classes that manage the HTTP connections, so that it can be more readable and efficient.

At this moment, the backend has the method to add and retrieve annotations fully working, allowing anyone to read and add annotations. In a short period of time, I expect to have working the endpoint that allows an user to vote for an annotation.

It is also possible to download all the annotations for a language pair in csv format, to play with the dataset in Weka easily.
