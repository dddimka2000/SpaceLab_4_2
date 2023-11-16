package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.entity.property.type.*;

@Data
public class HouseMaterialDto {
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String wallMaterial,wallMaterialEng, wallMaterialUkr;
    @NotNull(message = "{error.field.empty}")
    private HouseState stateInterior;
    @NotNull(message = "{error.field.empty}")
    private HouseState stateHouse;
    @NotNull(message = "{error.field.empty}")
    private KitchenType kitchenType;
    @NotNull(message = "{error.field.empty}")
    private BathroomType bathroomType;
    @NotNull(message = "{error.field.empty}")
    private GasType gasType;
    @NotNull(message = "{error.field.empty}")
    private WaterType waterType;
    @NotNull(message = "{error.field.empty}")
    private SewageType sewageType;
    @NotNull(message = "{error.field.empty}")
    private HeatingType heatingType;
    @NotNull(message = "{error.field.empty}")
    private LadderType ladderType;
    @NotNull(message = "{error.field.empty}")
    private RoofType roofType;
    @NotNull(message = "{error.field.empty}")
    private FloorType floorType;
    @NotNull(message = "{error.field.empty}")
    private WindowType windowType;
    @NotNull(message = "{error.field.empty}")
    private CarpentryType carpentryType;
    @NotNull(message = "{error.field.empty}")
    private FrontDoorType frontDoorType;
}
