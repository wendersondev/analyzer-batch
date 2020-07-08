package br.com.agibank.analyzer.batch.domain;

import java.math.BigDecimal;
import java.util.List;

public class Sale {

    private Integer id;
    private List<SaleItem> items;
    private String salesmanName;
    private SalesMan salesman;

    private BigDecimal total;

    public Sale(Integer id, List<SaleItem> items, String salesmanName,SalesMan salesman){
        this.id = id;
        this.items = items;
        this.salesmanName = salesmanName;
        this.salesman = salesman;
    }

    public BigDecimal getTotalValue() {
        return items
                .stream()
                .map(c-> c.valueSales())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateTotal() {
        this.setTotal(new BigDecimal(0));

        BigDecimal sum = new BigDecimal(0);
        for (SaleItem item: items) {
            sum = sum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        setTotal(sum);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public SalesMan getSalesman() {
        return salesman;
    }

    public void setSalesman(SalesMan salesman) {
        this.salesman = salesman;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
