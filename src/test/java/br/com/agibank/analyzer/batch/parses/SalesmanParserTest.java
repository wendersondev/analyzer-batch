package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SalesMan;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SalesmanParserTest {

    @Test
    public void parseSuccess() throws Exception {

        String line = "001ç3245678865434çBruno Abreuç1299.99";
        SalesMan salesman = SalesmanParser.parse(line);

        assertTrue(salesman.getCpf().equals("3245678865434"));
        assertTrue(salesman.getName().equals("Bruno Abreu"));
        assertTrue(salesman.getSalary().doubleValue() == 1299.99);
    }

    @Test
    public void parseThrowsException() {

        String message = "The line must start with " + Constants.TYPE_SALES_MAN;
        String line = "002ç3245678865434çBruno Abreuç1299.99";
        try {
            SalesMan salesman = SalesmanParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(message));
        }

    }

    @Test
    public void parseThrowsFormatException() {

        String message = "The line must start with " + Constants.TYPE_SALES_MAN;
        String line = "001ç3245678865434çBruno AbreuçSALARY";
        try {
            SalesMan salesman = SalesmanParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(NumberFormatException.class));
        }
    }

    @Test
    public void parseThrowsInvalidFileDataSizeException() {

        try {
            String line = "001ç3245678865434çBruno Abreuç7800çOutro";
            SalesMan salesman = SalesmanParser.parse(line);
        } catch (Exception e) {
            assertTrue(e.getClass().equals(InvalidFileDataSizeException.class));
        }
    }
}