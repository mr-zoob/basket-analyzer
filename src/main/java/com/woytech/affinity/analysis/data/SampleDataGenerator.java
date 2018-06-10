package com.woytech.affinity.analysis.data;

import com.google.common.collect.Lists;
import com.woytech.affinity.analysis.Transaction;

import java.util.List;

public class SampleDataGenerator {

    public static List<Transaction> getSampleData() {

        return Lists.newArrayList(
                new Transaction("1", Lists.newArrayList("I1", "I2", "I5")),
                new Transaction("2", Lists.newArrayList("I2", "I4")),
                new Transaction("3", Lists.newArrayList("I2", "I3")),
                new Transaction("4", Lists.newArrayList("I1", "I2", "I4")),
                new Transaction("5", Lists.newArrayList("I1", "I3")),
                new Transaction("6", Lists.newArrayList("I2", "I3")),
                new Transaction("7", Lists.newArrayList("I1", "I3")),
                new Transaction("8", Lists.newArrayList("I1", "I2" ,"I3", "I5")),
                new Transaction("9", Lists.newArrayList("I1", "I2", "I3"))
        );


    }

}
