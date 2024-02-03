package com.msb.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class StockSubscriptionDO {
    @Id
    private Long id;

    private String email;

    private String symbol;
}
