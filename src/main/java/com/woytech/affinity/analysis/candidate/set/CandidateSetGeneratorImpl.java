package com.woytech.affinity.analysis.candidate.set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.woytech.affinity.analysis.Transaction;
import com.woytech.affinity.analysis.data.structure.Item;
import com.woytech.affinity.analysis.data.structure.ItemSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CandidateSetGeneratorImpl implements CandidateSetGenerator {

    private final static Logger LOG = LoggerFactory.getLogger(CandidateSetGeneratorImpl.class);

    private final static int LEVEL_1 = 1;
    private final static int LEVEL_2 = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemSet generate1ElementSet(List<Transaction> transactions) {
        Preconditions.checkNotNull(transactions, "Transaction list can't be null");

        LOG.info("Generating one element candidate set. Number of transactions - {}", transactions.size());

        List<Item> candidateItems = transactions.stream().flatMap(transaction -> transaction.getItems().stream()).collect(
                Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().map(entry -> {
            return new Item(entry.getValue().intValue(), Lists.newArrayList(entry.getKey()));
        }).sorted((ci1, ci2) -> ci1.getValues().get(0).compareTo(ci2.getValues().get(0))).collect(Collectors.toList());

        LOG.info("Finished generating one element candidate set. Generated {} candidate set(s) size.", candidateItems.size());

        return new ItemSet(LEVEL_1, candidateItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemSet generate2ElementSet(ItemSet oneElementItemSet, List<Transaction> transactions) {
        Preconditions.checkNotNull(oneElementItemSet, "One element item set can't be null");
        Preconditions.checkNotNull(transactions, "Transaction list can't be null");

        LOG.info("Generating two elements candidate set. One element set size - {}", oneElementItemSet.getItems().size());

        ItemSet twoElementsItemSet = new ItemSet(LEVEL_2);

        int numberOfOneElementItems = oneElementItemSet.getItems().size();
        for (int prefix = 0; prefix < numberOfOneElementItems; prefix++) {
            for (int suffix = prefix + LEVEL_1; suffix < numberOfOneElementItems; suffix++) {
                List<String> values = Lists.newArrayList(oneElementItemSet.getItems().get(prefix).getValues());
                values.addAll(oneElementItemSet.getItems().get(suffix).getValues());
                Item twoElementItem = new Item(values);
                twoElementsItemSet.addItem(twoElementItem);
            }
        }

        LOG.info("Finished generating two elements candidate set. Generated {} candidate set(s).", twoElementsItemSet.getItems().size());

        return twoElementsItemSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemSet generateNElementSet(ItemSet previousLevelItemSet, int level, List<Transaction> transactions) {
        Preconditions.checkNotNull(previousLevelItemSet, "Candidate set from previous iteration can't be null");
        Preconditions.checkArgument(level > 2, "Level must be higher than 2");
        Preconditions.checkNotNull(transactions, "Transaction list can't be null");

        LOG.info("Generating {} elements candidate set. Previous element set size - {}", level, previousLevelItemSet.getItems().size());
        ItemSet newItemSet = new ItemSet(level);

        int numberOfItems = previousLevelItemSet.getItems().size();
        if (numberOfItems > 1) {
            for (int prefix = 0; prefix < numberOfItems; prefix++) {
                for (int suffix = prefix + 1; suffix < numberOfItems; suffix++) {
                    List<String> prefixList = previousLevelItemSet.getItems().get(prefix).getValues();
                    List<String> suffixList = previousLevelItemSet.getItems().get(suffix).getValues();

                    boolean commonPrefix = true;
                    for (int index = 0; index < level - 2; index++) {
                        if (!prefixList.get(index).equals(suffixList.get(index))) {
                            commonPrefix = false;
                            break;
                        }
                    }

                    if (commonPrefix) {
                        ArrayList<String> newValuesList = Lists.newArrayList(prefixList);
                        newValuesList.add(suffixList.get(suffixList.size() - 1));
                        Item item = new Item(newValuesList);

                        //prune step
                        if (checkIfSubSetsAreFrequent(item, previousLevelItemSet)) {
                            newItemSet.addItem(item);
                        }
                    }

                }
            }
        }
        LOG.info("Finished generating {} elements candidate set. Generated {} candidate set(s).", level, newItemSet.getItems().size());

        return newItemSet;
    }

    private boolean checkIfSubSetsAreFrequent(Item item, ItemSet previousFrequentSet) {
        List<List<String>> subSets = generatePreviousLevelSubSets(item);

        for (List<String> subSet : subSets) {
            boolean isSubSetFrequent = previousFrequentSet.
                    getItems().
                    stream().
                    anyMatch(currentItem -> currentItem.getValues().equals(subSet));

            if (!isSubSetFrequent) {
                LOG.trace("Some subsets of item {} are not frequent, so this item will be excluded!", item);
                return false;
            }
        }

        return true;
    }

    //TODO test this method
    private List<List<String>> generatePreviousLevelSubSets(Item item) {
        List<List<String>> subSets = new ArrayList<>();
        List<String> values = item.getValues();

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j - i >= 0 && j - i < values.size(); j++) {
                ArrayList<String> subSet = Lists.newArrayList(values);
                //remove element from current subset
                subSet.remove(j - i);

                subSets.add(subSet);

                if (i > 0) {
                    break;
                }
            }
        }
        return subSets;
    }

    public static void main(String[] args) {
        CandidateSetGeneratorImpl candidateSetGenerator = new CandidateSetGeneratorImpl();
        List<List<String>> lists = candidateSetGenerator.generatePreviousLevelSubSets(new Item(Lists.newArrayList("1", "3", "4", "6", "8", "9")));
        System.out.println(lists);
    }

}
