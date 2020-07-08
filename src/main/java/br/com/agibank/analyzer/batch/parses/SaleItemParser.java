package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.domain.builder.SaleItemBuilder;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;

import java.math.BigDecimal;


public class SaleItemParser {

    private static final String SEPARATOR = "-";

    public static SaleItem parse(String line) throws InvalidFileDataSizeException {
        String[] data = line.split(SEPARATOR);

        if(data.length != 3){
            throw new InvalidFileDataSizeException("Sale item data size must be 4");
        }

        SaleItem item = new SaleItemBuilder()
                .id(new Integer(data[0]))
                .quantity(Integer.parseInt(data[1]))
                .price(new BigDecimal(data[2]))
                .build();

        return item;
    }
}
