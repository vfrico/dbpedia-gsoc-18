#!/usr/bin/env python3
import urllib.parse
import shlex
import uuid
import re
import sys

regex1 = '.*<(.*)&template=(.*)&property=(.*)&split(.*)>'
r1 = re.compile(regex1)

regex2 = '.*<(.*)&template=(.*)&property=(.*)>'
r2 = re.compile(regex2)

regexQuads = '(<.*>) (<.*>) (.*) (<.*>) \.'
rquads = re.compile(regexQuads)

def re_split(s):
    def strip_quotes(s):
        if s and (s[0] == '"' or s[0] == "'") and s[0] == s[-1]:
            return s[1:-1]
        return s
    return [strip_quotes(p).replace('\\"', '"').replace("\\'", "'") for p in re.findall(r'"(?:\\.|[^"])*"|\'(?:\\.|[^\'])*\'|[^\s]+', s)]

def split_quads(s):
    try:
        return rquads.match(s).groups()
    except Exception as exc:
        print("No puedo parsear la línea \n{}\n porque no cumple con el formato de quad {}".format(s, regexQuads), file=sys.stderr)
        return []

def mapToTriples(quad):
    """
    try:
        parsed = shlex.split(quad)
    except Exception as exc:
        print("Error al parsear "+quad)
        print(exc)
        parsed = quad.split()
    """
    parsed = split_quads(quad)
    if len(parsed) <= 0:
        return [""]
    subject = parsed[0]
    predicate = parsed[1]
    obj = parsed[2]
    prov = parsed[3]

    parsed_uri = r1.match(prov)
    parsed_uri2 = r2.match(prov)
    if parsed_uri is not None:
        # print(prov, parsed_uri)
        uri = parsed_uri.groups()[0]
        template = parsed_uri.groups()[1]
        attribute = parsed_uri.groups()[2]
    elif parsed_uri2 is not None:
        parsed_uri = parsed_uri2
        uri = parsed_uri.groups()[0]
        template = parsed_uri.groups()[1]
        attribute = parsed_uri.groups()[2]
    else:
        print("Error reificando {}".format(quad), file=sys.stderr)
        return [""]
    """
        prov_parsed = urllib.parse.urlparse(prov[1:-1])
        fragment_dict = urllib.parse.parse_qs(prov_parsed.fragment)
        template = fragment_dict["template"][0]
        attribute = fragment_dict["property"][0]
    """


    uuid_t = uuid.uuid4()

    triples = ["_:{} <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement> .".format(uuid_t)]
    triples.append("_:{} <http://www.w3.org/1999/02/22-rdf-syntax-ns#subject> {} . ".format(uuid_t, subject))
    triples.append("_:{} <http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate> {}".format(uuid_t, predicate))
    triples.append("_:{} <http://www.w3.org/ns/prov#wasDerivedFrom> {} . ".format(uuid_t, uri))
    triples.append("_:{} <http://dbpedia.org/x-template> \"{}\" . ".format(uuid_t, template))
    triples.append("_:{} <http://dbpedia.org/x-attribute> \"{}\" . ".format(uuid_t, attribute))

    return triples



def main():
    f = open(sys.argv[1])
    lineN = 0
    for l in f:
        lineN += 1
        # print("Linea: "+l)
        if l[0] != "#":  # Ignoramos comentarios en el fichero
            triples = mapToTriples(l)
            [print(j) for j in triples]
        if lineN % 10000 == 0:
            print("Línea {}".format(lineN), file=sys.stderr)

if __name__ == '__main__':
    main()
