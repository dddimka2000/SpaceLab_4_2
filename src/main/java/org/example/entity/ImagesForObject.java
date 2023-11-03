package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.property.type.TypeObject;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

/*

fixme
why is this class needed

 */

@Entity
@Data
public class ImagesForObject {

    // fixme why "idImage" instead of "id"
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer idImage;

    @Enumerated(EnumType.STRING)
    TypeObject typeObject;

    String path;
    Integer idObject;
}
