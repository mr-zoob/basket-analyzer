package com.woytech.affinity.analysis.data.structure;

import com.woytech.affinity.analysis.Transaction;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.List;

public class Item {

    public final static int NUM_OF_TRANSACTIONS_NOT_SET = -1;

    private int numberOfTransactions;
    private List<String> values;

    public Item( List<String> values) {
        this.values = values;
        this.numberOfTransactions = NUM_OF_TRANSACTIONS_NOT_SET;
    }

    public Item(int numberOfTransactions, List<String> values) {
        this.numberOfTransactions = numberOfTransactions;
        this.values = values;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public void calculateNumOfSupportedTransactions(List<Transaction> transactions) {
        int numberOfSupportedTransactions = 0;

        for (Transaction transaction : transactions) {
            List<String> transactionItems = transaction.getItems();
            boolean valuesInTransaction = true;

            for (String item : values) {
                if (!transactionItems.contains(item)) {
                    valuesInTransaction = false;
                    break;
                }
            }
            if (valuesInTransaction) {
                numberOfSupportedTransactions++;
            }
        }

        this.numberOfTransactions = numberOfSupportedTransactions;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }



    @Override
    public String toString() {
        return "Item{" +
                "numberOfTransactions=" + numberOfTransactions +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Item item = (Item) obj;
        return new EqualsBuilder()
                .append(numberOfTransactions, item.numberOfTransactions)
                .append(values, item.values)
                .isEquals();
    }

}
