FROM tenforce/virtuoso

RUN apt-get update \
    && apt-get install -y python3 wget \
    && apt autoremove -y \
    && apt autoclean \
    && chmod +x /virtuoso.sh 

ENV DBA_PASSWORD=myDbaPassword \
    SPARQL_UPDATE=true \
    VIRT_Parameters_NumberOfBuffers=1360000 \
    VIRT_Parameters_NumberOfBuffers=1000000 \
    VIRT_SPARQL_ResultSetMaxRows=100000 \
    VIRT_SPARQL_MaxQuerqyCostEstimationTime=4000 \
    VIRT_SPARQL_MaxQueryExecutionTime=2000

ENV VIRTUOSO_ROOT_DIR /usr/local/virtuoso-opensource/var/lib/virtuoso/db

COPY reif.py dbpedia_download.sh graph_reif.sh launch_reif.sh tql_parser.sh ttl_download.sh $VIRTUOSO_ROOT_DIR/data/

COPY loader.isql docker-entrypoint.sh $VIRTUOSO_ROOT_DIR/

EXPOSE 8890 1111

CMD ["/bin/bash", "docker-entrypoint.sh"]

