package de.rappsilberlab.mzidvalidator;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.ValidatorHandler;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaxValidator {

    public void validate(Path xmlFile, Path xsdFile) throws Exception {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema;
        try (InputStream xsdStream = Files.newInputStream(xsdFile)) {
            schema = schemaFactory.newSchema(new SAXSource(new InputSource(xsdStream)));
        }

        ValidatorHandler validator = schema.newValidatorHandler();
        ValidationErrorHandler errorHandler = new ValidationErrorHandler();
        validator.setErrorHandler(errorHandler);

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        saxFactory.setNamespaceAware(true);

        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();

        // Pipe SAX events directly into the validator
        xmlReader.setContentHandler(validator);
        xmlReader.setErrorHandler(errorHandler);

        try (InputStream xmlStream = Files.newInputStream(xmlFile)) {
            xmlReader.parse(new InputSource(xmlStream));
        }

        if (errorHandler.hasErrors()) {
            throw new IllegalStateException(errorHandler.getFormattedErrors());
        }
    }
}
