package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.Sale;
import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SaleParserTest {

    @Test
    public void parseSuccess() throws Exception {

        String line = "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çWalleide";
        Sale sale = SaleParser.parse(line);

        assertTrue(sale.getId().equals(8));
        assertTrue(sale.getSalesmanName().equals("Walleide"));
        assertTrue(sale.getItems().size() == 3);

        SaleItem item = sale.getItems().get(0);
        assertTrue(item.getId().equals(1));
        assertTrue(item.getQuantity().doubleValue() == 34);
        assertTrue(item.getPrice().doubleValue() == 10);

        item = sale.getItems().get(1);
        assertTrue(item.getId().equals(2));
        assertTrue(item.getQuantity().doubleValue() == 33);
        assertTrue(item.getPrice().doubleValue() == 1.50);

        item = sale.getItems().get(2);
        assertTrue(item.getId().equals(3));
        assertTrue(item.getQuantity().doubleValue() == 40);
        assertTrue(item.getPrice().doubleValue() == 0.10);
    }

    @Test
    public void parseThrowsExceptionType() {

        String message = "The line must start with " + Constants.TYPE_SALES;
        String line = "001ç08ç[1-34-10,2-33-1.50,3-40-0.10]çWalleide";
        try {
            Sale sale = SaleParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(message));
        }

    }

    @Test
    public void ParseThrowsFormatExceptionForInteger() {

        try {
            String line = "003çACSç[1-34-10,2-33-1.50,3-40-0.10]çWalleide";
            Sale sale = SaleParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseLineThrowsFormatExceptionDecimal() {

        try {
            String line = "003ç1ç[1-34-10,2-33-CXS,3-40-0.10]çWalleide";
            Sale sale = SaleParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsInvalidFileDataSizeException() {

        try {
            String line = "003ç1ç[1-34-10,2-33-123,3-40-0.10]çWalleide";
            Sale sale = SaleParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(InvalidFileDataSizeException.class));
        }
    }
}