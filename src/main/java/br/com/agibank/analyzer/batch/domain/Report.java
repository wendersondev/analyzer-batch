package br.com.agibank.analyzer.batch.domain;

public class Report {

    private Integer qtdCustomers;
    private Integer qtdSalesMan;
    private Integer idSales;
    private String  salesMan;

    public Report(Integer qtdCustomers,Integer qtdSalesMan,Integer idSales,String salesMan){
        this.qtdCustomers = qtdCustomers;
        this.qtdSalesMan = qtdSalesMan;
        this.idSales = idSales;
        this.salesMan = salesMan;
    }

    public Integer getQtdCustomers() {
        return qtdCustomers;
    }

    public void setQtdCustomers(Integer qtdCustomers) {
        this.qtdCustomers = qtdCustomers;
    }

    public Integer getQtdSalesMan() {
        return qtdSalesMan;
    }

    public void setQtdSalesMan(Integer qtdSalesMan) {
        this.qtdSalesMan = qtdSalesMan;
    }

    public Integer getIdSales() {
        return idSales;
    }

    public void setIdSales(Integer idSales) {
        this.idSales = idSales;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    @Override
    public String toString() {
        return "Report{" +
                "qtdCustomers=" + qtdCustomers +
                ", qtdSalesMan=" + qtdSalesMan +
                ", idSales=" + idSales +
                ", salesMan='" + salesMan + '\'' +
                '}';
    }
}
