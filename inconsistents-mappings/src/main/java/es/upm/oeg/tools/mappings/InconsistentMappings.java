package es.upm.oeg.tools.mappings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.RDFNode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

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
 *
 */
public class InconsistentMappings {

//    public static final String SPARQL_ENDPOINT = "http://4v.dia.fi.upm.es:8890/sparql";
    public static final String SPARQL_ENDPOINT = "http://35.195.180.82:8890/sparql";
    //public static final String SPARQL_ENDPOINT = "http://172.17.0.1:8890/sparql";

    private static final String Q1_PATH = "src/main/resources/mappings/q1.rq";
    private static final String Q2_PATH = "src/main/resources/mappings/q2.rq";
    private static final String Q3_PATH = "src/main/resources/mappings/q3.rq";
    private static final String Q4_PATH = "src/main/resources/mappings/q4.rq";
    private static final String Q5_PATH = "src/main/resources/mappings/q5.rq";
    private static final String Q1_String;
    private static final String Q2_String;
    private static final String Q3_String;
    private static final String Q4_String;
    private static final String Q5_String;


    private static final DecimalFormatSymbols symbolsDE_DE = DecimalFormatSymbols.getInstance(Locale.US);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###,###,##0.0000", symbolsDE_DE);

    private static final Logger logger = LoggerFactory.getLogger(InconsistentMappings.class);

    static {
        try {
            Q1_String = readFile(Q1_PATH, Charset.defaultCharset());
            Q2_String = readFile(Q2_PATH, Charset.defaultCharset());
            Q3_String = readFile(Q3_PATH, Charset.defaultCharset());
            Q4_String = readFile(Q4_PATH, Charset.defaultCharset());
            Q5_String = readFile(Q5_PATH, Charset.defaultCharset());
        } catch (IOException ioe) {
            logger.error("Error loading the queries", ioe);
            throw new IllegalStateException("Error loading the query: " + ioe.getMessage(), ioe);
        }
        graph1 =  "http://dbpedia.org/";
        graph2 =  "http://es.dbpedia.org/";
        rGraph1 = "http://dbpedia.org/r";
        rGraph2 = "http://es.dbpedia.org/r";
        classGraph1 = "http://dbpedia.org/rml";
        classGraph2 = "http://es.dbpedia.org/rml";

        infoboxPrefix1 = "http://mappings.dbpedia.org/server/mappings/en/";
        infoboxPrefix2 = "http://mappings.dbpedia.org/server/mappings/es/";
    }

    public static String graph1;
    public static String graph2;
    public static String rGraph1;
    public static String rGraph2;
    public static String infoboxPrefix1;
    public static String infoboxPrefix2;
    public static String classGraph1;
    public static String classGraph2;

    BufferedWriter writer;

    final Object lock = new Object();

    public static void main(String[] args) throws Exception {

        InconsistentMappings props = new InconsistentMappings();
        props.process();

    }

    //Initialize parameters
    private void init() throws IOException {



        Path path = FileSystems.getDefault().getPath("/home/vfrico/en-es-lit.csv");

        logger.trace("Escribiendo en: "+path);

        writer = Files.newBufferedWriter(path, Charset.defaultCharset(),
                StandardOpenOption.CREATE);
        printCSVTitles();
    }

