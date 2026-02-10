package de.rappsilberlab.mzidvalidator;

import java.nio.file.Path;

public class SaxValidationApp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar xml-sax-validator.jar <xml-file> <xsd-file>");
            System.exit(1);
        }

        Path xmlPath = Path.of(args[0]);
        Path xsdPath = Path.of(args[1]);

        SaxValidator validator = new SaxValidator();

        try {
            validator.validate(xmlPath, xsdPath);
            System.out.println("XML is valid.");
        } catch (Exception e) {
            System.err.println("XML validation failed:");
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }
}