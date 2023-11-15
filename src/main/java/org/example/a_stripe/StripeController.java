package org.example.a_stripe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StripeController {
    @GetMapping("/stripe")
    public String getStripe(){
        return "/stripe";
    }
}
