package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.entity.property.type.*;

@Data
public class CommercialMaterialAndAreaDto {
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer areaTotal, areaUseful;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String roomMeters;
    @NotNull(message = "{error.field.empty}")
    private Double heightCeiling;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer plotAreaTotal, plotAreaFree;
    @NotNull(message = "{error.field.empty}")
    private PlotPurposeType plotPurposeType;
    @NotNull(message = "{error.field.empty}")
    private Boolean landOwnershipPresent;
    @NotNull(message = "{error.field.empty}")
    private HouseState stateInterior;
    @NotNull(message = "{error.field.empty}")
    private PropertyStructureState propertyStructureState;
    @NotNull(message = "{error.field.empty}")
    private BathroomType bathroomType;
    @NotNull(message = "{error.field.empty}")
    private WindowViewType windowViewType;
    @NotNull(message = "{error.field.empty}")
    private Boolean environment, parking, fund, facade, railway;
    @NotNull(message = "{error.field.empty}")
    private GasType gasType;
    @NotNull(message = "{error.field.empty}")
    private WaterType waterType;
    @NotNull(message = "{error.field.empty}")
    private SewageType sewageType;
    @NotNull(message = "{error.field.empty}")
    private HeatingType heatingType;
    @NotNull(message = "{error.field.empty}")
    private AirConditioningType airConditioningType;
    @NotNull(message = "{error.field.empty}")
    private VentilationType ventilationType;
    @NotNull(message = "{error.field.empty}")
    private LadderType ladderType;
    @NotNull(message = "{error.field.empty}")
    private ElectricityType electricityType;
    @NotNull(message = "{error.field.empty}")
    private FloorType floorType;
    @NotNull(message = "{error.field.empty}")
    private WindowType windowType;
    @NotNull(message = "{error.field.empty}")
    private CarpentryType carpentryType;
    @NotNull(message = "{error.field.empty}")
    private FrontDoorType frontDoorType;
}