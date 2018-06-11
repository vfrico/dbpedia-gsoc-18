---
layout: post
published: true
title: First week coding
date: '2018-06-07'
---
# Review of my first month on GSoC
After this four weeks since coding started on this edition of Google Summer of Code, and especially on the last week, I have learned how DBpedia works and releases new versions. Also I have learned to create a server using Virtuoso and replicate the DBPedia's SPARQL endpoint.

On this last week, I've also meet with my mentors on DBpedia and talked about my progress. We have decided that I should foucs more on my results rather than on the infrastructure part.

I've been also anotating more examples to add extra instances to the original dataset. This is an important task because if we success on adding more instances and getting the same accuracy, we can ensure that the application to detect incorrect mappings will work as expected.

At the moment of writing, the tests have shown a light decrease on accuracy with the Random Trees classifier, but this can be because I couldn't replicate at all the same metrics on the dataset offered on the paper.

To solve this last issue, I am working with one of my mentors, Nandana, and he is helping me to build correctly a dataset with correct metrics.

Anyway, there is no reason to alarm, because although the precission is lower, is still an acceptable value.

So far, I've got the following results:

| Classifier | Correct % | Precission | Recall | AUC ROC | Comments |
| --- | --- | --- | --- |
| Random Forest | 86.7% | 0.724 | 0.656 | 0.914 | default params in weka |
| MultiLayerPerceptron | 82.5% | 0.6 | 0.656 | 0.83 | L=0.3, M=0.12, H="a,18", N=5000 |

If we look at the area under the ROC curve, results are quite similar to the ones published on the original paper: 0.91 vs 0.96.