package com.netflix.payments.data;

/**
 * @author sudutha.
 */
/**
 * Stores details about each paymentProcessor. Currently only value.
 * This could be maintained by registry but for time sake used enums.
 */
public enum PaymentProcessor {

    PROCESSOR1("PROCESSOR#1"),
    PROCESSOR2("PROCESSOR#2"),
    PROCESSOR3("PROCESSOR3");

    String value;

    PaymentProcessor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
