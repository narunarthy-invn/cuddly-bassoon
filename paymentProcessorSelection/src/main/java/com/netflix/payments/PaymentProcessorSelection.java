package com.netflix.payments;

import com.netflix.payments.configuration.CountryBasedPaymentProcessorSelectionConfigurationImpl;
import com.netflix.payments.configuration.CurrencyBasedPaymentProcessorSelectionConfigurationImpl;
import com.netflix.payments.data.AuthorizationRequest;
import com.netflix.payments.data.CardType;
import com.netflix.payments.data.PaymentParameters;
import com.netflix.payments.data.PaymentProcessor;
import com.netflix.payments.exceptions.ProcessorSelectionFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author sudutha.
 */
@Component
public class PaymentProcessorSelection {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessorSelection.class);

    protected ApplicationContext applicationContext;

    @Autowired
    CountryBasedPaymentProcessorSelectionConfigurationImpl countryBasedProcessorConfiguration;

    @Autowired
    CurrencyBasedPaymentProcessorSelectionConfigurationImpl currencyBasedProcessorConfiguration;

    /**
     * Method takes in the paramters required for selection and returns the processor name, amount and other
     * information in AuthorizationRequest object.
     * be used given the parameters.
     *
     * @param paymentParameters
     * @return AuthorizationRequest object.
     *
     * @throws ProcessorSelectionFailureException if any error occurs in selection of processor.
     */
    AuthorizationRequest findPaymentProcessor(PaymentParameters paymentParameters) throws ProcessorSelectionFailureException {

        AuthorizationRequest authorizationRequest = new AuthorizationRequest();

        try {
            validatePaymentParameters(paymentParameters);

            LOGGER.debug("Recieved paymentParameters: {}", paymentParameters);

            authorizationRequest.setCurrencyType(paymentParameters.getCurrencyType());

            /*
               Special case where card type is AmericanExpress. Amex does not support 0 amount and hence we set to 1 in
               the given currency.
             */
            float authorizationAmount = paymentParameters.getAuthorizationAmount();
            if (CardType.AmericanExpress.equals(paymentParameters.getCardType()) && authorizationAmount == 0) {
                authorizationAmount = 1.0f;
            }

            //Set the authorization amount
            authorizationRequest.setAuthorizationAmount(authorizationAmount);

            PaymentProcessor processor = null;

            //Currency based configuration supersedes country based. Hence first.
            //TBD: We can optimize this by creating a order chain - cardType, country, currency and registering
            //appropriate configurationProcessor.
            processor = currencyBasedProcessorConfiguration.getProcessor(paymentParameters);

            if (processor == null) {
                processor = countryBasedProcessorConfiguration.getProcessor(paymentParameters);
            }
            Assert.notNull(processor, "Failed to find appropriate processor for: " + paymentParameters);
            authorizationRequest.setPaymentProcessor(processor.getValue());
        }
        catch (Exception e) {
            LOGGER.error("Receieved exception while determining processor for {}", paymentParameters, e);
            throw new ProcessorSelectionFailureException(e.getMessage());
        }
        LOGGER.debug("Returning determined authorizationRequest: {}", authorizationRequest);
        return authorizationRequest;
    }

    private void validatePaymentParameters(PaymentParameters paymentParameters) {
        Assert.notNull(paymentParameters, "Payment parameters cannot be empty");

        LOGGER.debug("Validating paymentParameters: {}", paymentParameters);
    }

}
