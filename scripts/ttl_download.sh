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
    echo "ttl_download - Automates the download to a single graph"
    echo "Usage: ttl_download.sh <graph_name> <graph_uri> <destination_name> <http origin>"
    echo ""
    echo "The list of uris can contain .ttl or .tql. On the latter case, a reification"
    echo "process will start"
    echo ""
    exit 1;
fi


GRAPH_NAME="$1"
GRAPH_URI="$2"
URIS="$4"
NAME="$3"

mkdir -p $GRAPH_NAME
if [ ! -d "$GRAPH_NAME" ]; then
	echo "Unable to create folder. Exit..."
	exit 1;
fi

cd $GRAPH_NAME

# Write the graph name
echo $GRAPH_URI > global.graph

wget -O $NAME $URI

