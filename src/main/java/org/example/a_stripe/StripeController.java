package org.example.a_stripe;

import jakarta.validation.Valid;
import org.example.a_stripe.model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StripeController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @GetMapping("/stripe")
    public String getStripe(Model model) {
        model.addAttribute("request", new Request());
        return "/stripe";
    }

    @PostMapping("/stripe")
    public String showCard(@ModelAttribute @Valid Request request,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "/stripe";
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("productName", request.getProductName());
        return "/checkout";
    }
}
