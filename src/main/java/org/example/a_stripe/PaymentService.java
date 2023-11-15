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
                .setAmount(1000L) // Сумма в центах (например, 1000 центов = $10.00)
                .build();

        try {
            return PaymentIntent.create(params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PaymentIntent: " + e.getMessage());
        }
    }
}