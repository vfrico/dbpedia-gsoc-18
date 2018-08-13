package es.upm.oeg.tools.mappings;

import com.google.common.base.Joiner;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hp.hpl.jena.ontology.OntModelSpec.OWL_MEM;
import static com.hp.hpl.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;

/**
 * Copyright 2014-2018 Ontology Engineering Group, Universidad Politécnica de Madrid, Spain
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Nandana Mihindukulasooriya
 * @since 1.0.0
 */
public class DBO {
    static String graph = "http://dbpedia.org/ontology";

//    private static final String SPARQL_Endpoint = "http://4v.dia.fi.upm.es:8890/sparql";
    private static final String SPARQL_Endpoint = "http://35.195.180.82:8890/sparql";
//    private static final String DBO_GRAGH = "http://dbpedia.org/o/201604";

//    OntModel inf;
    private static String PREFIXES = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
        "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " ;
    public DBO() {
//        OntModel base = ModelFactory.createOntologyModel( OWL_MEM );
//        base.read(DBO.class.getClassLoader().getResourceAsStream("mappings/dbpedia.owl"), "RDF/XML" );
//        inf = ModelFactory.createOntologyModel(OWL_MEM_MICRO_RULE_INF , base );
    }

    public static boolean isSubClass(String classA, String classB) {
        if (classA.trim().equals("") || classB.trim().equals("")) {
            System.out.println("Alguna de las clases está vacía: classA="+classA+" y classB="+classB);
            return false;
        }
        String query = PREFIXES + " " +
                buildAskQuery(graph, classA, "rdfs:subClassOf*", classB);

        System.out.println(query);

        return execAskSparql(query);
    }

    public static boolean isSubProperty(String propA, String propB) {
        if (propA.trim().equals("") || propB.trim().equals("")) {
            System.out.println("Alguna de las propiedades está vacía: PropA="+propA+" y propB="+propB);
            return false;
        }
        String query = PREFIXES + " " +
                buildAskQuery(graph, propA, "rdfs:subPropertyOf*", propB);

        System.out.println(query);

        return execAskSparql(query);

    }

    public static String buildAskQuery(String graph, String subject, String predicate, String object) {
        subject = InconsistentMappings.getPrefixedProperty(subject);
        object = InconsistentMappings.getPrefixedProperty(object);
        return "ask { graph <"+graph+"> " +
                "{ " + subject + " "+predicate+" " + object + " } }";
//        return  " ASK FROM <"+graph+"> WHERE { { " +
//                "SELECT  ?tt1  ?tt2 " +
//                "WHERE {" +
//                "  ?tt1 "+predicate+" ?tt2 .  } OFFSET  0 }  " +
//                "OPTION ( TRANSITIVE, T_NO_CYCLES, T_MIN  0, T_IN ( ?tt1 ), T_OUT ( ?tt2 )) " +
//                "FILTER (?tt1 =  <"+subject+">) FILTER (?tt2 =  <"+object+">) }";
    }

    public static boolean areEquivalentClass(String classA, String classB) {
        if (classA.trim().equals("") || classB.trim().equals("")) {
            System.out.println("Alguna de las clases está vacía: classA="+classA+" y classB="+classB);
            return false;
        }
        String graph = "http://dbpedia.org/ontology";
        String query = PREFIXES + " " +
                buildAskQuery(graph, classA, " owl:equivalentClass* ", classB);

        System.out.println(query);

        return execAskSparql(query);
    }
    public static boolean areEquivalentProperties(String propA, String propB) {
        if (propA.trim().equals("") || propB.trim().equals("")) {
            System.out.println("Alguna de las propiedades está vacía: PropA="+propA+" y propB="+propB);
            return false;
        }

        String query = PREFIXES + " " +
                buildAskQuery(graph, propA, "owl:equivalentProperty *", propB);

        System.out.println(query);

        return execAskSparql(query);
    }

    private static boolean execAskSparql(String query) {
        try {
            return SparqlUtils.ask(query, SPARQL_Endpoint);
        } catch (QueryParseException qpe) {
            System.out.println("Query "+query+" error:"+qpe+" \nReturn false");
            qpe.printStackTrace();
            return false;
        }
    }


    public static String getDomain(String property) {
        property = InconsistentMappings.getPrefixedProperty(property);
        String query = PREFIXES + " " +
                "select ?d { graph <http://dbpedia.org/ontology> { " + property + " <http://www.w3.org/2000/01/rdf-schema#domain> ?d } }";
        System.out.println("Query GetDomain: "+query);
        List<RDFNode> domains = SparqlUtils.executeQueryForList(query, SPARQL_Endpoint, "d");

        Set<String> domainList = domains.stream()
                .filter(d -> d.isURIResource())
                .map(s -> s.asResource().getURI())
                .collect(Collectors.toSet());
        System.out.println("Dominio: "+domainList);
        return Joiner.on(",").join(domainList);

//        Property p = inf.getProperty(property);
//        OntProperty prop = null;
//        try {
//            prop = p.as(OntProperty.class);
//        } catch (Exception e) {
//            return "";
//        }
//
//        OntResource domain = prop.getDomain();
//        if (domain != null) {
//            return domain.getURI();
//        } else {
//            return "";
//        }
    }

    public static String getRange(String property) {
        property = InconsistentMappings.getPrefixedProperty(property);
        String query = PREFIXES + " " +
                "select ?r { graph <http://dbpedia.org/ontology> { " + property + " <http://www.w3.org/2000/01/rdf-schema#range> ?r } }";

        List<RDFNode> ranges = SparqlUtils.executeQueryForList(query, SPARQL_Endpoint, "r");

        Set<String> rangeList = ranges.stream()
                .filter(d -> d.isURIResource())
                .map(s -> s.asResource().getURI())
                .collect(Collectors.toSet());

        return Joiner.on(",").join(rangeList);



//        Property p = inf.getProperty(property);
//        OntProperty prop = null;
//        try {
//            prop = p.as(OntProperty.class);
//        } catch (Exception e) {
//            return "";
//        }
//        OntResource range = prop.getRange();
//        if (range != null) {
//            return range.getURI();
//        } else {
//            return "";
//        }
    }

    public static void main(String[] args) {

        String property = "http://dbpedia.org/ontology/birthPlace";

        String classA = "dbo:Person";
        String classB = "dbo:AdultActor";

        DBO dbo = new DBO();
        System.out.println("Range:" + dbo.getRange(property));
        System.out.println("Domain:" + dbo.getDomain(property));
        System.out.println(dbo.isSubClass(classB, classA));
        System.out.println(dbo.isSubProperty( "dbo:isPartOfMilitaryConflict", "dbo:isPartOf"));

    }

}

