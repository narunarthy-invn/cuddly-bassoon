package com.netflix.payments.configuration;

import com.netflix.payments.data.Country;
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
public class CountryBasedPaymentProcessorSelectionConfigurationImpl implements PaymentProcessorSelectionConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryBasedPaymentProcessorSelectionConfigurationImpl.class);

    public PaymentProcessor getProcessor(PaymentParameters paymentParameters) throws ProcessorSelectionFailureException {

        Country country = paymentParameters.getCountry();
        PaymentProcessor processor = null;
        switch (country) {
            case US:
            case CANADA:
                processor = PaymentProcessor.PROCESSOR1;
                break;
            case UK:
            case IRELAND:
                processor = PaymentProcessor.PROCESSOR2;
                break;
            default:
                return PaymentProcessor.PROCESSOR3;
        }

        LOGGER.debug("Selected processor for country {}: {}", country, processor);
        return processor;
    }
}
