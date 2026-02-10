package de.rappsilberlab.mzidvalidator;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaxValidatorTest {

    private final SaxValidator validator = new SaxValidator();

    @Test
    void validXmlShouldPassValidation() {
        Path xml = resourcePath("xml/valid.xml");
        Path xsd = resourcePath("xsd/test-schema.xsd");

        assertDoesNotThrow(() -> validator.validate(xml, xsd));
    }

    @Test
    void invalidXmlShouldFailValidation() {
        Path xml = resourcePath("xml/invalid.xml");
        Path xsd = resourcePath("xsd/test-schema.xsd");

        assertThrows(IllegalStateException.class,
                () -> validator.validate(xml, xsd));
    }

    private Path resourcePath(String resource) {
        return Path.of(
                getClass()
                        .getClassLoader()
                        .getResource(resource)
                        .getPath()
        );
    }
}