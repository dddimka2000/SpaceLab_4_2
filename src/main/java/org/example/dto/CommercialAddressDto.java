package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommercialAddressDto {
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer houseNumber;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String region;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String city;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String district;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String street;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String streetUkr;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String streetEng;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String zone;
    @NotNull(message = "Введите секцию дома")
    @Min(value = 1, message = "Введите корректную секцию дома")
    @Max(value = 100000001, message = "Введите корректную секцию дома")
    private Integer section;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer apartmentNumber;
}