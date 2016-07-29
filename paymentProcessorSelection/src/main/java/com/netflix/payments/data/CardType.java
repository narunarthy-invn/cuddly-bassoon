package com.netflix.payments.data;

/**
 * @author sudutha.
 */
public enum CardType {
    VISA("VISA)"),
    MasterCard("Master Card"),
    AmericanExpress("American Express");

    String cardType;

    CardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }
}
