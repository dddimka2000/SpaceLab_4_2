package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import org.example.util.property.*;

@Data
@Entity
public class PropertySecondaryObject extends _PropertyObject {

    private PropertySecondaryType propertyType;    // отдельная/коммунальная
    private LayoutType layoutType;                 // раздельная / ??
    private HouseProjectType houseProjectType;     // спецпроект
    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private BalconyType balconyType;
    private WindowViewType viewType;
    private KitchenStoveType stoveType;
    private HeatingType heatingType;
    private LadderType ladderType;
    private FloorType floorType;
    private WindowType windowType;
    private CarpentryType carpentryType;
    private FrontDoorType frontDoorType;

    private Boolean office;
}
