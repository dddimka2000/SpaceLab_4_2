package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */


@Entity
@Data
public class ExchangeRates {

    // fixme using empty @Column is unnecessary

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private BigDecimal  exchange_rate;
    @Column
    private LocalDateTime date_refresh;
}
