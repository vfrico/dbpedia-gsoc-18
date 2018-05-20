---
layout: post
published: true
title: First week coding
date: '2018-05-18'
---
The 14th day of this month started the coding period on the Google Summer of Code timeline. After my _community bonding_ period of the past weeks, I have finally started developing the project itself. I am not really creative at naming my projects, so the [repository](https://github.com/vfrico/mapping-predictor-backend) I created is named describing what it is: the backend of the web application.

I've decided to start with the backend because it is the most complicated part, as it should serve the annotations for the user and store them. The language choosed to develop it is Java, as I think it will be easier to access to Weka and other machine learning libraries. In addition to this, the Java EE platform is well tested and used across the world.

Along with the server technologies I will be using, I also have checked how I will be accessing to the current mappings available at [mappings.dbpedia.org](http://mappings.dbpedia.org). DBpedia has a [repository](https://github.com/dbpedia/extraction-framework) with the code used to generate the RDF triples from different sources (mappings too, of course), and this code is written in Java and Scala. The last one is a language I haven't worked with it before, but I think that given it's popularity it deserves to take a look. This fact also reafirms my choose of Java as backend language, since it is possible to access to Scala code from Java in a native way. Note that both languages uses the JVM _under the hood_.

My work on the next weeks will be on defining the services that should be exposed to allow the webapp work seamless. 
