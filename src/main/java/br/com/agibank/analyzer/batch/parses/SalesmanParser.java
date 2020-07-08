package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SalesMan;
import br.com.agibank.analyzer.batch.domain.builder.SalesManBuilder;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;

import java.math.BigDecimal;

public class SalesmanParser {

    public static SalesMan parse(String line) throws Exception {

        if(!line.startsWith(Constants.TYPE_SALES_MAN))
            throw new Exception("The line must start with " + Constants.TYPE_SALES_MAN);

        String[] data = line.split(Constants.DELIMITER);

        if(data.length != 4){
            throw new InvalidFileDataSizeException("SalesMan data size must be 4");
        }

        SalesMan salesman = new SalesManBuilder()
                .cpf(data[1])
                .name(data[2])
                .salary( new BigDecimal(data[3]) )
                .build();

        return salesman;
    }

}
