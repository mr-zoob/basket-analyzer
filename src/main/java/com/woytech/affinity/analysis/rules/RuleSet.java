package com.woytech.affinity.analysis.rules;


import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {

    private List<Rule> rules = new ArrayList<>();

    public void addRule(Rule rule) {
        Preconditions.checkNotNull(rule, "Rule can't be null");

        rules.add(rule);
    }

    public void addRules(List<Rule> rules) {
        Preconditions.checkNotNull(rules, "Rules can't be null");

        this.rules.addAll(rules);
    }

    public List<Rule> getRules() {
        return new ArrayList<>(rules);
    }

}
