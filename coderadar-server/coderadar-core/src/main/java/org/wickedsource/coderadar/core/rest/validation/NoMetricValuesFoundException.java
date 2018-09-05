package org.wickedsource.coderadar.core.rest.validation;

public class NoMetricValuesFoundException extends UserException {

    public NoMetricValuesFoundException() {
        super("No metric values found. Try to restart your analyzing job or change parameters for your query.");
    }
}
