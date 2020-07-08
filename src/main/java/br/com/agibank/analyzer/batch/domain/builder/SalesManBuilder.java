package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.SalesMan;

import java.math.BigDecimal;

public class SalesManBuilder {

    private String cpf;
    private String name;
    private BigDecimal salary;
    private BigDecimal salesAmount = new BigDecimal(0);

    public SalesManBuilder cpf(String cpf){
        this.cpf = cpf;
        return this;
    }

    public SalesManBuilder name(String name){
        this.name = name;
        return this;
    }

    public SalesManBuilder salary(BigDecimal salary){
        this.salary = salary;
        return this;
    }

    public SalesManBuilder salesAmount(BigDecimal salesAmount){
        this.salesAmount = salesAmount;
        return this;
    }

    public SalesMan build(){
        return new SalesMan(cpf,name,salary,salesAmount);
    }

}
