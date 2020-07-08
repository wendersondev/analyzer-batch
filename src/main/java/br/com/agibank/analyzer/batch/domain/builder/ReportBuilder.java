package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.Report;

public class ReportBuilder {

    private Integer qtdCustomers;
    private Integer qtdSalesMan;
    private Integer idSales;
    private String  salesMan;

    public ReportBuilder qtdCustomers(Integer qtdCustomers){
        this.qtdCustomers = qtdCustomers;
        return this;
    }

    public ReportBuilder qtdSalesMan(Integer qtdSalesMan){
        this.qtdSalesMan = qtdSalesMan;
        return this;
    }

    public ReportBuilder idSales(Integer idSales){
        this.idSales = idSales;
        return this;
    }

    public ReportBuilder salesMan(String salesMan){
        this.salesMan = salesMan;
        return this;
    }

    public Report build(){
        return new Report(qtdCustomers,qtdSalesMan,idSales,salesMan);
    }

}
