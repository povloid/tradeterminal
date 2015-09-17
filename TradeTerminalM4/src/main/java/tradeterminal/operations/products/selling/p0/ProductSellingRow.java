/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tradeterminal.operations.products.selling.p0;

/**
 *
 * @author PKopychenko
 */
public final class ProductSellingRow extends BaseProductRow {

    public static final int SIZE = 9;
    public static final int HAVE_NO_DISCOUNT = 0;
    public static final int SPEC_PRICE_DISCOUNT = 1;
    public static final int PERCENT_DISCOUNT = 2;
    protected int sSpriceType = HAVE_NO_DISCOUNT;
    protected double currSpecPrice;
    protected double currPercentDiscount;
    protected String sPriceDescription = "скидок нет";
    protected double priceForUnit;
    protected double outputPrice;

    public ProductSellingRow(int id, int productsGroupsId, String name, String description, String scod, double quantity, int measuresId, String measuresName, double listPrice, double specPrice, double percentDiscount, double step, boolean mType) {
        super(SIZE, id, productsGroupsId, name, description, scod, quantity, measuresId, measuresName, listPrice, specPrice, percentDiscount, step, mType);

        if (specPrice > 0 && percentDiscount == 0) {
            sPriceDescription = "Возможна скидка по специальной цене";
            sSpriceType = SPEC_PRICE_DISCOUNT;
            currSpecPrice = listPrice;
        } else if (specPrice == 0 && percentDiscount > 0) {
            sPriceDescription = "Возможна процентная скидка";
            sSpriceType = PERCENT_DISCOUNT;
            currPercentDiscount = 0;
        }

        calculate();
    }

    public int getSpriceType() {
        return sSpriceType;
    }

    // Строчные вычисления
    @Override
    protected void calculate() {

        switch (sSpriceType) {
            case SPEC_PRICE_DISCOUNT:
                priceForUnit = currSpecPrice;
                break;
            case PERCENT_DISCOUNT:
                priceForUnit = listPrice - listPrice * 0.01d * currPercentDiscount;
                break;
            case HAVE_NO_DISCOUNT:
            default:
                priceForUnit = listPrice;
        }
        outputPrice = quantity * priceForUnit;

        // Теперь обновляем представление
        updateView();
    }

    public double getCurrPercentDiscount() {
        return currPercentDiscount;
    }

    public void setCurrPercentDiscount(double currPercentDiscount) {
        this.currPercentDiscount = currPercentDiscount;
        calculate();
    }

    public double getCurrSpecPrice() {
        return currSpecPrice;
    }

    public void setCurrSpecPrice(double currSpecPrice) {
        this.currSpecPrice = currSpecPrice;
        calculate();
    }

    @Override
    public double getOutputPrice() {
        return outputPrice;
    }

    @Override
    protected void updateView() {
        // Обновляем значение порядкового номера записи
        set(0, rowIndex);

        // Текперь устанавливаем логическое содержимое
        set(1, name);
        set(2, description);
        set(3, scod);
        set(4, quantity);
        set(5, measuresName);
        set(6, priceForUnit);
        set(7, sPriceDescription);
        set(8, outputPrice);

    }

    public double getPriceForUnit() {
        return priceForUnit;
    }
}
