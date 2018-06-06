#!/bin/bash
#
# Copyright 2018 Víctor Fernández <vfrico@gmail.com>
#
# This is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#
# This scripts automates the process of generating the needed sources
# and getting them ready to dump to a triple store like virtuoso
#
# Each line on this script generates a folder with a global.graph inside
# which means that every rdf file must be dumped on the same graph
#
# Note that this task can be very time consuming

# This scripts should have execution permissions (add with chmod +x) 
# and must be on the same folder
GRAPH_REIF="./graph_reif.sh"
TTL_DOWNLOAD="./ttl_download.sh"


# English dbpedia reification
$GRAPH_REIF "english_ttl" "http://dbpedia.org/" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_literals_en.ttl.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_objects_en.ttl.bz2" 
$GRAPH_REIF "english_tql" "http://dbpedia.org/r" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_literals_en.tql.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_objects_en.tql.bz2" 
# English R2RML mappings
$TTL_DOWNLOAD "english_rml" "http://dbpedia.org/rml" "r2rml_en.ttl" "http://mappings.dbpedia.org/server/mappings/en/pages/rdf/all"


# Spanish dbpedia reification
$GRAPH_REIF "spanish_ttl" "http://es.dbpedia.org/"  "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_literals_en_uris_es.ttl.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_objects_en_uris_es.ttl.bz2" 
$GRAPH_REIF "spanish_tql" "http://es.dbpedia.org/r" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_literals_en_uris_es.tql.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_objects_en_uris_es.tql.bz2" 
# Spanish R2RML mappings
$TTL_DOWNLOAD "spanish_rml" "http://es.dbpedia.org/rml" "r2rml_es.ttl" "http://mappings.dbpedia.org/server/mappings/es/pages/rdf/all"


# DBpedia ontology download
$TTL_DOWNLOAD "ontology" "http://dbpedia.org/ontology" "dbpedia-ontology.nt" "http://downloads.dbpedia.org/2016-10/dbpedia_2016-10.nt"