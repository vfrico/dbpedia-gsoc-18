---
layout: post
published: true
title: Users and login management
date: '2018-07-20'
---
I'm planning to implement next week a very simple version of the web application, so to achieve this I need to finish before the users management part.

On this week I've been implementing all the methods to add, delete and modify users on the database. Also, I've been exploring different alternatives to manage the login information, and I've decided to go with a session token called JWT.

![JWT logo]( https://jwt.io/img/logo-asset.svg "Javascript Web Token: JWT")

This token is thought to store login information such username and email as a encrypted and encoded string, making the solution secure to store as a cookie on the client and send as a HTTP header. As the token is always signed, it is possible to prevent *illegal* changes.

The login status will be stored on the database on a single row, and compared against the sent token to proof that the user is correctly logged in on the system and there is anyone that is trying to break the system.
