package br.com.agibank.analyzer.batch.fixture;

import br.com.agibank.analyzer.batch.domain.Sale;
import br.com.agibank.analyzer.batch.domain.builder.SaleBuilder;
import java.util.Arrays;

public class SaleFixture {

    public static Sale getOneSaleWithOneItem(){
        return new SaleBuilder()
                .id(1)
                .salesmanName("SalesMan")
                .items(
                        Arrays.asList(
                                SaleItemFixture.getOneSaleItem()
                        )
                )
                .build();
    }

    public static Sale getONeSaleWithoutSalesMan(){
        return new SaleBuilder()
                .id(1)
                .items(
                        Arrays.asList(
                                SaleItemFixture.getOneSaleItem()
                        )
                )
                .build();
    }

    public static Sale getOneSaleWithObjectSalesMan(){
        return new SaleBuilder()
                .id(1)
                .salesman(SalesManFixture.getOneSalesManWithNameSalesMan())
                .items(
                        Arrays.asList(
                                SaleItemFixture.getOneSaleItem()
                        )
                )
                .build();
    }

    public static Sale getOneSaleWithItensTotalSix(){
        return new SaleBuilder()
                .id(1)
                .salesmanName("SalesMan 2")
                .items(
                        Arrays.asList(
                                SaleItemFixture.getOneSaleItemTotalSix()
                        )
                )
                .build();
    }

}
