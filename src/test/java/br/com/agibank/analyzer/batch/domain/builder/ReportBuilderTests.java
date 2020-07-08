package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.Report;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReportBuilderTests {

    @Test
    public void builderTwoReports(){

        Report report1 = new ReportBuilder().idSales(1).qtdCustomers(2).salesMan("SAL1")
                .qtdSalesMan(2).build();

        Report report2 = new ReportBuilder().idSales(2).qtdCustomers(3).salesMan("SAL2")
                .qtdSalesMan(3).build();

        assertTrue(Objects.nonNull(report1));
        assertTrue(Objects.nonNull(report2));
        assertEquals(report1.getIdSales(),Integer.valueOf(1));
        assertEquals(report2.getIdSales(),Integer.valueOf(2));
        assertEquals(report1.getQtdCustomers(),Integer.valueOf(2));
        assertEquals(report2.getQtdCustomers(),Integer.valueOf(3));
        assertEquals(report1.getQtdSalesMan(),Integer.valueOf(2));
        assertEquals(report2.getQtdSalesMan(),Integer.valueOf(3));
        assertEquals(report1.getSalesMan(),"SAL1");
        assertEquals(report2.getSalesMan(),"SAL2");

    }

    @Test
    public void builderThreeReports(){

        Report report1 = new ReportBuilder().idSales(1).qtdCustomers(2).salesMan("SAL1")
                .qtdSalesMan(2).build();

        Report report2 = new ReportBuilder().idSales(2).qtdCustomers(3).salesMan("SAL2")
                .qtdSalesMan(3).build();

        Report report3 = new ReportBuilder().idSales(3).qtdCustomers(4).salesMan("SAL3")
                .qtdSalesMan(4).build();

        assertTrue(Objects.nonNull(report1));
        assertTrue(Objects.nonNull(report2));
        assertTrue(Objects.nonNull(report3));
        assertEquals(report1.getIdSales(),Integer.valueOf(1));
        assertEquals(report2.getIdSales(),Integer.valueOf(2));
        assertEquals(report3.getIdSales(),Integer.valueOf(3));
        assertEquals(report1.getQtdCustomers(),Integer.valueOf(2));
        assertEquals(report2.getQtdCustomers(),Integer.valueOf(3));
        assertEquals(report3.getQtdCustomers(),Integer.valueOf(4));
        assertEquals(report1.getQtdSalesMan(),Integer.valueOf(2));
        assertEquals(report2.getQtdSalesMan(),Integer.valueOf(3));
        assertEquals(report3.getQtdSalesMan(),Integer.valueOf(4));
        assertEquals(report1.getSalesMan(),"SAL1");
        assertEquals(report2.getSalesMan(),"SAL2");
        assertEquals(report3.getSalesMan(),"SAL3");

    }

}
