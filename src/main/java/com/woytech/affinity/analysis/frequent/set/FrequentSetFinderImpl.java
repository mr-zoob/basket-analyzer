package com.woytech.affinity.analysis.frequent.set;

import com.google.common.collect.Lists;
import com.woytech.affinity.analysis.data.structure.Item;
import com.woytech.affinity.analysis.data.structure.ItemSet;
import com.woytech.affinity.analysis.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FrequentSetFinderImpl implements FrequentSetFinder{

    private final static Logger LOG = LoggerFactory.getLogger(FrequentSetFinderImpl.class);


    private int numTransactionsForMinSupport;
    private List<Transaction> transactions;

    public FrequentSetFinderImpl(int numTransactionsForMinSupport, List<Transaction> transactions) {
        this.numTransactionsForMinSupport = numTransactionsForMinSupport;
        this.transactions = transactions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemSet findFrequentSet(ItemSet candidateSet, Optional<ItemSet> previousFrequentSet) {
        LOG.info("Finding frequent set for candidate set on the {} level", candidateSet.getLevel());
        LOG.trace("Candidate set: {}", candidateSet);

        //remove all the candidates with min support less then assumed value
        candidateSet.getItems().removeIf(item -> {
            item.calculateNumOfSupportedTransactions(transactions);
            return item.getNumberOfTransactions() < numTransactionsForMinSupport;
        });

        LOG.trace("Found frequent set: {}", candidateSet);
        return candidateSet;
    }
}
