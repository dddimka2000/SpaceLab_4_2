package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import org.example.entity.property.type.*;

@Data
@Entity
public class PropertyInvestorObject extends _PropertyObject {

    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private BalconyType balconyType;
    private WindowViewType windowViewType;
    private KitchenStoveType stoveType;
    private HeatingType heatingType;

}
