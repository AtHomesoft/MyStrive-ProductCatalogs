package com.mystrive.products;

public class Product {
    public static final String NUMBER = "Number";
    public static final String NAME = "Name";
    public static final String RETAIL = "Retail";
    public static final String WHOLESALE = "Wholesale";
    public static final String SECTION = "Section";
    public static final String CATEGORY = "Category";
    public static final String DISCONTINUED = "Discontinued";
    public static final String TAXABLE = "Taxable";

    private String number;
    private String name;
    private double retail;
    private double wholesale;
    private int section;
    private String category;
    private boolean discontinued;
    private boolean taxable;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRetail() {
        return retail;
    }

    public void setRetail(double retail) {
        this.retail = retail;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public double getWholesale() {
        return wholesale;
    }

    public void setWholesale(double wholesale) {
        this.wholesale = wholesale;
    }
}
