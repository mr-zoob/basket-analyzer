package com.woytech.affinity.analysis;

import com.google.common.base.Preconditions;
import com.woytech.affinity.analysis.candidate.set.CandidateSetGenerator;
import com.woytech.affinity.analysis.candidate.set.CandidateSetGeneratorImpl;
import com.woytech.affinity.analysis.data.structure.ItemSet;
import com.woytech.affinity.analysis.frequent.set.FrequentSetFinder;
import com.woytech.affinity.analysis.frequent.set.FrequentSetFinderImpl;
import com.woytech.affinity.analysis.rules.RuleSet;
import com.woytech.affinity.analysis.rules.RulesGenerator;
import com.woytech.affinity.analysis.rules.RulesGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AprioriAffinityAnalysis implements AffinityAnalysis {

    private final static Logger LOG = LoggerFactory.getLogger(AprioriAffinityAnalysis.class);


    private List<Transaction> transactions;
    private double minSupport;
    private int numTransactionForMinSupport;

    private double minConfidence;


    private CandidateSetGenerator candidateSetGenerator;
    private FrequentSetFinder frequentSetFinder;

    public AprioriAffinityAnalysis(List<Transaction> transactions, double minSupport, double minConfidence) {
        this.transactions = transactions;
        this.minSupport = minSupport;
        this.minConfidence = minConfidence;

        this.numTransactionForMinSupport = new Double(Math.ceil(transactions.size() * minSupport)).intValue();

        this.candidateSetGenerator = new CandidateSetGeneratorImpl();
        this.frequentSetFinder = new FrequentSetFinderImpl(numTransactionForMinSupport, transactions);
    }

    @Override
    public void execute() {
        Preconditions.checkNotNull(transactions, "Transaction list can't be null");
        List<ItemSet> frequentItemSets = new ArrayList<>();
        int level = 1;

        ItemSet candidateSet1Element = candidateSetGenerator.generate1ElementSet(transactions);
        ItemSet frequentSet1Element = null;
        if (!candidateSet1Element.getItems().isEmpty()) {
            frequentSet1Element = frequentSetFinder.findFrequentSet(candidateSet1Element, Optional.empty());
            frequentItemSets.add(frequentSet1Element);
        }

        if (frequentSet1Element != null && !frequentSet1Element.getItems().isEmpty()) {
            level++;
            ItemSet candidateSet2Element = candidateSetGenerator.generate2ElementSet(frequentSet1Element, transactions);
            ItemSet frequentSet2Element = null;
            if (!candidateSet1Element.getItems().isEmpty()) {
                frequentSet2Element = frequentSetFinder.findFrequentSet(candidateSet2Element, Optional.of(frequentSet1Element));
            }

            ItemSet nextFrequentSet = frequentSet2Element;
            while (nextFrequentSet != null && !nextFrequentSet.getItems().isEmpty()) {
                level++;
                frequentItemSets.add(nextFrequentSet);
                ItemSet candidateSet = candidateSetGenerator.generateNElementSet(nextFrequentSet, level, transactions);
                ItemSet frequentSet = null;
                if (!candidateSet.getItems().isEmpty()) {
                    frequentSet = frequentSetFinder.findFrequentSet(candidateSet, Optional.of(nextFrequentSet));
                }
                nextFrequentSet = frequentSet;
            }

        }
        LOG.info("Finished finding frequent sets on {} level. Found {} frequent sets.", level, frequentItemSets.size());

        RulesGenerator rulesGenerator = new RulesGeneratorImpl();
        RuleSet ruleSet = rulesGenerator.generateRules(minConfidence, frequentItemSets);

        System.out.println(ruleSet.getRules());
    }

}
