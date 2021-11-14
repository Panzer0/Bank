package Backend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class BankMoney {

    private BigDecimal modelAmount;
    private BigDecimal displayAmount;
    private final NumberFormat format;
    
    public BankMoney() {
        // Initialise amounts
        modelAmount = new BigDecimal("0");
        updateDisplayAmount();

        // Initialise currency format
        this.format = NumberFormat.getCurrencyInstance();
        this.format.setMinimumFractionDigits(1);
        this.format.setMaximumFractionDigits(2);
    }
    public BankMoney(String amount) throws  NumberFormatException{
        // Initialise amounts
        modelAmount = new BigDecimal(amount);
        updateDisplayAmount();

        // Initialise currency format
        this.format = NumberFormat.getCurrencyInstance();
        this.format.setMinimumFractionDigits(1);
        this.format.setMaximumFractionDigits(2);
    }

    public boolean isNegative() {
        return (this.modelAmount.compareTo(new BigDecimal("0")) < 0);
    }

    public BigDecimal getModelAmount() {
        return modelAmount;
    }

    public BigDecimal getDisplayAmount() {
        return displayAmount;
    }

    private void updateDisplayAmount() {
        this.displayAmount = this.modelAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void addAmount(BankMoney value) throws IllegalArgumentException {
        if(value.isNegative()) throw new IllegalArgumentException("Invalid argument - is negative");
        this.modelAmount = modelAmount.add(value.getModelAmount());
        updateDisplayAmount();
    }
    public void subtractAmount(BankMoney value) throws IllegalArgumentException {
        if(value.isNegative()) throw new IllegalArgumentException("Invalid argument - is negative");
        this.modelAmount = modelAmount.subtract(value.getModelAmount());
        updateDisplayAmount();
    }

    public int compareTo(BankMoney other) {
        return this.modelAmount.compareTo(other.modelAmount);
    }

    public void display() {
        System.out.println(this.format.format(this.displayAmount.doubleValue()));
    }

    @Override
    public String toString() {
        return this.format.format(this.displayAmount.doubleValue());
    }
    public String toStringNoCurrency() {
        return String.valueOf(this.displayAmount.doubleValue());
    }
}
