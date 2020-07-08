package br.com.agibank.analyzer.batch.domain;

import java.util.Objects;

public class Customer {

    private String cnpj;
    private String name;
    private String businessArea;

    public Customer(String cnpj, String name, String businessArea){
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getCnpj(), customer.getCnpj()) &&
                Objects.equals(getName(), customer.getName()) &&
                Objects.equals(getBusinessArea(), customer.getBusinessArea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCnpj(), getName(), getBusinessArea());
    }
}
