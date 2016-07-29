package com.netflix.payments.configuration;

import com.netflix.payments.data.CurrencyType;
import com.netflix.payments.data.PaymentParameters;
import com.netflix.payments.data.PaymentProcessor;
import com.netflix.payments.exceptions.ProcessorSelectionFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author sudutha.
 */
@Component
public class CurrencyBasedPaymentProcessorSelectionConfigurationImpl implements PaymentProcessorSelectionConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyBasedPaymentProcessorSelectionConfigurationImpl.class);

    @Override

    public PaymentProcessor getProcessor(PaymentParameters paymentParameters) throws ProcessorSelectionFailureException {

        CurrencyType currencyType = paymentParameters.getCurrencyType();
        PaymentProcessor processor = null;
        switch (currencyType) {
            case EURO:
                processor = PaymentProcessor.PROCESSOR2;
                break;
            case JAPANESE_YEN:
                processor = PaymentProcessor.PROCESSOR1;
                break;
            default:
        }

        LOGGER.debug("Selected processor for country {}: {}", currencyType, processor);
        return processor;
    }
}
