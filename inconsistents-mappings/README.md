# Inconsistent mappings

*This project is part of my work done during my Google 
Summer of Code 2018 in collaboration with DBpedia*

This repository contains the code needed to generate 
a CSV with the annotations on a language pair.
Before running any code here, please, read the 
instructions to build a virtuoso endpoint and load
the recommended triples and graphs.

## Development environment
The recommended IDE to run this project is Intellij IDEA.
This repository should include the `.idea` folder, to
ease the project importation to this IDE.

The recommended Java version to run it is Java 8.

## Virtuoso endpoint
This application will execute hundreds of queries against
the SPARQL endpoint selected. So it is recommended to
use a local endpoint to no overload original DBpedia
server, as well as we also will need some additional
triples that are not available by default on original
endpoints.

[This script](https://github.com/vfrico/dbpedia-gsoc-18/blob/master/scripts/launch_reif.sh) will download and reify triples for the 
english and spanish DBpedia.

As we can observe by reading it, we need the following graphs for each language
* http://dbpedia.org/: contains the entities URIs, the same as the default DBpedia endpoint
* http://dbpedia.org/r: reified triples from tql releases on dbpedia.
* http://dbpedia.org/rml: The DBpedia mappings but transformed to rml format

We will use a different URIs for different languages. In the
example, if we use the spanish language, we would load
`http://es.dbpedia.org/`, `http://es.dbpedia.org/r` 
and `http://es.dbpedia.org/rml` graphs 

Additonally, the DBpedia ontology is needed to infer
class hierarchy between properties and classes.

For the virtuoso configuration, you can use the virtuoso.ini
file that exists inside this folder. It should not been used
as a drop in replacement of installed .ini, but as a 
reference to fix problems, if any exist.


## Program execution
To execute the program and produce the needed annotations 
CSV file, you only have to run the `InconsistentMappings` 
class, starting on the main method.

The params to edit are the SPARQL endpoint and the 
RDF graphs to use with each language.

It may be needed to change the SPARQL endpoint constant
also in other classes, like DBO class.

## Work done under the GSoC 2018
The work done by Mihindukulasooriya et al. was almost ready for production, 
but it still worked weirdly on some executions and 
some attribute names were interchanged, so the CSV file
wasn't consistent with the published attributes.
