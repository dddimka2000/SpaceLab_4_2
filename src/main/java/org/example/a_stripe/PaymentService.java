package org.example.a_stripe;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
//TODO Не сделано, попробовал сделать, пока ждал ответ Тимур
    public PaymentIntent createPaymentIntent() {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency("usd")
                .setAmount(1000L)
                .build();

        try {
            return PaymentIntent.create(params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PaymentIntent: " + e.getMessage());
        }
    }
}