package org.example.dto;


// fixme
// records here , but nowhere else ? they are easy to use for DTO
public record RecaptchaResponse(Boolean success,String challege_ts,String hostname,Double score, String action) {
}
