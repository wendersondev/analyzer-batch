package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SalesItemListParserTest {

    @Test
    public void parseSuccess() throws Exception {

        String line = "1-34-10,2-33-1.50,3-40-0.10";
        List<SaleItem> itens = SalesItemListParser.parse(line);

        assertTrue(itens.size() == 3);

        SaleItem item = itens.get(0);
        assertTrue(item.getId().equals(1));
        assertTrue(item.getQuantity().doubleValue() == 34);
        assertTrue(item.getPrice().doubleValue() == 10);

        item = itens.get(1);
        assertTrue(item.getId().equals(2));
        assertTrue(item.getQuantity().doubleValue() == 33);
        assertTrue(item.getPrice().doubleValue() == 1.50);

        item = itens.get(2);
        assertTrue(item.getId().equals(3));
        assertTrue(item.getQuantity().doubleValue() == 40);
        assertTrue(item.getPrice().doubleValue() == 0.10);
    }

    @Test
    public void parseSuccessdWithVector() throws Exception {

        String line = "[1-34-10,2-33-1.50,3-40-0.10]";
        List<SaleItem> itens = SalesItemListParser.parse(line);

        assertTrue(itens.stream().count() == 3);

        SaleItem item = itens.get(0);
        assertTrue(item.getId().equals(1));
        assertTrue(item.getQuantity().doubleValue() == 34);
        assertTrue(item.getPrice().doubleValue() == 10);
    }

    @Test
    public void parseThrowsFormatExceptionForInteger() {

        try {
            String line = "1-34-10,2-33-1.50,A-40-0.10";
            List<SaleItem> itens = SalesItemListParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsNumberFormatExceptionForDecimal() {

        try {
            String line = "1-34-10,2-33-1.50,3-40-ASD";
            List<SaleItem> itens = SalesItemListParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsInvalidFileDataSizeException() {

        try {
            String line = "1-34-10,2-33-1.50,3-40-123-4";
            List<SaleItem> itens = SalesItemListParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(InvalidFileDataSizeException.class));
        }
    }
}