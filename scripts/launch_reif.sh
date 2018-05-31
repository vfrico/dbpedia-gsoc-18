GRAPH_REIF="./graph_reif.sh"
TTL_DOWNLOAD="./ttl_download.sh"

# English dbpedia
$GRAPH_REIF "english_ttl" "http://dbpedia.org/" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_literals_en.ttl.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_objects_en.ttl.bz2" 
$GRAPH_REIF "english_tql" "http://dbpedia.org/r" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_literals_en.tql.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/en/mappingbased_objects_en.tql.bz2" 

$GRAPH_REIF "spanish_ttl" "http://es.dbpedia.org/"  "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_literals_en_uris_es.ttl.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_objects_en_uris_es.ttl.bz2" 
$GRAPH_REIF "spanish_tql" "http://es.dbpedia.org/r" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_literals_en_uris_es.tql.bz2" "http://downloads.dbpedia.org/2016-04/core-i18n/es/mappingbased_objects_en_uris_es.tql.bz2" 

$TTL_DOWNLOAD "english_rml" "http://dbpedia.org/rml" "r2rml_en.ttl" "http://mappings.dbpedia.org/server/mappings/en/pages/rdf/all"
$TTL_DOWNLOAD "spanish_rml" "http://es.dbpedia.org/rml" "r2rml_es.ttl" "http://mappings.dbpedia.org/server/mappings/es/pages/rdf/all"
