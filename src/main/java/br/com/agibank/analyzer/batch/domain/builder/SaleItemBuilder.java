package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.SaleItem;

import java.math.BigDecimal;

public class SaleItemBuilder {

    private Integer id;
    private Integer quantity;
    private BigDecimal price;

    public SaleItemBuilder id(Integer id){
        this.id = id;
        return this;
    }

    public SaleItemBuilder quantity(Integer quantity){
        this.quantity = quantity;
        return this;
    }

    public SaleItemBuilder price(BigDecimal price){
        this.price = price;
        return this;
    }

    public SaleItem build(){
        return new SaleItem(id,quantity,price);
    }

}
