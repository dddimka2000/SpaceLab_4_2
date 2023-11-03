package org.example.entity.property;

import jakarta.persistence.Entity;
import lombok.Data;
import org.example.entity.property.type.*;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Data
@Entity
public class PropertyInvestorObject extends _PropertyObject {
    private KitchenType kitchenType;
    private BathroomType bathroomType;
    private BalconyType balconyType;
    private WindowViewType windowViewType;
    private KitchenStoveType stoveType;
    private HeatingType heatingType;
    private PublicationStatus publicationStatus;
}
