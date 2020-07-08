package br.com.agibank.analyzer.batch.domain.enums;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Objects;

import static org.junit.Assert.*;

public class TypeDataTests {

    private final String TYPE_SALESMAN = "001";
    private final String TYPE_CUSTOMER = "002";
    private final String TYPE_SALES = "003";
    private final String TYPE_NOT_EXIST = "004";

    @Test
    public void isCodeSalesManExists(){
        assertTrue(TypeData.isCodExists(TYPE_SALESMAN));
    }

    @Test
    public void isCodeCustomersExists(){
        assertTrue(TypeData.isCodExists(TYPE_CUSTOMER));
    }

    @Test
    public void isCodeSalesExists(){
        assertTrue(TypeData.isCodExists(TYPE_SALES));
    }

    @Test
    public void isCodeNotExists(){
        assertFalse(TypeData.isCodExists(TYPE_NOT_EXIST));
    }

    @Test
    public void getTypeDataCustomers(){
        assertEquals(TypeData.getCodeSplit(TYPE_CUSTOMER).name(),"CUSTOMER");
    }

    @Test
    public void getTypeDataSalesMan(){
        assertEquals(TypeData.getCodeSplit(TYPE_SALESMAN).name(),"SALESMAN");
    }

    @Test
    public void getTypeDataSales(){
        assertEquals(TypeData.getCodeSplit(TYPE_SALES).name(),"SALES");
    }

    @Test
    public void getTypeDatNotExists(){
        assertTrue(Objects.isNull( TypeData.getCodeSplit(TYPE_NOT_EXIST)));
    }

}
