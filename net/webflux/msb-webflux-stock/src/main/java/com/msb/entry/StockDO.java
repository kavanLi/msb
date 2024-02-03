package com.msb.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class StockDO {
    @Id
    private Long id;

    private String symbol;

    private String name;
}
