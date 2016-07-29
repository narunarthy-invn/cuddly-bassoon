package com.netflix.payments.data;

/**
 * @author sudutha.
 */
/**
 * Enums to represent different currencies. Ideally this could be maintained by a registry which can dynamically
 * add all the supported currencies.
 */
public enum CurrencyType {

    US_DOLLAR("US Dollar"),
    CANADIAN_DOLLAR("Canadian Dollar"),
    EURO("Euro"),
    JAPANESE_YEN("Japanese Yen"),
    BRITISH_POUND("British Pound");

    String name;

    CurrencyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
