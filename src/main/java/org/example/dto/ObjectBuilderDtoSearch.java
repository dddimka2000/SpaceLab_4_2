package org.example.dto;

import lombok.Data;

/*

fixme

use key-values from message.properties in annotations, like this:

@NotBlank(message = {error.field.empty})
@Size(max = 50, message = {error.field.max-size})

in message.properties:
error.field.empty = "Заповніть поле!"
error.field.max-size = "Поле повинно мати не більше {max} символів"!

DO NOT USE OPTIONALS HERE

maybe split these DTO into multiple smaller ones , like AddressDTO or smth

 */

@Data
public class ObjectBuilderDtoSearch {
    String name;
    String district;
    String street;
    String topozone;
    Integer floorQuantity;
    Integer minPrice;
}
