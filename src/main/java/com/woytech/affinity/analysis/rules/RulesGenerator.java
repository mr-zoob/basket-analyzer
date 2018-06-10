package com.woytech.affinity.analysis.rules;

import com.woytech.affinity.analysis.data.structure.ItemSet;

import java.util.List;

public interface RulesGenerator {
    RuleSet generateRules(double minConfidence, List<ItemSet> frequentSets);
}
