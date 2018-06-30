---
layout: post
published: true
title: Backend REST implementation and first tests with Docker
date: '2018-06-29'
---

On this week, the project is going forward. I have started to put all the backend on a Docker container, to facilitate the deployment on the future. Once this configuration has been finished, I will be able to work more focused on the code, as I could run the application easily wherever I need.

Apart from *dockerising* the backend, I also have been adding the MySQL connectivity. The MySQL server will be a component inside the system, so is an additional Docker container.

I also have some comments to do about the next DBpedia release. On the Slack channel, we are discussing the different datasets that will be available to download and the changes with respect to the previous one, which has about one year and a half old.

The main problem is that the original .tql files that were available on each release, on this one we will not have them available. This is a problem we have to deal if we want to have our system ready for the last DBpedia release. We still have to decide if we build those files by ourselves, or instead, we look for another way to get the same information.

We also have to ensure that the [endpoint](http://mappings.dbpedia.org/server/mappings/en/pages/rdf/all) that generates the RML mappings will be available and stable for a reasonable time. If not, instead, we could look for ways to extract that information from the [Git repository](https://github.com/dbpedia/mappings-tracker) which is updated for each DBpedia release.
