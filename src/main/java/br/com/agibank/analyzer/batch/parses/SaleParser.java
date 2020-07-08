package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.Sale;
import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.domain.builder.SaleBuilder;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;
import br.com.agibank.analyzer.batch.utils.Constants;

import java.util.List;

public class SaleParser {

    public static Sale parse(String line) throws Exception {

        if(!line.startsWith(Constants.TYPE_SALES))
            throw new Exception("The line must start with " + Constants.TYPE_SALES);

        String[] data = line.split(Constants.DELIMITER);

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Sale data size must be 4");
        }

        List<SaleItem> itens = SalesItemListParser.parse(data[2]);

        Sale sale = new SaleBuilder()
                .id(new Integer(data[1]))
                .items(itens)
                .salesmanName(data[3])
                .build();

        sale.updateTotal();
        return sale;
    }

}
