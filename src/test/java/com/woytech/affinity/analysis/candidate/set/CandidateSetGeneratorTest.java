package com.woytech.affinity.analysis.candidate.set;

import com.google.common.collect.Lists;
import com.woytech.affinity.analysis.data.SampleDataGenerator;
import com.woytech.affinity.analysis.data.structure.Item;
import com.woytech.affinity.analysis.data.structure.ItemSet;
import org.junit.Assert;
import org.junit.Test;

public class CandidateSetGeneratorTest {

    private CandidateSetGenerator candidateSetGenerator;

    public CandidateSetGeneratorTest() {
        candidateSetGenerator = new CandidateSetGeneratorImpl();
    }

    @Test
    public void checkIfFirstLevelCandidateSetIsGenerated() {
        //given
        //when
        ItemSet firstLevelCandidateSet = candidateSetGenerator.generate1ElementSet(SampleDataGenerator.getSampleData());

        //then
        Assert.assertEquals(5, firstLevelCandidateSet.getItems().size());
        Assert.assertEquals(new Item(6, Lists.newArrayList("I1")), firstLevelCandidateSet.getItems().get(0));
        Assert.assertEquals(new Item(7, Lists.newArrayList("I2")), firstLevelCandidateSet.getItems().get(1));
        Assert.assertEquals(new Item(6, Lists.newArrayList("I3")), firstLevelCandidateSet.getItems().get(2));
        Assert.assertEquals(new Item(2, Lists.newArrayList("I4")), firstLevelCandidateSet.getItems().get(3));
        Assert.assertEquals(new Item(2, Lists.newArrayList("I5")), firstLevelCandidateSet.getItems().get(4));
    }

    @Test
    public void checkIfSecondLevelCandidateSetIsGenerated() {
        //given
        Item item1 = new Item(6, Lists.newArrayList("I1"));
        Item item2 = new Item(7, Lists.newArrayList("I2"));
        Item item3 = new Item(6, Lists.newArrayList("I3"));
        Item item4 = new Item(2, Lists.newArrayList("I4"));
        Item item5 = new Item(2, Lists.newArrayList("I5"));
        ItemSet firstLevelFrequentSet = new ItemSet(1, Lists.newArrayList(item1, item2, item3, item4, item5));

        //when
        ItemSet secondLevelCandidateSet = candidateSetGenerator.generate2ElementSet(firstLevelFrequentSet, SampleDataGenerator.getSampleData());

        //then
        Assert.assertEquals(10, secondLevelCandidateSet.getItems().size());
        Assert.assertEquals(Lists.newArrayList("I1", "I2"), secondLevelCandidateSet.getItems().get(0).getValues());
        Assert.assertEquals(Lists.newArrayList("I1", "I3"), secondLevelCandidateSet.getItems().get(1).getValues());
        Assert.assertEquals(Lists.newArrayList("I1", "I4"), secondLevelCandidateSet.getItems().get(2).getValues());
        Assert.assertEquals(Lists.newArrayList("I1", "I5"), secondLevelCandidateSet.getItems().get(3).getValues());
        Assert.assertEquals(Lists.newArrayList("I2", "I3"), secondLevelCandidateSet.getItems().get(4).getValues());
        Assert.assertEquals(Lists.newArrayList("I2", "I4"), secondLevelCandidateSet.getItems().get(5).getValues());
        Assert.assertEquals(Lists.newArrayList("I2", "I5"), secondLevelCandidateSet.getItems().get(6).getValues());
        Assert.assertEquals(Lists.newArrayList("I3", "I4"), secondLevelCandidateSet.getItems().get(7).getValues());
        Assert.assertEquals(Lists.newArrayList("I3", "I5"), secondLevelCandidateSet.getItems().get(8).getValues());
        Assert.assertEquals(Lists.newArrayList("I4", "I5"), secondLevelCandidateSet.getItems().get(9).getValues());
    }

    @Test
    public void checkIfThirdLevelCandidateSetIsGenerated() {
        //given
        Item item1 = new Item(4, Lists.newArrayList("I1", "I2"));
        Item item2 = new Item(4, Lists.newArrayList("I1", "I3"));
        Item item3 = new Item(2, Lists.newArrayList("I1", "I5"));
        Item item4 = new Item(4, Lists.newArrayList("I2", "I3"));
        Item item5 = new Item(2, Lists.newArrayList("I2", "I4"));
        Item item6 = new Item(2, Lists.newArrayList("I2", "I5"));
        ItemSet secondLevelFrequentSet = new ItemSet(1, Lists.newArrayList(item1, item2, item3, item4, item5, item6));

        //when
        ItemSet thirdLevelCandidateSet = candidateSetGenerator.generateNElementSet(secondLevelFrequentSet, 3, SampleDataGenerator.getSampleData());

        //then
        Assert.assertEquals(2, thirdLevelCandidateSet.getItems().size());
        Assert.assertEquals(Lists.newArrayList("I1", "I2", "I3"), thirdLevelCandidateSet.getItems().get(0).getValues());
        Assert.assertEquals(Lists.newArrayList("I1", "I2", "I5"), thirdLevelCandidateSet.getItems().get(1).getValues());
    }
}