    public void printCSVTitles() {
        try {
            writer.write("Template A"
                    + ", " + "Attribute A"
                    + ", " + "Template B"
                    + ", " + "Attribute B"
                    + ", " + "Property A"
                    + ", " + "Property B"
                    + ", " + "Class A"
                    + ", " + "Class B"
                    + ", " + "Domain Property A"
                    + ", " + "Domain Property B"
                    + ", " + "Range Property A"
                    + ", " + "Range Property B"
                    + ", " + "C1" // M1/M4
                    + ", " + "C2" // M2/M4
                    + ", " + "C3a" // M3a/M5a
                    + ", " + "C3b" // M3b/M5b
                    + ", " + "M3" // M4
                    + ", " + "M1"
                    + ", " + "M2"
                    + ", " + "M4a" //M4a
                    + ", " + "M4b" //M4b
                    + ", " + "M5a"
                    + ", " + "M5b"
                    + ", " + "TB1"
                    + ", " + "TB2"
                    + ", " + "TB3"
                    + ", " + "TB4"
                    + ", " + "TB5"
                    + ", " + "TB6"
                    + ", " + "TB7"
                    + ", " + "TB8"
                    + ", " + "TB9"
                    + ", " + "TB10"
                    + ", " + "TB11"
            );
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Extract the data
    public void process() throws Exception {

        //Initialize the parameters
        //TODO config as command line parameters
        init();

        //Extract the property pairs
        List<PropPair> propPairList = extractPropPairs();

        //Extract the metrics
        new ForkJoinPool(20).submit(() -> {
            propPairList.parallelStream()
                    .forEach(propPair -> {
                        collectMetrics(propPair);
                    });
        }).get();

        writer.close();


    }

    private List<PropPair> extractPropPairs() {

        List<PropPair> propPairList = new ArrayList<>();

        ParameterizedSparqlString pss = new ParameterizedSparqlString();
        pss.setCommandText(Q1_String);
        pss.setIri("graph1", graph1);
        pss.setIri("graph2", graph2);
        pss.setIri("rGraph1", rGraph1);
        pss.setIri("rGraph2", rGraph2);
        String q1 = pss.toString();

        logger.debug("Query 1:\n{}", q1);

        List<Map<String, RDFNode>> resultsMap = SparqlUtils.executeQueryForList(q1, SPARQL_ENDPOINT,
                Sets.newHashSet("p1", "p2", "t1", "t2", "a1", "a2", "count"));

        for (Map<String, RDFNode> map : resultsMap) {
            String t1 = map.get("t1").asLiteral().getString();
            String t2 = map.get("t2").asLiteral().getString();
            String p1 = map.get("p1").asResource().getURI();
            String p2 = map.get("p2").asResource().getURI();
            String a1 = map.get("a1").asLiteral().getString();
            String a2 = map.get("a2").asLiteral().getString();
            long count = map.get("count").asLiteral().getLong();

            PropPair pair = new PropPair(t1, t2, a1, a2, p1, p2, count);
            propPairList.add(pair);
        }

        logger.info("{} properties found", propPairList.size());

        return propPairList;
    }


    public static PropPair metricas(PropPair propPair) {
        propPair.setPropA(getFullProperty(propPair.getPropA()));
        propPair.setPropB(getFullProperty(propPair.getPropB()));

        logger.info("Collecting metrics for {}, {}, {}, {}",
                getPrefixedProperty(propPair.getPropA()),
                getPrefixedProperty(propPair.getPropB()),
                propPair.getTemplateA(),
                propPair.getTemplateB()
        );

        ParameterizedSparqlString q2pss = new ParameterizedSparqlString();
        q2pss.setCommandText(Q2_String);
        q2pss.setIri("graph1", graph1);
        q2pss.setIri("graph2", graph2);
        q2pss.setIri("rGraph1", rGraph1);
        q2pss.setIri("rGraph2", rGraph2);
        q2pss.setIri("p1", propPair.getPropA());
        q2pss.setIri("p2", propPair.getPropB());
        q2pss.setLiteral("t1", propPair.getTemplateA());
        q2pss.setLiteral("t2", propPair.getTemplateB());
        q2pss.setLiteral("a1", propPair.getAttributeA());
        q2pss.setLiteral("a2", propPair.getAttributeB());
        String q2 = q2pss.toString();

        Map<String, RDFNode> resultsList;
        try {
            resultsList = SparqlUtils.executeQueryForMap(q2, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}\n{}", e.getMessage(), q2, e);
            return null;
        }

        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            logger.info("Query2 : "+q2);
            logger.info("M2 es: "+count);
            propPair.setM2(count);
        }

        ParameterizedSparqlString q3pss = new ParameterizedSparqlString();
        q3pss.setCommandText(Q3_String);
        q3pss.setIri("graph", graph1);
        q3pss.setIri("rGraph1", rGraph1);
        q3pss.setIri("rGraph2", rGraph2);
        q3pss.setLiteral("t1", propPair.getTemplateA());
        q3pss.setLiteral("t2", propPair.getTemplateB());
        q3pss.setIri("p1", propPair.getPropA());
        q3pss.setIri("p2", propPair.getPropB());
        q2pss.setLiteral("a1", propPair.getAttributeA());
        q2pss.setLiteral("a2", propPair.getAttributeB());
        String q3a = q3pss.toString();

        try {
            resultsList = SparqlUtils.executeQueryForMap(q3a, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}\n{}", e.getMessage(), q3a, e);
            return null;
        }


        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            logger.info("M4a es:"+count);
            propPair.setM4a(count);
        }

        q3pss.setIri("graph", graph2);
        String q3b = q3pss.toString();


        try {
            resultsList = SparqlUtils.executeQueryForMap(q3b, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}, \n{}", e.getMessage(), q3b, e);
            return null;
        }


        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            propPair.setM4b(count);
        }

