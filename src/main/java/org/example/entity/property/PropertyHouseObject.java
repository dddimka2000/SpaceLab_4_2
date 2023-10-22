package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import org.example.entity.property.type.*;

@Data
@Entity
public class PropertyHouseObject extends _PropertyObject {

    private String cadastralNumber;
    private String plotDescription;

    private PropertyHouseType houseType;
    private Integer plotAreaTotal, plotAreaFree;
    private Boolean landOwnershipPresent;
    private PlotPurposeType plotPurposeType;
    private Integer houseQuantity, bedroomQuantity;

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
