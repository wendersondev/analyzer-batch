package br.com.agibank.analyzer.batch.domain;

import br.com.agibank.analyzer.batch.domain.builder.CustomerBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SaleBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SaleItemBuilder;
import br.com.agibank.analyzer.batch.domain.builder.SalesManBuilder;
import br.com.agibank.analyzer.batch.exceptions.FileDataException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class MapperGroupSaleTests {

    private GroupSale groupSale;

    @Before
    public void setup() throws FileDataException {
        groupSale = new GroupSale();

        groupSale.addSalesman(
                new SalesManBuilder()
                        .cpf("2345")
                        .name("SalesMan 1")
                        .salary(BigDecimal.valueOf(1234.56))
                        .build()
        );

        groupSale.addCustomer(
                new CustomerBuilder()
                        .businessArea("IT")
                        .cnpj("12345678909876")
                        .name("Customer")
                        .build()
        );

        groupSale.addSale(
                new SaleBuilder()
                        .id(1)
                        .salesman(new SalesManBuilder()
                                .cpf("2345")
                                .name("Vendedor 1")
                                .salary(BigDecimal.valueOf(1234.56))
                                .build()
                        )
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
    }

    @Test
    public void getSalesmen() {

        assertTrue(groupSale.getSalesmen().size() == 1);
        SalesMan salesman = groupSale.getSalesmen().get(0);
        assertTrue(salesman.getCpf().equals("2345"));
    }

    @Test
    public void addSalesman() {
        assertTrue(groupSale.getSalesmen().size() == 1);

        groupSale.addSalesman(new SalesManBuilder().name("Outro").build());
        assertTrue(groupSale.getSalesmen().size() == 2);
        SalesMan salesman = groupSale.getSalesmen().get(1);
        assertTrue(Objects.isNull(salesman.getCpf()));
    }

    @Test
    public void getCustomers() {

        assertTrue(groupSale.getCustomers().size() == 1);
        Customer customer = groupSale.getCustomers().get(0);
        assertTrue(customer.getCnpj().equals("12345678909876"));

    }

    @Test
    public void addCustomer() {

        assertTrue(groupSale.getCustomers().size() == 1);

        groupSale.addCustomer(new CustomerBuilder().name("Mais um").build());
        assertTrue(groupSale.getCustomers().size() == 2);
        Customer customer = groupSale.getCustomers().get(1);
        assertTrue(Objects.isNull(customer.getCnpj()));
    }

    @Test
    public void getSales() {

        assertTrue(groupSale.getSales().size() == 1);
        Sale sale = groupSale.getSales().get(0);
        assertTrue(sale.getItems().size() == 1);

    }

    @Test
    public void addSale() throws FileDataException {
        assertTrue(groupSale.getSales().size() == 1);

        groupSale.addSale(
                new SaleBuilder()
                        .id(2)
                        .salesman(new SalesManBuilder()
                                .cpf("2345")
                                .name("Vendedor 1")
                                .salary(BigDecimal.valueOf(1234.56))
                                .build()
                        )
                        .items(
                                Arrays.asList(
                                        new SaleItemBuilder()
                                                .id(1)
                                                .price(BigDecimal.valueOf(5))
                                                .quantity(7)
                                                .build()
                                )
                        )
                        .build()
        );

        assertTrue(groupSale.getSales().size() == 2);
        Sale sale = groupSale.getSales().get(1);
        assertTrue(sale.getItems().size() == 1);
        assertTrue(sale.getId() == 2);
    }

}
