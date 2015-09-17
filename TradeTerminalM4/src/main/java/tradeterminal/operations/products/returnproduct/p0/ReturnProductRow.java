/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.returnproduct.p0;

import java.util.ArrayList;


/**
 *
 * @author PKopychenko
 */
public class ReturnProductRow extends ArrayList {

    protected int rowIndex;
    protected int id;
    protected int productsGroupsId;
    protected String name;
    protected String description;
    protected String scod;
    protected double quantity;
    protected int measuresId;
    protected String measuresName;
    protected double actualPrice;
    protected double returnedQuantity;
    protected double outputPrice;
    protected double step;
    // Текущее значение
    protected double currentReturnedQuantity;
    protected double currentReturnedQuantityMAX;
    protected double currentReturnedSumm;
    public static int SIZE = 11;

    public ReturnProductRow(
            int id,
            int productsGroupsId,
            String name,
            String description,
            String scod,
            double quantity,
            int measuresId,
            String measuresName,
            double actualPrice,
            double returnedQuantity,
            double step) {
        super(SIZE);

        // Индексное поле
        //add(0, 0);
        for (int i = 0; i < SIZE; i++) {
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
        this.actualPrice = actualPrice;
        this.returnedQuantity = returnedQuantity;
        this.step = step;

        outputPrice = quantity * actualPrice;

        // Вычисляем максимальный предел возвращаемого значения
        currentReturnedQuantityMAX = quantity - returnedQuantity;

        calculate();
    }

    public double getCurrentReturnedQuantityMAX() {
        return currentReturnedQuantityMAX;
    }

    // Посчитать
    protected void calculate() {

        currentReturnedSumm = currentReturnedQuantity * actualPrice;

        updateView();
    }

    // Обновить вид
    protected void updateView() {
        // Обновляем значение порядкового номера записи
        set(0, rowIndex);

        // Текперь устанавливаем логическое содержимое
        set(1, name);
        set(2, description);
        set(3, scod);
        set(4, quantity);
        set(5, measuresName);
        set(6, actualPrice);
        set(7, outputPrice);
        set(8, returnedQuantity);

        set(9, currentReturnedQuantity);
        set(10, currentReturnedSumm);
    }

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

    public double getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setCurrentReturnedQuantity(double q) {
        this.currentReturnedQuantity = q;
        calculate();
    }

    public double getCurrentReturnedQuantity() {
        return currentReturnedQuantity;
    }

    public double getCurrentReturnedSumm() {
        return currentReturnedSumm;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public String getMeasuresName() {
        return measuresName;
    }

    public double getOutputPrice() {
        return outputPrice;
    }

    public double getStep() {
        return step;
    }
}    
