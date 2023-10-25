package org.example.dto;

public record RecaptchaResponse(Boolean success,String challege_ts,String hostname,Double score, String action) {
}
