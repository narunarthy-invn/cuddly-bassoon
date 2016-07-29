package com.netflix.payments.registry;

/**
 * @author sudutha.
 */

import com.netflix.payments.data.PaymentProcessor;

import java.util.Arrays;
import java.util.List;

import static com.netflix.payments.data.PaymentProcessor.*;

/**
 * Example of Registries: Registry which tracks a list of paymentProcessor available.
 */
public class PaymentProcessorRegistry {

    static List<PaymentProcessor> processorList = Arrays.asList(PROCESSOR1,
                                                                PROCESSOR2,
                                                                PROCESSOR3);

    public static List<PaymentProcessor> getProcessorList() {
        return processorList;
    }

    public static void setProcessorList(List<PaymentProcessor> processorList) {
        PaymentProcessorRegistry.processorList = processorList;
    }
}
