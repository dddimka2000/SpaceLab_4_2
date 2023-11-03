package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Entity
@Table(name = "pages", schema = "my_bd", catalog = "")
@Data
public class PageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;

    // fixme instead of this column definition , just set length = 3000 / ...
    // text gives unlimited amount of symbols , using it before is my mistake
    @Column(columnDefinition = "TEXT")
    private String content;

}
