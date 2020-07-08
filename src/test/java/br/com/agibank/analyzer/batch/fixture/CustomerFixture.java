package br.com.agibank.analyzer.batch.fixture;

import br.com.agibank.analyzer.batch.domain.Customer;
import br.com.agibank.analyzer.batch.domain.builder.CustomerBuilder;

public class CustomerFixture {

    public static Customer getOneCustomerByNameOne(){
        return new CustomerBuilder()
                .name("Cliente 1")
                .businessArea("IT")
                .cnpj("123456")
                .build();
    }

    public static Customer getOneCustomerByNameTwo(){
        return new CustomerBuilder()
                .name("Cliente 2")
                .businessArea("IT")
                .cnpj("123456")
                .build();
    }

}
