---
layout: post
title: GSoC project introduction
date: '2018-05-06 20:50:18 +0200'
categories: jekyll update
published: true
---
I have been recently selected by DBpedia to take part in the 2018 edition of the Google Summer of Code. The proposal I have presented is related to data quality and aims to create a [web application](https://summerofcode.withgoogle.com/organizations/5257820488859648/#5421604163551232) to help [DBpedia](http://mappings.dbpedia.org/) editors with the mapping process. My work will be derived from the paper that Mariano and Nandana presented on the 33rd ACM/SIGAPP Symposium On Applied Computing: "Predicting Incorrect Mappings: A Data-Driven Approach Applied to DBpedia". I want to thank them for the great involvement they have shown and the help they provided me to write a great proposal.

[DBpedia](https://dbpedia.org/) is an organization that converts the structured data on Wikipedia to a machine-readable format like RDF. This empowers computers to understand better the data available on the Internet and allows developers and enterprises to create smart tools like personal assistants or enrich a variety of pages, from Google Search to Spanish National Library open data portal.

The main source of information of DBpedia are the infoboxes on Wikipedia. But before anyone can use this information, we need to transform it into RDF. Mappings are essential on this process because relates the specific information on each Wikipedia page to an ontology property. But sometimes mappings are not consistent over different DBpedias, making different data consumers treat the information incorrectly.

The process of creating mappings can be improved, so I will be developing a web app that will show mappers the infoboxes with more mapping inconsistencies. As the system will have a supervised learning algorithm running in the background, it'll also have a training section.

On this blog, I pretend to share weekly updates on how the development is going. I will also publish different mockups and documentation on GitHub, as well as the source code. Check out this blog regularly and don't miss anything!

