package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.Customer;

public class CustomerBuilder {

    private String cnpj;
    private String name;
    private String businessArea;

    public CustomerBuilder cnpj(String cnpj){
        this.cnpj = cnpj;
        return this;
    }

    public CustomerBuilder name(String name){
        this.name = name;
        return this;
    }

    public CustomerBuilder businessArea(String businessArea){
        this.businessArea = businessArea;
        return this;
    }

    public Customer build(){
        return new Customer(cnpj,name,businessArea);
    }

}
