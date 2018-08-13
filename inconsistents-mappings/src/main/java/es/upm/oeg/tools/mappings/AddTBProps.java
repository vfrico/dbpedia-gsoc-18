package es.upm.oeg.tools.mappings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class AddTBProps {
    static BufferedWriter writer;

    private static final DecimalFormatSymbols symbolsDE_DE = DecimalFormatSymbols.getInstance(Locale.US);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###,###,##0.0000", symbolsDE_DE);

    public static void main(String[] args) {
        Path iPath = FileSystems.getDefault().getPath("/home/vfrico/anotaciones.csv");
        Path oPath = FileSystems.getDefault().getPath("/home/vfrico/anotados.csv");

        try {
            writer = Files.newBufferedWriter(oPath, Charset.defaultCharset(),
                    StandardOpenOption.CREATE);
            printCSVTitles();
            BufferedReader br = Files.newBufferedReader(iPath);

            br.lines().forEach((line) -> {

                String[] campos = line.split(",");
                String templateA = campos[0].trim();
                String templateB = campos[2].trim();

                String attributeA = campos[1].trim();
                String attributeB = campos[3].trim();

                String propA = campos[4].trim();
                String propB = campos[5].trim();

                String sM1 = campos[19].trim();
                String sM2 = campos[20].trim();
                String sM3 = campos[21].trim();
                String sM4a = campos[22].trim();
                String sM4b = campos[23].trim();
                String sM5a = campos[24].trim();
                String sM5b = campos[25].trim();

                String anotacion = campos[6].trim();
                long m1 = 0;
                long m2 = 0;
                long m3 = 0;
                long m4a = 0;
                long m5a = 0;
                long m4b = 0;
                long m5b = 0;
                try {
                     m1 = Long.parseLong(sM1);
                     m2 = Long.parseLong(sM2);
                     m3 = Long.parseLong(sM3);
                     m4a = Long.parseLong(sM4a);
                     m4b = Long.parseLong(sM4b);
                     m5a = Long.parseLong(sM5a);
                     m5b = Long.parseLong(sM5b);
                } catch (NumberFormatException nfe) {
                    System.out.println("Error parsing "+sM1);
                }
                PropPair entry = new PropPair(templateA, templateB, attributeA, attributeB,
                        propA, propB, m1);
                entry.setAnotacion(anotacion);
                entry.setM2(m2);
                entry.setM3(m3);
                entry.setM4a(m4a);
                entry.setM4b(m4b);
                entry.setM5a(m5a);
                entry.setM5b(m5b);
            //                System.out.println(Arrays.toString(campos));
                System.out.println(entry);
                escribeLinea(entry);
            });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printCSVTitles() {
        try {
            writer.write("Template A"
                    + ", " + "Attribute A"
                    + ", " + "Template B"
                    + ", " + "Attribute B"
                    + ", " + "Property A"
                    + ", " + "Property B"
                    + ", " + "Class A"
                    + ", " + "Class B"
                    + ", " + "Anotacion"
                    + ", " + "Domain Property A"
                    + ", " + "Domain Property B"
                    + ", " + "Range Property A"
                    + ", " + "Range Property B"
                    + ", " + "C1" // M1/M4
                    + ", " + "C2" // M2/M4
                    + ", " + "C3a" // M3a/M5a
                    + ", " + "C3b" // M3b/M5b
                    + ", " + "M1" // M4
                    + ", " + "M2"
                    + ", " + "M3"
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

    public static void escribeLinea(PropPair propPair) {
        // Configure TB properties
        try {
            propPair = InconsistentMappings.metricas(propPair);
            if (propPair == null) throw new Exception("empty propPair");
            InconsistentMappings.fillTBProperties(propPair);
        } catch (Exception exc) {
            System.out.println("Error on collecting metrics: "+exc);
            exc.printStackTrace();
            return;
        }


            try {
                writer.write(propPair.getTemplateA()
                        + ", " + propPair.getAttributeA()
                        + ", " + propPair.getTemplateB()
                        + ", " + propPair.getAttributeB()
                        + ", " + InconsistentMappings.getPrefixedProperty(propPair.getPropA())
                        + ", " + InconsistentMappings.getPrefixedProperty(propPair.getPropB())
                        + ", " + InconsistentMappings.getClass(InconsistentMappings.classGraph1, InconsistentMappings.infoboxPrefix1 + propPair.getTemplateA())
                        + ", " + InconsistentMappings.getClass(InconsistentMappings.classGraph2, InconsistentMappings.infoboxPrefix2 + propPair.getTemplateB())
                        + ", " + propPair.getAnotacion()
                        + ", " + InconsistentMappings.getPrefixedProperty(DBO.getDomain(propPair.getPropA()))
                        + ", " + InconsistentMappings.getPrefixedProperty(DBO.getDomain(propPair.getPropB()))
                        + ", " + InconsistentMappings.getPrefixedProperty(DBO.getRange(propPair.getPropA()))
                        + ", " + InconsistentMappings.getPrefixedProperty(DBO.getRange(propPair.getPropB()))
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM2()) / propPair.getM1())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM3()) / propPair.getM1())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM4a()) / propPair.getM5a())
                        + ", " + DECIMAL_FORMAT.format(((double) propPair.getM4b()) / propPair.getM5b())
                        + ", " + propPair.getM1()
                        + ", " + propPair.getM2()
                        + ", " + propPair.getM3()
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
                System.out.println("Error serializing the results! " + e.getMessage()+ e);
            }

    }
}