        ParameterizedSparqlString q4pss = new ParameterizedSparqlString();
        q4pss.setCommandText(Q4_String);
        q4pss.setIri("graph1", graph1);
        q4pss.setIri("graph2", graph2);
        q4pss.setIri("rGraph1", rGraph1);
        q4pss.setIri("rGraph2", rGraph2);
        q4pss.setLiteral("t1", propPair.getTemplateA());
        q4pss.setLiteral("t2", propPair.getTemplateB());
        q4pss.setIri("p1", propPair.getPropA());
        q4pss.setIri("p2", propPair.getPropB());
        q2pss.setLiteral("a1", propPair.getAttributeA());
        q2pss.setLiteral("a2", propPair.getAttributeB());
        String q4 = q4pss.toString();

        try {
            resultsList = SparqlUtils.executeQueryForMap(q4, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}\n{}", e.getMessage(), q4, e);
            return null;
        }


        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            propPair.setM3(count);
        }

        ParameterizedSparqlString q5pss = new ParameterizedSparqlString();
        q5pss.setCommandText(Q5_String);
        q5pss.setIri("graph", graph1);
        q5pss.setIri("rGraph1", rGraph1);
        q5pss.setIri("rGraph2", rGraph2);
        q5pss.setLiteral("t1", propPair.getTemplateA());
        q5pss.setLiteral("t2", propPair.getTemplateB());
        q5pss.setIri("p1", propPair.getPropA());
        q5pss.setIri("p2", propPair.getPropB());
        q2pss.setLiteral("a1", propPair.getAttributeA());
        q2pss.setLiteral("a2", propPair.getAttributeB());
        String q5a = q5pss.toString();

        try {
            resultsList = SparqlUtils.executeQueryForMap(q5a, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}\n{}", e.getMessage(), q5a, e);
            return null;
        }


        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            propPair.setM5a(count);
        }

        q5pss.setIri("graph", graph2);
        String q5b = q5pss.toString();

        try {
            resultsList = SparqlUtils.executeQueryForMap(q5b, SPARQL_ENDPOINT,
                    Sets.newHashSet("count"));
        } catch (Exception e) {
            logger.error("Error executing query: {}\n{}", e.getMessage(), q5a, e);
            return null;
        }


        if (resultsList.size() > 0) {
            long count = resultsList.get("count").asLiteral().getLong();
            propPair.setM5b(count);
        }
        return propPair;
    }

    private void collectMetrics(PropPair propPair) {
        DBO dbo = new DBO();

        propPair = metricas(propPair);

        Thread.currentThread().setName("collect-metrics-" + getPrefixedProperty(propPair.getPropA())
                + "-"+ getPrefixedProperty(propPair.getPropB()));


        // Configure TB properties
        fillTBProperties(propPair);

        synchronized (lock) {
            try {
                writer.write(propPair.getTemplateA()
                        + ", " + propPair.getAttributeA()
                        + ", " + propPair.getTemplateB()
                        + ", " + propPair.getAttributeB()
                        + ", " + getPrefixedProperty(propPair.getPropA())
                        + ", " + getPrefixedProperty(propPair.getPropB())
                        + ", " + getClass(classGraph1, infoboxPrefix1 + propPair.getTemplateA())
                        + ", " + getClass(classGraph2, infoboxPrefix2 + propPair.getTemplateB())
                        + ", " + getPrefixedProperty(dbo.getDomain(propPair.getPropA()))
                        + ", " + getPrefixedProperty(dbo.getDomain(propPair.getPropB()))
                        + ", " + getPrefixedProperty(dbo.getRange(propPair.getPropA()))
                        + ", " + getPrefixedProperty(dbo.getRange(propPair.getPropB()))
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM2()) / propPair.getM1())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM3()) / propPair.getM1())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM4a()) / propPair.getM5a())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM4b()) / propPair.getM5b())
                        + ", " + propPair.getM3()
                        + ", " + propPair.getM1()
                        + ", " + propPair.getM2()
                        + ", " + propPair.getM4a()
                        + ", " + propPair.getM4b()
                        + ", " + propPair.getM5a()
                        + ", " + propPair.getM5b()
                        + ", " + propPair.getTb1()
                        + ", " + propPair.getTb2()
                        + ", " + propPair.getTb3()
                        + ", " + propPair.getTb4()
                        + ", " + propPair.getTb5()
                        + ", " + propPair.getTb6()
                        + ", " + propPair.getTb7()
                        + ", " + propPair.getTb8()
                        + ", " + propPair.getTb9()
                        + ", " + propPair.getTb10()
                        + ", " + propPair.getTb11()
                );
                writer.newLine();
                writer.flush();
            } catch (Exception e) {
                logger.error("Error serializing the results! {}", e.getMessage(), e);
            }
        }
    }

    public static void fillTBProperties(PropPair propPair) {
        String pi = getPrefixedProperty(propPair.getPropA());
        String pj = getPrefixedProperty(propPair.getPropB());
        // TB1: 1 if Pi is subprop of Pj
        propPair.setTb1(DBO.isSubProperty(pi, pj));

        // TB2: 1 if Pj is subprop of pi
        propPair.setTb2(DBO.isSubProperty(pj, pi));

        String classi = getClass(classGraph1, infoboxPrefix1 + propPair.getTemplateA());
        String classj = getClass(classGraph2, infoboxPrefix2 + propPair.getTemplateB());

        // TB3: 1 if class corresponding are in the same graphs
        propPair.setTb3(DBO.areEquivalentClass(classi, classj));

        // TB4: 1 if the class in Gi is a subclass of the class in Gj
        propPair.setTb4(DBO.isSubClass(classi, classj));
        // TB5: vice versa
        propPair.setTb5(DBO.isSubClass(classj, classi));


        String domainPi = getPrefixedProperty(DBO.getDomain(pi));
        String domainPj = getPrefixedProperty(DBO.getDomain(pj));
        String rangePi = getPrefixedProperty(DBO.getRange(pi));
        String rangePj = getPrefixedProperty(DBO.getRange(pj));

        // TB6: Domain of Pi and Pj are the same => 1
        propPair.setTb6(DBO.areEquivalentProperties(domainPi, domainPj));

        // TB7: 1 if domain(Pi) subclass of domain(Pj)
        propPair.setTb7(DBO.isSubClass(domainPi, domainPj));

        // TB8: Vice versa
        propPair.setTb8(DBO.isSubClass(domainPj, domainPi));



        // TB9: Range of Pi and Pj are the same => 1
        propPair.setTb9(DBO.areEquivalentProperties(rangePi, rangePj));

        // TB10: 1 if range(Pi) subclass of range(Pj)
        propPair.setTb10(DBO.isSubClass(rangePi, rangePj));

        // TB11: vice versa
        propPair.setTb11(DBO.isSubClass(rangePj, rangePi));


    }

    public static String getClass(String graph, String infobox) {

        StringBuffer sb = new StringBuffer();
        sb.append("PREFIX rr: <http://www.w3.org/ns/r2rml#> ");
        sb.append("select ?class where { ");
        sb.append("  graph <" + graph +"> { ");
        sb.append("    <" + infobox + "> a rr:TriplesMap ; ");
        sb.append("    rr:subjectMap ?subjectMap . ");
        sb.append("    ?subjectMap rr:class ?class . } } ");

        String query = sb.toString();
        logger.warn("Query getClass: "+query);
        final List<RDFNode> classList = SparqlUtils.executeQueryForList(query, SPARQL_ENDPOINT, "class");

        Set<String> classes = new HashSet<>();
        for (RDFNode clazz : classList) {
            if(clazz.isURIResource()) {
                classes.add(clazz.asResource().getURI());
            }
        }

        if (classes.size() > 0) {
            return Joiner.on(",").join(classes);
        } else {
            return "";
        }
    }

    public static String getPrefixedProperty(String property) {
        property = property.replace("http://dbpedia.org/ontology/", "dbo:");
        property = property.replace("http://www.w3.org/2001/XMLSchema#", "xsd:");
        property = property.replace("http://www.w3.org/2002/07/owl#", "owl:");
        property = property.replace("http://www.w3.org/2003/01/geo/wgs84_pos#", "geo:");
        property = property.replace("http://www.w3.org/2000/01/rdf-schema#", "rdfs:");
        return property.replace("http://xmlns.com/foaf/0.1/", "foaf:");
    }

    public static String getFullProperty(String property) {
        property = property.replace( "dbo:", "http://dbpedia.org/ontology/");
        property = property.replace("xsd:","http://www.w3.org/2001/XMLSchema#");
        property = property.replace("owl:", "http://www.w3.org/2002/07/owl#");
        property = property.replace("rdfs:", "http://www.w3.org/2000/01/rdf-schema#");
        property = property.replace("geo:", "http://www.w3.org/2003/01/geo/wgs84_pos#");
        property = property.replace("rdfs:", "http://www.w3.org/2000/01/rdf-schema#");
        return property.replace( "foaf:", "http://xmlns.com/foaf/0.1/");
    }

    public static String readFile(String path, Charset encoding)
            throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        //byte[] encoded = ByteStreams.toByteArray(IOUtils.class.getClassLoader().getResourceAsStream(path));
        return new String(encoded, encoding);

    }




}
