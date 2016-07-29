package com.netflix.payments;

import com.netflix.payments.data.*;
import com.netflix.payments.exceptions.ProcessorSelectionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.netflix.payments.data.CardType.AmericanExpress;
import static com.netflix.payments.data.CardType.MasterCard;
import static com.netflix.payments.data.Country.*;
import static com.netflix.payments.data.CurrencyType.*;
import static org.testng.Assert.assertEquals;

/**
 * @author sudutha.
 */
@ContextConfiguration(locations = {"classpath:testPaymentProcessor.xml"})
public class PaymentProcessorSelectionTest extends AbstractTestNGSpringContextTests {

    @Autowired
    PaymentProcessorSelection paymentProcessorSelection;

    @Test(dataProvider = "createPaymentParamtersForProcessorVerification")
    public void verifyProcessor(PaymentParameters paymentParameters, AuthorizationRequest expectedAuthRequest) throws
                                                                                                                        Exception {

        AuthorizationRequest recievedAuthRequest = paymentProcessorSelection.findPaymentProcessor(paymentParameters);
        assertEquals(recievedAuthRequest.getPaymentProcessor(), expectedAuthRequest.getPaymentProcessor());
    }

    @Test(dataProvider = "createPaymentParamtersForAuthorizationAmountVerification")
    public void verifyAuthorizationAmount(PaymentParameters paymentParameters, AuthorizationRequest
            expectedAuthRequest) throws Exception {

        AuthorizationRequest recievedAuthRequest = paymentProcessorSelection.findPaymentProcessor(paymentParameters);
        assertEquals(recievedAuthRequest.getAuthorizationAmount(), expectedAuthRequest.getAuthorizationAmount());
        assertEquals(recievedAuthRequest.getCurrencyType(), expectedAuthRequest.getCurrencyType());
    }

    @Test(expectedExceptions = ProcessorSelectionFailureException.class)
    public void verifyExceptionOnFailure() throws ProcessorSelectionFailureException {
        paymentProcessorSelection.findPaymentProcessor(null);
    }

    @DataProvider(name = "createPaymentParamtersForProcessorVerification")
    Object[][] createPaymentParamtersForProcessorVerification() {

        //Sample set: Canada, canadian dollar, no amount, master card
        PaymentParameters param1 = new PaymentParameters(MasterCard, CANADA, CANADIAN_DOLLAR);

        //Sample set: Canada, canadian dollar, 0, MasterCard with same expected authorization request.
        PaymentParameters param3 = new PaymentParameters(0, CardType.VISA, CANADA, CANADIAN_DOLLAR);

        AuthorizationRequest authorizationRequest1 = new AuthorizationRequest();
        authorizationRequest1.setAuthorizationAmount(0);
        authorizationRequest1.setCurrencyType(CurrencyType.CANADIAN_DOLLAR);
        authorizationRequest1.setPaymentProcessor(PaymentProcessor.PROCESSOR1.getValue());

        //Sample set: UnitedStates, Euro, 2, Amex
        PaymentParameters param5 = new PaymentParameters(0, AmericanExpress, US, EURO);
        AuthorizationRequest authorizationRequest4 = new AuthorizationRequest();
        authorizationRequest4.setAuthorizationAmount(1);
        authorizationRequest4.setCurrencyType(CurrencyType.EURO);
        authorizationRequest4.setPaymentProcessor(PaymentProcessor.PROCESSOR2.getValue());

        //Sample set: UnitedStates, Euro, 2, Amex
        PaymentParameters param6 = new PaymentParameters(2, AmericanExpress, MEXICO, US_DOLLAR);
        AuthorizationRequest authorizationRequest5 = new AuthorizationRequest();
        authorizationRequest5.setAuthorizationAmount(2);
        authorizationRequest5.setCurrencyType(CurrencyType.US_DOLLAR);
        authorizationRequest5.setPaymentProcessor(PaymentProcessor.PROCESSOR3.getValue());

        //Sample set: UK, Pound, 2, Amex
        PaymentParameters ukPound = new PaymentParameters(2, AmericanExpress, UK, BRITISH_POUND);
        AuthorizationRequest authorizationRequestUKPound = new AuthorizationRequest();
        authorizationRequestUKPound.setAuthorizationAmount(2);
        authorizationRequestUKPound.setCurrencyType(CurrencyType.BRITISH_POUND);
        authorizationRequestUKPound.setPaymentProcessor(PaymentProcessor.PROCESSOR2.getValue());

        return new Object[][]{
                {param1, authorizationRequest1},
                {param3, authorizationRequest1},
                {param5, authorizationRequest4},
                {param6, authorizationRequest5},
                {ukPound, authorizationRequestUKPound}
        };
    }

    @DataProvider(name = "createPaymentParamtersForAuthorizationAmountVerification")
    Object[][] createPaymentParamtersForAuthorizationAmountVerification() {

        //Sample set: Canada, canadian dollar, 0, Amex
        PaymentParameters zeroAmountAmex = new PaymentParameters(0, AmericanExpress, CANADA, CANADIAN_DOLLAR);
        AuthorizationRequest zeroAmexAuthRequest = new AuthorizationRequest();
        zeroAmexAuthRequest.setAuthorizationAmount(1);
        zeroAmexAuthRequest.setCurrencyType(CurrencyType.CANADIAN_DOLLAR);
        zeroAmexAuthRequest.setPaymentProcessor(PaymentProcessor.PROCESSOR1.getValue());

        //Sample set: UnitedStates, US dollar, 2, Amex
        PaymentParameters nonZeroAmex = new PaymentParameters(2, AmericanExpress, US, US_DOLLAR);
        AuthorizationRequest nonZeroAmexAuthRequest = new AuthorizationRequest();
        nonZeroAmexAuthRequest.setAuthorizationAmount(2);
        nonZeroAmexAuthRequest.setCurrencyType(CurrencyType.US_DOLLAR);
        nonZeroAmexAuthRequest.setPaymentProcessor(PaymentProcessor.PROCESSOR1.getValue());

        //Sample set: UnitedStates, US dollar, 2, Amex
        PaymentParameters nullAmex = new PaymentParameters(AmericanExpress, US, US_DOLLAR);
        AuthorizationRequest nullAmexAuthRequest = new AuthorizationRequest();
        nullAmexAuthRequest.setAuthorizationAmount(1.0f);
        nullAmexAuthRequest.setCurrencyType(CurrencyType.US_DOLLAR);
        nullAmexAuthRequest.setPaymentProcessor(PaymentProcessor.PROCESSOR1.getValue());

        //Sample set: UnitedStates, US dollar, 2, Amex
        PaymentParameters nonZeroMasterCard = new PaymentParameters(0, MasterCard, US, US_DOLLAR);
        AuthorizationRequest nonZeroMasterAuthRequest = new AuthorizationRequest();
        nonZeroMasterAuthRequest.setAuthorizationAmount(0);
        nonZeroMasterAuthRequest.setCurrencyType(CurrencyType.US_DOLLAR);
        nonZeroMasterAuthRequest.setPaymentProcessor(PaymentProcessor.PROCESSOR1.getValue());

        return new Object[][]{
                {zeroAmountAmex,zeroAmexAuthRequest},
                {nonZeroAmex, nonZeroAmexAuthRequest},
                {nonZeroMasterCard, nonZeroMasterAuthRequest},
                {nullAmex, nullAmexAuthRequest}
        };
    }

}
