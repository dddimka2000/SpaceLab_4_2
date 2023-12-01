package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.ToString;
import org.example.entity.property.type.*;

@Data
@Entity
@ToString(callSuper = true)
public class PropertySecondaryObject extends _PropertyObject {

    private PropertySecondaryType propertyType;    // отдельная/коммунальная
    private LayoutType layoutType;                 // раздельная / ??
    private HouseProjectType houseProjectType;     // спецпроект
    private LadderType ladderType;
    private FloorType floorType;
    private WindowType windowType;
    private CarpentryType carpentryType;
    private FrontDoorType frontDoorType;
    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private BalconyType balconyType;
    private WindowViewType viewType;
    private KitchenStoveType stoveType;
    private HeatingType heatingType;
    private PublicationStatus publicationStatus;
    private Boolean office;
    private Integer residentialComplexId;
}
