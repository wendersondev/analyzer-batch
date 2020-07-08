package br.com.agibank.analyzer.batch.domain;


import java.math.BigDecimal;
import java.util.Optional;

public class SaleItem {

    private Integer id;
    private Integer quantity;
    private BigDecimal price;

    public SaleItem(Integer id, Integer quantity, BigDecimal price){
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal valueSales() {
        return Optional.of(price)
                .map(c -> c.multiply(BigDecimal.valueOf(quantity)))
                .orElse(BigDecimal.ZERO);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
