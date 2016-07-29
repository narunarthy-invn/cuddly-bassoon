package com.netflix.payments.data;

/**
 * @author sudutha.
 */

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Object to represent all the parameters that can be passed to the payment processor.
 */
public class PaymentParameters {

    private Country country;
    private CurrencyType currencyType;
    private CardType cardType;
    private float authorizationAmount;

    public PaymentParameters(CardType cardType, Country country, CurrencyType currencyType) {
        this.cardType = cardType;
        this.country = country;
        this.currencyType = currencyType;
    }

    public PaymentParameters(float authorizationAmount, CardType cardType, Country country, CurrencyType currencyType) {
        this.authorizationAmount = authorizationAmount;
        this.cardType = cardType;
        this.country = country;
        this.currencyType = currencyType;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public float getAuthorizationAmount() {
        return authorizationAmount;
    }

    public void setAuthorizationAmount(float authorizationAmount) {
        this.authorizationAmount = authorizationAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authorizationAmount", authorizationAmount)
                .append("country", country)
                .append("currencyType", currencyType)
                .append("cardType", cardType)
                .toString();
    }
}
