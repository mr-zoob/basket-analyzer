package com.woytech.affinity.analysis.rules;

import com.google.common.collect.Lists;
import com.woytech.affinity.analysis.data.structure.Item;
import com.woytech.affinity.analysis.data.structure.ItemSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RulesGeneratorImpl implements RulesGenerator {


    @Override
    public RuleSet generateRules(double minConfidence, List<ItemSet> frequentSets) {
        RuleSet ruleSet = new RuleSet();
        Map<List<String>, Integer> itemsMap = frequentSets.stream().flatMap(itemSet -> itemSet.getItems().stream()).collect(Collectors.toMap(Item::getValues, Item::getNumberOfTransactions));

        for (ItemSet itemSet : frequentSets) {
            for (Item item : itemSet.getItems()) {
                List<List<String>> subsets = getAllSubsets(item.getValues());
                List<Rule> itemRules = generateSubsetRules(item, subsets, itemsMap, minConfidence);
                ruleSet.addRules(itemRules);
            }
        }

        return ruleSet;
    }

    private List<Rule> generateSubsetRules(Item currentItem, List<List<String>> subsets, Map<List<String>, Integer> itemsMap, double minConfidence) {
        List<Rule> rules = new ArrayList<>();

        subsets.forEach(subset -> {
            Rule rule = new Rule();
            rule.setLeft(subset);
            List<String> rightSide = Lists.newArrayList(currentItem.getValues());
            rightSide.removeAll(subset);
            rule.setRight(rightSide);

            rule.setConfidence(calculateConfidence(currentItem.getNumberOfTransactions(), rightSide, itemsMap));

            if (rule.getConfidence() >= minConfidence) {
                rules.add(rule);
            }
        });

        return rules;
    }

    private double calculateConfidence(int currentItemSupportCounter, List<String> rightSideSet, Map<List<String>, Integer> itemsMap) {
        Integer rightSideSetSupportCounter = itemsMap.get(rightSideSet);

        if (rightSideSetSupportCounter != null && rightSideSetSupportCounter != 0) {
            double confidence = ((double)currentItemSupportCounter / (double)rightSideSetSupportCounter);
            return confidence;
        }

        return 0;
    }


    public static List<List<String>> getAllSubsets(List<String> set) {
        int n = set.size();

        List<List<String>> result = new ArrayList<>();
        // Run a loop for printing all 2^n
        // subsets one by obe
        for (int i = 0; i < (1 << n); i++) {
            List<String> subSet = new ArrayList<>();

            // Print current subset
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    subSet.add(set.get(j));
                }
            }
            result.add(subSet);
        }


        return result;
    }

    // not generate sub sets from single element
    //remove empty set
    //remove all the items set

}