package br.com.agibank.analyzer.batch.domain.builder;

import br.com.agibank.analyzer.batch.domain.Customer;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerBuilderTests {

    @Test
    public void builderTwoCustomers(){
        Customer customer1 = new CustomerBuilder().name("A").cnpj("123").businessArea("AB").build();
        Customer customer2 = new CustomerBuilder().name("B").cnpj("456").businessArea("CD").build();

        assertTrue(Objects.nonNull(customer1));
        assertTrue(Objects.nonNull(customer2));
        assertEquals(customer1.getCnpj(),"123");
        assertEquals(customer2.getCnpj(),"456");
        assertEquals(customer1.getBusinessArea(),"AB");
        assertEquals(customer2.getBusinessArea(),"CD");
        assertEquals(customer1.getName(),"A");
        assertEquals(customer2.getName(),"B");
    }

    @Test
    public void builderThreeCustomers(){
        Customer customer1 = new CustomerBuilder().name("A").cnpj("123").businessArea("AB").build();
        Customer customer2 = new CustomerBuilder().name("B").cnpj("456").businessArea("CD").build();
        Customer customer3 = new CustomerBuilder().name("C").cnpj("789").businessArea("EF").build();

        assertTrue(Objects.nonNull(customer1));
        assertTrue(Objects.nonNull(customer2));
        assertEquals(customer1.getCnpj(),"123");
        assertEquals(customer2.getCnpj(),"456");
        assertEquals(customer3.getCnpj(),"789");
        assertEquals(customer1.getBusinessArea(),"AB");
        assertEquals(customer2.getBusinessArea(),"CD");
        assertEquals(customer3.getBusinessArea(),"EF");
        assertEquals(customer1.getName(),"A");
        assertEquals(customer2.getName(),"B");
        assertEquals(customer3.getName(),"C");
    }


}
