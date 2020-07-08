package br.com.agibank.analyzer.batch.domain;

import br.com.agibank.analyzer.batch.domain.builder.CustomerBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SaleBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SaleItemBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SalesManBuilder;
import br.com.agibank.analyzer.batch.exceptions.FileDataException;
import br.com.agibank.analyzer.batch.fixture.CustomerFixture;
import br.com.agibank.analyzer.batch.fixture.SaleFixture;
import br.com.agibank.analyzer.batch.fixture.SaleItemFixture;
import br.com.agibank.analyzer.batch.fixture.SalesManFixture;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GroupSaleTests {

    @Test
    public void notAddSaleWithoutSalesman(){
        GroupSale groupSale = new GroupSale();
        Sale sale = SaleFixture.getOneSaleWithOneItem();

        Sale saleNoMan = SaleFixture.getONeSaleWithoutSalesMan();

        try {
            groupSale.addSale(sale);
            assertTrue(groupSale.getSales().size() == 1);
            groupSale.addSale(saleNoMan);
        } catch (FileDataException e) {
            assertTrue(e.getClass().equals(FileDataException.class));
        }
    }

    @Test
    public void notAddEqualSalesMan() {
        GroupSale groupSale = new GroupSale();

        SalesMan salesman2 = SalesManFixture.getOneSalesManWithNameSalesMan2();

        groupSale.addSalesman(salesman2);

        assertTrue(groupSale.getSalesmen().size() == 1);

        SalesMan salesman2New = SalesManFixture.getOneSalesManWithNameSalesMan2();

        groupSale.addSalesman(salesman2New);

        assertTrue(groupSale.getSalesmen().size() == 1);

        SalesMan salesman3 = SalesManFixture.getOneSalesManWithNameSalesMan3();

        groupSale.addSalesman(salesman3);

        assertTrue(groupSale.getSalesmen().size() == 2);
    }

    @Test
    public void setSalesmanToSaleAddingSaleman() throws FileDataException {
        GroupSale groupSale = new GroupSale();
        groupSale.addSale(
                SaleFixture.getOneSaleWithObjectSalesMan()
        );

        groupSale.addSale(
                SaleFixture.getOneSaleWithItensTotalSix()
        );

        SalesMan salesman2 = SalesManFixture.getOneSalesManWithNameSalesMan2();

        groupSale.addSalesman(salesman2);

        Sale sale = groupSale.getSales().get(0);
        assertTrue(sale.getSalesman().getName().equals("SalesMan 1"));
        assertTrue(sale.getSalesman().getSalesAmount().doubleValue() == 7.65);

        sale = groupSale.getSales().get(1);
        assertTrue(sale.getSalesman().getName().equals("SalesMan 2"));
        assertTrue(sale.getSalesman().getSalesAmount().doubleValue() == 6);
    }

    @Test
    public void notSetSalesmanToSale() throws FileDataException {
        GroupSale groupSale = new GroupSale();
        groupSale.addSale(
                new SaleBuilder()
                        .id(1)
                        .salesman(SalesManFixture.getOneSalesManWithNameSalesMan())
                        .items(Arrays.asList(SaleItemFixture.getOneSaleItem()))
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(1)
                        .salesmanName("SalesMan 2")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(2.55))
                                                .quantity(3)
                                                .build()
                                )
                        )
                        .build()
        );

        SalesMan salesman3 = SalesManFixture.getOneSalesManWithNameSalesMan3();

        groupSale.addSalesman(salesman3);

        Sale sale = groupSale.getSales().get(0);
        assertTrue(sale.getSalesman().getName().equals("SalesMan 1"));

        sale = groupSale.getSales().get(1);
        assertTrue(sale.getSalesman() == null);
    }

    @Test
    public void notAddEqualCustomer(){
        GroupSale groupSale = new GroupSale();
        groupSale.addCustomer(CustomerFixture.getOneCustomerByNameOne());

        assertTrue(groupSale.getCustomers().size() == 1);

        groupSale.addCustomer(CustomerFixture.getOneCustomerByNameOne());

        assertTrue(groupSale.getCustomers().size() == 1);

        groupSale.addCustomer(CustomerFixture.getOneCustomerByNameTwo());
        assertTrue(groupSale.getCustomers().size() == 2);
    }

    @Test
    public void setSalesmanToSaleAddingSale() throws FileDataException{
        GroupSale groupSale = new GroupSale();

        SalesMan salesman1 = SalesManFixture.getOneSalesManWithNameSalesMan();
        groupSale.addSalesman(salesman1);

        SalesMan salesman2 = SalesManFixture.getOneSalesManWithNameSalesMan2();
        groupSale.addSalesman(salesman2);

        assertTrue(groupSale.getSalesmen().size() == 2);

        Sale noMansSale = new SaleBuilder()
                .id(1)
                .salesmanName("Any Salesman")
                .items(
                        Arrays.asList(
                                new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(3)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(noMansSale);
        Sale selected = groupSale.getSales().get(0);
        assertTrue(groupSale.getSales().size() == 1);
        assertTrue(selected.getSalesmanName().equals("Any Salesman"));
        assertTrue(selected.getSalesman() == null);

        Sale noVendedor1 = new SaleBuilder()
                .id(1)
                .salesmanName("SalesMan 1")
                .items(
                        Arrays.asList(
                               new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(3)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(noVendedor1);
        selected = groupSale.getSales().get(1);
        assertTrue(groupSale.getSales().size() == 2);
        assertTrue(selected.getSalesmanName().equals("SalesMan 1"));
        assertTrue(selected.getSalesman() != null);
        assertTrue(selected.getSalesman().getCpf().equals("2345"));
        assertTrue(selected.getSalesman().getSalesAmount().doubleValue() == 7.65);

        selected = groupSale.getSales().get(0);
        assertTrue(selected.getSalesmanName().equals("Any Salesman"));
        assertTrue(selected.getSalesman() == null);
    }

    @Test
    public void validateTotalOfSales() throws FileDataException {
        GroupSale groupSale = new GroupSale();
        groupSale.addSale(
                new SaleBuilder()
                        .id(2)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(5))
                                                .quantity(8)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(3))
                                                .quantity(4)
                                                .build()
                                )
                        )
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(1)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(10))
                                                .quantity(6)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(2))
                                                .quantity(5)
                                                .build()
                                )
                        )
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(3)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(1))
                                                .quantity(7)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(2))
                                                .quantity(3)
                                                .build()
                                )
                        )
                        .build()
        );

        assertTrue(groupSale.getSales().get(0).getTotal().doubleValue() == 52);
        assertTrue(groupSale.getSales().get(1).getTotal().doubleValue() == 70);
        assertTrue(groupSale.getSales().get(2).getTotal().doubleValue() == 13);
    }

    @Test
    public void validateTheGreaterSale() throws FileDataException {
        GroupSale groupSale = new GroupSale();
        groupSale.addSale(
                new SaleBuilder()
                        .id(1)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(5))
                                                .quantity(7)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(3))
                                                .quantity(4)
                                                .build()
                                )
                        )
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(2)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(10))
                                                .quantity(8)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(2))
                                                .quantity(5)
                                                .build()
                                )
                        )
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(3)
                        .salesmanName("SalesMan")
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(1))
                                                .quantity(6)
                                                .build(),
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(2))
                                                .quantity(4)
                                                .build()
                                )
                        )
                        .build()
        );

        Sale bestSale = groupSale.getGreaterSale();
        assertTrue(bestSale.getId().equals(2));
    }

    @Test
    public void validateTheWorstSalesman() throws FileDataException{
        GroupSale groupSale = new GroupSale();

        SalesMan salesman1 = new SalesManBuilder()
                .cpf("2345")
                .name("SalesMan 1")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
        groupSale.addSalesman(salesman1);

        SalesMan salesman2 = new SalesManBuilder()
                .cpf("12345")
                .name("SalesMan 2")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
        groupSale.addSalesman(salesman2);

        assertTrue(groupSale.getSalesmen().size() == 2);

        Sale sell1 = new SaleBuilder()
                .id(1)
                .salesman(salesman1)
                .items(
                        Arrays.asList(
                                new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(3)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(sell1);

        Sale sell2 = new SaleBuilder()
                .id(1)
                .salesman(salesman2)
                .items(
                        Arrays.asList(
                                new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(1)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(sell2);

        SalesMan worst = groupSale.getWorstSalesman();

        assertTrue(worst.getName().equals("SalesMan 2"));
        assertTrue(worst.getSalesAmount().doubleValue() == 2.55);
    }

    @Test
    public void validateWorstSalesmanWithZero() throws FileDataException{
        GroupSale groupSale = new GroupSale();

        SalesMan salesman1 = new SalesManBuilder()
                .cpf("2345")
                .name("SalesMan 1")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
        groupSale.addSalesman(salesman1);

        SalesMan salesman2 = new SalesManBuilder()
                .cpf("12345")
                .name("SalesMan 2")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
        groupSale.addSalesman(salesman2);

        SalesMan salesman3 = new SalesManBuilder()
                .cpf("34567")
                .name("SalesMan 3")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
        groupSale.addSalesman(salesman3);

        assertTrue(groupSale.getSalesmen().size() == 3);

        Sale sell1 = new SaleBuilder()
                .id(1)
                .salesman(salesman1)
                .items(
                        Arrays.asList(
                                new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(3)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(sell1);

        Sale sell2 = new SaleBuilder()
                .id(1)
                .salesman(salesman2)
                .items(
                        Arrays.asList(
                                new SaleItemBuilder()
                                        .id(1)
                                        .price(BigDecimal.valueOf(2.55))
                                        .quantity(1)
                                        .build()
                        )
                )
                .build();

        groupSale.addSale(sell2);

        SalesMan worst = groupSale.getWorstSalesman();

        assertTrue(worst.getName().equals("SalesMan 3"));
        assertTrue(worst.getSalesAmount().doubleValue() == 0);
    }

}
