/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0;


import java.util.ArrayList;

/**
 *
 * @author PKopychenko
 */
public abstract class BaseProductRow extends ArrayList {

    protected int rowIndex;
    protected int id;
    protected int productsGroupsId;
    protected String name;
    protected String description;
    protected String scod;
    protected double quantity;
    protected int measuresId;
    protected String measuresName;
    protected double listPrice;
    protected double specPrice;
    protected double percentDiscount;
    protected double step;
    protected boolean mType;

    public BaseProductRow(
            int size,
            int id,
            int productsGroupsId,
            String name,
            String description,
            String scod,
            double quantity,
            int measuresId,
            String measuresName,
            double listPrice,
            double specPrice,
            double percentDiscount,
            double step,
            boolean mType) {
        super(size);

        // Индексное поле
        //add(0, 0);
        for (int i = 0; i < size; i++) {
            add(i, null);
        }

        // Инициализируем поля
        this.id = id;
        this.productsGroupsId = productsGroupsId;
        this.name = name;
        this.description = description;
        this.scod = scod;
        this.quantity = quantity;
        this.measuresId = measuresId;
        this.measuresName = measuresName;
        this.listPrice = listPrice;
        this.specPrice = specPrice;
        this.percentDiscount = percentDiscount;
        this.step = step;
        this.mType = mType;

        calculate();
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public double getSpecPrice() {
        return specPrice;
    }

    protected abstract void updateView();

    protected abstract void calculate();

    public abstract double getOutputPrice();

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        set(0, rowIndex);
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public double getListPrice() {
        return listPrice;
    }

    public int getMeasuresId() {
        return measuresId;
    }

    public String getMeasuresName() {
        return measuresName;
    }

    public String getName() {
        return name;
    }

    public int getProductsGroupsId() {
        return productsGroupsId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getScod() {
        return scod;
    }

    // Общим параметром для всех видов товарных операция является количество товара
    public void setQuantity(double quantity) {
        this.quantity = quantity;
        calculate();
    }

    public double getStep() {
        return step;
    }

    public boolean ismType() {
        return mType;
    }
 
}    
