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
# The input file should contain one URI on each line

if [ "$#" -ne 1 ]; then
    echo "dbpedia_download - Automates the download of dbpedia dumps"
    echo "Usage: dbpedia_download <text_file_with_.bz2_uris>"
    exit 1;
fi

while IFS='' read -r line || [[ -n "$line" ]]; do
	FILENAME="${line##*/}"
	wget "$line" && bunzip2 -v "$FILENAME"
done < "$1"

