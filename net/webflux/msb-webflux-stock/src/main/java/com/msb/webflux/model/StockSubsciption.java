package com.msb.webflux.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockSubsciption {
    private String email;
    private String symbol;

}
