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
# Automates the download of .ttl.bz2 or .tql.bz2 files and reification
# process.

if [ "$#" -lt 3 ]; then
    echo "graph_reif - Automates the download and tql reification process"
    echo "Usage: graph_reif.sh <graph_name> <graph_uri> <list of bz2 uris from graph>"
    echo ""
    echo "The list of uris can contain .ttl or .tql. On the latter case, a reification"
    echo "process will start"
    echo ""
    exit 1;
fi

# Refication script:
# tql2reif.sh script can be found on:
#    https://gist.github.com/nandana/7cea0f4d990696465e9b4b21e5f1485e
REIF_SCRIPT="tql2reif.sh"


GRAPH_NAME="$1"
GRAPH_URI="$2"
URIS="${@:3}"

mkdir -p $GRAPH_NAME
if [ ! -d "$GRAPH_NAME" ]; then
	echo "Unable to create folder. Exit..."
	exit 1;
fi

cd $GRAPH_NAME

# Write the graph name
echo $GRAPH_URI > global.graph

# Download and unzip files
for line in ${URIS[@]}; do
	echo -e "\n---\nProcessing file $line\n---\n"

	# Removes the path from the URI
	FILENAME="${line##*/}"
	wget "$line"
	
	echo "Unzipping TQL"
	bunzip2 -v "$FILENAME"
	
	UNZIPPED_NAME="$(basename $FILENAME .bz2)"
	
	FILE_EXTENSION="${UNZIPPED_NAME##*.}"

	# If file is .tql, it must be reified
	if [ $FILE_EXTENSION == "tql" ]; then
		TTL_FILE="$(basename $FILE_EXTENSION .tql)"_r.ttl
		echo "Refication of the TQL file"
		$REIF_SCRIPT $UNZIPPED_NAME > $TTL_FILE
		# Delete .tql file
		rm $UNZIPPED_NAME
	fi
	# If the file is .ttl, nothing is required
done