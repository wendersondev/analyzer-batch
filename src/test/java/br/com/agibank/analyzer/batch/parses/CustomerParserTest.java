package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.Customer;
import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CustomerParserTest {

    @Test
    public void parseSuccess() throws Exception {

        String line = "002ç2345675434544345çTalita LarissaçIT";
        Customer customer = CustomerParser.parse(line);

        assertTrue(customer.getCnpj().equals("2345675434544345"));
        assertTrue(customer.getName().equals("Talita Larissa"));
        assertTrue(customer.getBusinessArea().equals("IT"));
    }

    @Test
    public void parseThriwsException() {

        String message = "The line must start with " + Constants.TYPE_CUSTOMER;
        String line = "001ç2345675434544345çMarcos RobertoçIT";
        try {
            Customer customer = CustomerParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(message));
        }

    }

    @Test
    public void parseThrowsInvalidFileDataSizeException() {

        try {
            String line = "001ç2345675434544345çMariaJoseçRuralçEcommerce";
            SaleItem item = SaleItemParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(InvalidFileDataSizeException.class));
        }
    }
}