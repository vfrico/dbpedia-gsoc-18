Delivered products:

* System to generate annotations from SPARQL

  * SPARQL & Bash scripts: resources available on [vfrico/dbpedia-gsoc-18](https://github.com/vfrico/dbpedia-gsoc-18/tree/master/scripts)

  * Java project that generates CSV files with annotations and classifier features, named *TB{1-11}*, *C{1-4}* and *M{1-5}*. Available also on: [vfrico/dbpedia-gsoc-18](https://github.com/vfrico/dbpedia-gsoc-18/tree/master/inconsistents-mappings). Note: this product is derived from [Nandana](https://github.com/nandana)'s previous work, as stated on the source code files. This work is licensed under Apache 2.0 License.

* Web application and API to manage annotations votes and classification:

  * Web App: Built with Node.js, and uses React.js as visual framework.

    * Source code on: [vfrico/mapping-predictor-webapp](https://github.com/vfrico/mapping-predictor-webapp)

    * Docker image on: [vfrico/dbpedia-mappings-webapp](https://hub.docker.com/r/vfrico/dbpedia-mappings-webapp/)

  * Backend API: Built with JavaEE (Glassfish) and Jersey REST Framework

    * Source code available on: [vfrico/mapping-predictor-backend](https://github.com/vfrico/mapping-predictor-backend)

    * Docker image available on: [vfrico/dbpedia-mappings-backend](https://hub.docker.com/r/vfrico/dbpedia-mappings-backend/)

    * API documentation available on: [Github pages](https://vfrico.github.io/mapping-predictor-backend/)

  * [Docker-compose](https://docs.docker.com/compose/) file, to deploy all the client application with one-click. It may be neccesary to edit the two environment variables that refers to Backend API and SPARQL endpoint. Available on: []vfrico/mapping-predictor-backend](https://github.com/vfrico/mapping-predictor-backend/blob/master/docker-compose.yml)



License: Unless other license are specified on each document, all the source code and derived work is released under [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).




