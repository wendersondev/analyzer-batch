package br.com.agibank.analyzer.batch.fixture;

import br.com.agibank.analyzer.batch.domain.SalesMan;
import br.com.agibank.analyzer.batch.domain.builder.SalesManBuilder;

import java.math.BigDecimal;

public class SalesManFixture {

    public static SalesMan getOneSalesManWithNameSalesMan(){

        return  new SalesManBuilder()
                .cpf("2345")
                .name("SalesMan 1")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
    }


    public static SalesMan getOneSalesManWithNameSalesMan2(){

        return  new SalesManBuilder()
                .cpf("12345")
                .name("SalesMan 2")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
    }

    public static SalesMan getOneSalesManWithNameSalesMan3(){

        return  new SalesManBuilder()
                .cpf("12345")
                .name("SalesMan 3")
                .salary(BigDecimal.valueOf(1234.56))
                .build();
    }
}
