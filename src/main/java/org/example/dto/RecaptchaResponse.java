package org.example.dto;

import lombok.Data;

public record RecaptchaResponse(Boolean success,String challege_ts,String hostname,Double score, String action) {
}
