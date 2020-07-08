package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.Sale;
import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.domain.SalesMan;

import java.util.List;

public class SaleBuilder {

    private Integer id;
    private List<SaleItem> items;
    private String salesmanName;
    private SalesMan salesman;

    public SaleBuilder id(Integer id){
        this.id = id;
        return this;
    }

    public SaleBuilder items(List<SaleItem> items){
        this.items = items;
        return this;
    }

    public SaleBuilder salesmanName(String salesmanName){
        this.salesmanName = salesmanName;
        return this;
    }

    public SaleBuilder salesman(SalesMan salesman){
        this.salesman = salesman;
        return this;
    }

    public Sale build(){
        return new Sale(id,items,salesmanName,salesman);
    }

}
