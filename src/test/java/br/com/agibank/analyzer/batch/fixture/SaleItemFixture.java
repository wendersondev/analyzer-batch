package br.com.agibank.analyzer.batch.fixture;

import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.domain.builder.SaleItemBuilder;

import java.math.BigDecimal;

public class SaleItemFixture {

    public static SaleItem getOneSaleItem(){
        return new SaleItemBuilder()
                .id(1)
                .price(BigDecimal.valueOf(2.55))
                .quantity(3)
                .build();
    }

    public static SaleItem getOneSaleItemTotalSix(){
        return new SaleItemBuilder()
                .id(1)
                .price(BigDecimal.valueOf(2))
                .quantity(3)
                .build();
    }
}
