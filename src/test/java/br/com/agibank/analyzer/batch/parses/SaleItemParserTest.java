package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SaleItemParserTest {

    @Test
    public void parseSuccess() throws Exception {

        String line = "1-99-101";
        SaleItem item = SaleItemParser.parse(line);

        assertTrue(item.getId().equals(1));
        assertTrue(item.getQuantity().doubleValue() == 99);
        assertTrue(item.getPrice().doubleValue() == 101);
    }

    @Test
    public void parseSucceedWithDecimals() throws Exception {

        String line = "2-38-3.50";
        SaleItem item = SaleItemParser.parse(line);

        assertTrue(item.getId().equals(2));
        assertTrue(item.getQuantity().doubleValue() == 38);
        assertTrue(item.getPrice().doubleValue() == 3.50);
    }

    @Test
    public void parseThrowsFormatExceptionForId() {

        try {
            String line = "A-33.56-1.50";
            SaleItem item = SaleItemParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsFormatExceptionForQuantity() {

        try {
            String line = "2-ZWY-11.50";
            SaleItem item = SaleItemParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsFormatExceptionForPrice() {

        try {
            String line = "2-631.45-ZWY";
            SaleItem item = SaleItemParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsInvalidFileDataSizeException() {

        try {
            String line = "2-237.345-1123-4";
            SaleItem item = SaleItemParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(InvalidFileDataSizeException.class));
        }
    }
}