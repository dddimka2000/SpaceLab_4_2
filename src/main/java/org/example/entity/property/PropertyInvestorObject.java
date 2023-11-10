package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.ToString;
import org.example.entity.property.type.*;

@Data
@Entity
@ToString(callSuper = true)
public class PropertyInvestorObject extends _PropertyObject {
    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private BalconyType balconyType;
    private WindowViewType windowViewType;
    private KitchenStoveType kitchenStoveType;
    private HeatingType heatingType;
    private PublicationStatus publicationStatus;
    private Integer residentialComplexId;


}
