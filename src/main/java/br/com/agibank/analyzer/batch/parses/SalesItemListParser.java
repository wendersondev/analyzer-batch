package br.com.agibank.analyzer.batch.parses;

import br.com.agibank.analyzer.batch.domain.SaleItem;
import br.com.agibank.analyzer.batch.exceptions.InvalidFileDataSizeException;

import java.util.ArrayList;
import java.util.List;

public class SalesItemListParser {

    private static final String SEPARATOR = ",";

    public static List<SaleItem> parse(String line) throws InvalidFileDataSizeException {

        line = line.replace("[", "").replace("]", "");

        List<SaleItem> itens = new ArrayList<>();

        String[] data = line.split(SEPARATOR);

        for (String item: data){
            itens.add(SaleItemParser.parse(item));
        }

        return itens;
    }
}
