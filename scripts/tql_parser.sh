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
# tql2reif.sh script can be found on:
#    https://gist.github.com/nandana/7cea0f4d990696465e9b4b21e5f1485e
#
# Downloads the tql.bz2 files from dbpedia, unzip them and generates unified .ttl
# The input file should contain one URI on each line

if [ "$#" -ne 1 ]; then
    echo "tql_parser - Automates the download and tql reification process"
    echo "Usage: tql_parser.sh <text_file_with_.tql.bz2_uris>"
    exit 1;
fi

REIF_SCRIPT="./tql2reif.sh"
pids=""
while IFS='' read -r line || [[ -n "$line" ]]; do
	# Removes the path from the URI
	FILENAME="${line##*/}"
	wget "$line"
	
	echo "Unzipping TQL"
	bunzip2 -v "$FILENAME"
	
	UNZIPPED_TQL="$(basename $FILENAME .bz2)"
	TTL_FILE="$(basename $UNZIPPED_TQL .tql)".ttl
	
	echo "Refication of the TQL file"
	$REIF_SCRIPT $UNZIPPED_TQL > $TTL_FILE &
	pids="$pids $!"
done < "$1"

for pid in $pids; do
	echo "Waiting for $pid to end"
	wait $pid || echo "Error on $pid" 1>&2
done
