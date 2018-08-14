# Final GSoC 2018 submission

## Work done
* Bash scripts and other resources that help to generate RDF graphs to load in Virtuoso
* Refinement of code from previous Nandana's Work to generate pairs of annotation from DBpedia mappings and SPARQL queries:
    * The code has been tested and updated, to ensure it still works.
* Creation of a Java API that allows storing annotations, users can vote them and can be classified as correct or wrong by using Random Forest's Weka classifier.
* Creation of a Web application that consumes the API and exposes an easy interface for users to help in the annotation and mapping process.
* A weekly [blog](https://vfrico.github.io/dbpedia-gsoc-18/) can be consulted. It contains the work done week by week.
* Some Docker images and Docker-compose files have been created, to help in the deployment process.

## Work to be done
This is a finished product, as it can be used for the purpose it was conceived from the beginning. Nevertheless, every work always can be improved.

In this case, the part that has more room to be improved is the web application. The time for the Google Summer of code is limited, so it is not possible to perform a broad study of user interfaces and possible alternatives.

In the same way, it could be a good idea to make an in-depth study of AI techniques and finding which would be a better alternative to Random Forest classifier, if any. The classification result might be improved by using a better classifier, maybe using a Neural Network classifier or similar.

## Delivered products
In this section, you can access the links to each product. Each product itself should contain a more detailed explanation of how it works.

* System to generate annotations from SPARQL

  * SPARQL & Bash scripts: resources available on [vfrico/dbpedia-gsoc-18](https://github.com/vfrico/dbpedia-gsoc-18/tree/master/scripts)

  * Java project that generates CSV files with annotations and classifier features, named *TB{1-11}*, *C{1-4}* and *M{1-5}*. Available also on: [vfrico/dbpedia-gsoc-18](https://github.com/vfrico/dbpedia-gsoc-18/tree/master/inconsistents-mappings). Note: this product is derived from [Nandana](https://github.com/nandana)'s previous work, as stated on the source code files. This work is licensed under Apache 2.0 License.

* Web application and API to manage annotations votes and classification:

  * Web App: Built with Node.js, and uses React.js as a DOM framework.

    * Source code on: [vfrico/mapping-predictor-webapp](https://github.com/vfrico/mapping-predictor-webapp)

    * Docker image on: [vfrico/dbpedia-mappings-webapp](https://hub.docker.com/r/vfrico/dbpedia-mappings-webapp/)

  * Backend API: Built with JavaEE (Glassfish) and Jersey REST Framework

    * Source code available on: [vfrico/mapping-predictor-backend](https://github.com/vfrico/mapping-predictor-backend)

    * Docker image available on: [vfrico/dbpedia-mappings-backend](https://hub.docker.com/r/vfrico/dbpedia-mappings-backend/)

    * API documentation available on: [Github pages](https://vfrico.github.io/mapping-predictor-backend/)

  * [Docker-compose](https://docs.docker.com/compose/) file, to deploy all the client application with one-click. It may be necessary to edit the two environment variables that refer to Backend API and SPARQL endpoint. Available on: [vfrico/mapping-predictor-backend](https://github.com/vfrico/mapping-predictor-backend/blob/master/docker-compose.yml)



# License
Unless other licenses are specified on each document, all the source code and derived work are released under [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).
This is a free software license approved by [Free Software Foundation](https://www.gnu.org/licenses/license-list.html).



