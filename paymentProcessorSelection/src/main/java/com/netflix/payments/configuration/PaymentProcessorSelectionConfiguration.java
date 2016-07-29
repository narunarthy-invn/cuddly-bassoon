package com.netflix.payments.configuration;

import com.netflix.payments.data.PaymentParameters;
import com.netflix.payments.data.PaymentProcessor;
import com.netflix.payments.exceptions.ProcessorSelectionFailureException;

/**
 * @author sudutha.
 */
public interface PaymentProcessorSelectionConfiguration {

    default PaymentProcessor getProcessor(PaymentParameters value) throws ProcessorSelectionFailureException {
        throw new ProcessorSelectionFailureException("Need to be implemented");
    }
}
