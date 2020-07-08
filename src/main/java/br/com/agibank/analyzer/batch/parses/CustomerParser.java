package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.Customer;
import br.com.agibank.analyzer.batch.domain.builder.CustomerBuilder;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;

public class CustomerParser {

    public static Customer parse(String line) throws Exception {

        if(!line.startsWith(Constants.TYPE_CUSTOMER))
            throw new Exception("The line must start with " + Constants.TYPE_CUSTOMER);

        String[] data = line.split(Constants.DELIMITER);

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Customer data size must be 4: " + line);
        }

        Customer customer = new CustomerBuilder()
                .cnpj(data[1])
                .name(data[2])
                .businessArea(data[3])
                .build();

        return customer;
    }

}
