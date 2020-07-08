package br.com.agibank.analyzer.batch.domain.enums;

import br.com.agibank.analyzer.batch.domain.GroupSale;
import br.com.agibank.analyzer.batch.parses.CustomerParser;
import br.com.agibank.analyzer.batch.parses.SaleParser;
import br.com.agibank.analyzer.batch.parses.SalesmanParser;
import br.com.agibank.analyzer.batch.utils.Constants;

import java.util.Arrays;

public enum TypeData {


    SALESMAN(Constants.TYPE_SALES_MAN) {
        @Override
        public void importText(String line) throws Exception {
            GroupSale.getInstance().addSalesman(SalesmanParser.parse(line));
        }
    },CUSTOMER(Constants.TYPE_CUSTOMER) {
        @Override
        public void importText(String line) throws Exception{
            GroupSale.getInstance().addCustomer(CustomerParser.parse(line));
        }
    },SALES(Constants.TYPE_SALES) {
        @Override
        public void importText(String line) throws Exception {
            GroupSale.getInstance().addSale(SaleParser.parse(line));
        }
    };

    private final String codeSplit;

    private TypeData(final String codeSplit) {
        this.codeSplit = codeSplit;
    }

    public static TypeData getCodeSplit(final String code) {
        return Arrays.asList(TypeData.values())
                .stream().filter(cod -> cod.codeSplit.equals(code))
                .findFirst()
                .orElse(null);
    }

    public static boolean isCodExists(final String code){
        return Arrays.asList(TypeData.values())
                .stream().filter(cod -> cod.codeSplit.equals(code))
                .findFirst()
                .isPresent();
    }

    public abstract void importText(String line) throws Exception;

}
