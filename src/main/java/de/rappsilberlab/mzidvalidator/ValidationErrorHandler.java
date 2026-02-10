package de.rappsilberlab.mzidvalidator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorHandler implements ErrorHandler {

    private final List<String> errors = new ArrayList<>();

    @Override
    public void warning(SAXParseException e) {
        errors.add(format("WARNING", e));
    }

    @Override
    public void error(SAXParseException e) {
        errors.add(format("ERROR", e));
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        errors.add(format("FATAL", e));
        throw e; // fail fast on fatal errors
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getFormattedErrors() {
        return String.join(System.lineSeparator(), errors);
    }

    private String format(String level, SAXParseException e) {
        return String.format(
                "%s at line %d, column %d: %s",
                level,
                e.getLineNumber(),
                e.getColumnNumber(),
                e.getMessage()
        );
    }
}