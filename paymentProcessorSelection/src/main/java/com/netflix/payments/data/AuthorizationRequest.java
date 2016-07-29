package com.netflix.payments.data;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder; /**
 * @author sudutha.
 */

/**
 * Object to track the authorization request to be sent with processor and amount details.
 */
public class AuthorizationRequest {

    private String paymentProcessor;
    private float authorizationAmount;
    private CurrencyType currencyType;

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public float getAuthorizationAmount() {
        return authorizationAmount;
    }

    public void setAuthorizationAmount(float authorizationAmount) {
        this.authorizationAmount = authorizationAmount;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authorizationAmount", authorizationAmount)
                .append("paymentProcessor", paymentProcessor)
                .append("currencyType", currencyType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorizationRequest)) {
            return false;
        }
        AuthorizationRequest that = (AuthorizationRequest) o;
        return Objects.equal(authorizationAmount, that.authorizationAmount) &&
                Objects.equal(paymentProcessor, that.paymentProcessor) &&
                Objects.equal(currencyType, that.currencyType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paymentProcessor, authorizationAmount, currencyType);
    }
}
