package com.woytech.affinity.analysis.candidate.set;

import com.woytech.affinity.analysis.Transaction;
import com.woytech.affinity.analysis.data.structure.ItemSet;

import java.util.List;

public interface CandidateSetGenerator {

    /**
     * Generates first level (one-element) candidate set for the given transaction list.
     * <br/>
     * For instance for the following transactions:
     * <br/>
     * ID | Items
     * <br/>
     * 1 | 3 6 7
     * <br/>
     * 2 | 1 2 3 4
     *<br/><br/>
     *it will generate candidate set with number of supported transactions [1 -> 1], [2 -> 1], [3 -> 2], [4 -> 1], [6 -> 1], [7 -> 1].
     *
     * @param transactions list of transactions for which the candidate set will be generated
     * @return Itemset one element candidate set generated based on the provided transactions
     */
    ItemSet generate1ElementSet(List<Transaction> transactions);

    /**
     * Generates second level (two-elements) candidate set based on the first level item set(frequent set).
     * <br/>
     * For instance for the following transactions:
     * <br/>
     * ID | Items
     * <br/>
     * 1 | 1 2 3 4 7
     * <br/>
     * 2 | 1 2 3 4
     *<br/>
     * and first level item set: [1, 2, 3, 4]
     *<br/><br/>
     *it will generate candidate set with number of supported transactions [1 2 -> 2], [1 3 -> 2], [1 4 -> 2], [2 3 -> 2], [2 4 -> 2], [3 4 -> 2].
     *
     * @param oneElementItemSet one element item set
     * @param transactions list of transactions for which the candidate set will be generated
     * @return Itemset two-elements candidate set generated based on the provided first level item set
     */
    ItemSet generate2ElementSet(ItemSet oneElementItemSet, List<Transaction> transactions);

    /**
     * Generates candidate set on the specified level. The set is generated based on the previous level item set(frequent set).
     * <br/>
     * For instance for the following transactions:
     * <br/>
     * ID | Items
     * <br/>
     * 1 | 1 2 7
     * <br/>
     * 2 | 1 2 3 4
     * <br/>
     * and second level item set: [1 2 -> 2], [1 3 -> 2], [1 4 -> 2], [2 3 -> 2], [2 4 -> 2], [3 4 -> 2]
     *<br/><br/>
     *it will generate candidate set with number of supported transactions [1 2 3 -> 1], [1 2 4 -> 1], [2 3 4 -> 1].
     *
     * @param previousLevelItemSet previous level item set
     * @param level candidate set level
     * @param transactions list of transactions for which the candidate set will be generated
     * @return Itemset  candidate set generated based on the provided previous level item set
     */
    ItemSet generateNElementSet(ItemSet previousLevelItemSet, int level, List<Transaction> transactions);
}