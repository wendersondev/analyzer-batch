package br.com.agibank.analyzer.batch.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class SalesMan {

    public SalesMan() {
        salesAmount = new BigDecimal(0);
    }

    private String cpf;
    private String name;
    private BigDecimal salary;
    private BigDecimal salesAmount;

    public SalesMan(String cpf, String name, BigDecimal salary, BigDecimal salesAmount){
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
        this.salesAmount = salesAmount;
    }

    public void addSaleAmount(BigDecimal value) {
        if(salesAmount == null)
            salesAmount = new BigDecimal(0);
        salesAmount = salesAmount.add(value);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesMan)) return false;
        SalesMan salesMan = (SalesMan) o;
        return Objects.equals(getCpf(), salesMan.getCpf()) &&
                Objects.equals(getName(), salesMan.getName()) &&
                Objects.equals(getSalary(), salesMan.getSalary()) &&
                Objects.equals(getSalesAmount(), salesMan.getSalesAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCpf(), getName(), getSalary(), getSalesAmount());
    }
}
