package com.netflix.payments.data;

/**
 * @author sudutha.
 */

/**
 * Enums to represent different countries. Ideally this could be maintained by a registry which can dynamically
 * add all the supported countries.
 */
public enum Country {
    US("United States"),
    CANADA("Canada"),
    MEXICO("Mexico"),
    UK("United Kingdom"),
    IRELAND("Ireland");

    String value;

    Country(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
