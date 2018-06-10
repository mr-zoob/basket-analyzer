package com.woytech.affinity.analysis.frequent.set;

import com.woytech.affinity.analysis.data.structure.ItemSet;

import java.util.Optional;

public interface FrequentSetFinder {

    ItemSet findFrequentSet(ItemSet candidateSet, Optional<ItemSet> previousFrequentSet);
}
