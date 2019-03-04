#!/bin/bash

# turn on bash's job control
set -m

ls -l /

# Start the primary process and put it in the background
exec /virtuoso.sh &

sleep 20 

echo "Iniciamos la carga de tripletas"

# Le decimos a virtuoso que cargue la carpeta data/
isql-v -U dba -P $DBA_PASSWORD exec="ld_dir_all ('data/', '*.ttl', NULL);select * from DB.DBA.load_list;"

echo "Lanzamos dos loaders en paralelo"
isql-v -U dba -P $DBA_PASSWORD exec="rdf_loader_run();" &
isql-v -U dba -P $DBA_PASSWORD exec="rdf_loader_run();" &

echo "Esperamos..."

#wait

echo "Hemos acabado de esperar"

isql-v -U dba -P $DBA_PASSWORD exec="checkpoint;"
echo "Recomendado por la documentacion de isql de virtuoso..."

# Comprobamos que se ha cargado todo
echo "Comrpobamos que se ha cargado todo"
isql-v -U dba -P $DBA_PASSWORD exec="select * from DB.DBA.load_list;"

echo "Volvemos al proceso principal"

fg %1

