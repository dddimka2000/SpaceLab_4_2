package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import org.example.entity.property.type.*;

import java.time.LocalDate;

@Data
@Entity
public class PropertyCommercialObject extends _PropertyObject{
    private PublicationStatus publicationStatus;
    private String cadastralNumber;
    private String plotDescription;

    private LocalDate dateDeliveryHouse;

    private PropertyCommercialType propertyCommercialType;
    private Integer areaUseful;
    private Integer plotAreaTotal, plotAreaFree;
    private Boolean landOwnershipPresent;
    private PlotPurposeType plotPurposeType;

    private HouseState stateInterior;
    private PropertyStructureState propertyStructureState;
    private BathroomType bathroomType;
    private WindowViewType windowViewType;

    private Boolean environment, parking, fund, facade, railway;
    private PropertyHouseType houseType;

    private GasType gasType;
    private WaterType waterType;
    private SewageType sewageType;
    private HeatingType heatingType;
    private AirConditioningType airConditioningType;
    private VentilationType ventilationType;
    private LadderType ladderType;
    private ElectricityType electricityType;
    private FloorType floorType;
    private WindowType windowType;
    private CarpentryType carpentryType;
    private FrontDoorType frontDoorType;
}
