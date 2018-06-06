# Scripts
This folder contains several scripts for generating datasets to train

* `dbpedia_download.sh`: Downloads all .bz2 files from dbpedia downloads and unzip them. 
* `tql_parser.sh`: Downloads and generates reified triples from tql files.
* `graph_reif.sh`: Generates a .graph file containing the Graph IRI and downloads ttl or reifies .tql files.
* `launch_reif.sh`: Example of usage of `graph_reif.sh`. *Can be seen* as the main script here.
* `tql2reif.sh`: External script to do reification process. Consumes a `.tql` file and outputs a `.ttl` file on stdout. Download from [gist.github.com]( https://gist.github.com/nandana/7cea0f4d990696465e9b4b21e5f1485e).
* `ttl_download.sh`: Downloads a single .ttl or .nt file and generates a folder and a graph (see doc inside)

