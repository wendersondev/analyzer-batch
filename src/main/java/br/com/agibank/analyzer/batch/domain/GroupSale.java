package br.com.agibank.analyzer.batch.domain;

import br.com.agibank.analyzer.batch.exceptions.FileDataException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupSale {

    private List<SalesMan> salesmen;
    private List<Customer> customers;
    private List<Sale> sales;

    private static GroupSale instance = null;

    public static GroupSale getInstance()
    {
        if (instance == null)
            instance = new GroupSale();

        return instance;
    }

    public static void closeInstance(){
        instance = null;
    }

    public GroupSale() {
        salesmen = new ArrayList<>();
        customers = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public List<SalesMan> getSalesmen() {
        return salesmen;
    }

    public void addSalesman(SalesMan salesman) {

        for (SalesMan man : salesmen) {
            if (man.equals(salesman))
                return;
        }

        salesmen.add(salesman);

        Sale sale = sales
                .stream()
                .filter((sl) ->
                        sl.getSalesman() == null && sl.getSalesmanName().equals(salesman.getName()))
                .findFirst()
                .orElse(null);

        if (sale != null) {
            sale.setSalesman(salesman);
            sale.updateTotal();
            salesman.addSaleAmount(sale.getTotal());
        }

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {

        for (Customer current : customers) {
            if (current.equals(customer))
                return;
        }

        customers.add(customer);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) throws FileDataException {
        if (sale.getSalesman() != null) {
            sale.updateTotal();
            sale.getSalesman().addSaleAmount(sale.getTotal());
            sale.setSalesmanName(sale.getSalesman().getName());
        } else if (sale.getSalesmanName() != null && !sale.getSalesmanName().isEmpty()) {

            SalesMan man = salesmen
                    .stream()
                    .filter((cur) ->
                            cur.getName().equals(sale.getSalesmanName()))
                    .findFirst()
                    .orElse(null);

            if (man != null) {
                sale.setSalesman(man);
                sale.updateTotal();
                man.addSaleAmount(sale.getTotal());
            }

        } else {
            throw new FileDataException("There is no information about salesman in this sale");
        }

        sale.updateTotal();
        sales.add(sale);
    }

    public int getCustomersSize(){
        return this.customers.size();
    }

    public int getSalesmanSize(){
        return this.salesmen.size();
    }

    public Sale getGreaterSale(){
        Sale sale = this.sales
                .stream()
                .max(Comparator.comparing(Sale::getTotalValue))
                .get();
        return sale;
    }

    public SalesMan getWorstSalesman(){

        SalesMan man = this.salesmen
                .stream()
                .sorted(Comparator.comparing(SalesMan::getSalesAmount))
                .findFirst()
                .orElse(null);

        return man;

    }

}
