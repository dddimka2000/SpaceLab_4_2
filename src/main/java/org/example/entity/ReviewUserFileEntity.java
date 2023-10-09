package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review_user_file", schema = "my_bd", catalog = "")
@Data
public class ReviewUserFileEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "file_path")
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userByUserId;

}
