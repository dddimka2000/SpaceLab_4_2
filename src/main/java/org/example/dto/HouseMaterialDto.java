package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.*;

@Data
public class HouseMaterialDto {
    private String wallMaterial,wallMaterialEng, wallMaterialUkr;
    private HouseState stateInterior;
    private HouseState stateHouse;
    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private GasType gasType;
    private WaterType waterType;
    private SewageType sewageType;
    private HeatingType heatingType;
    private LadderType ladderType;
    private RoofType roofType;
    private FloorType floorType;
    private WindowType windowType;
    private CarpentryType carpentryType;
    private FrontDoorType frontDoorType;
}
