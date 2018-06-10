package com.woytech.affinity.analysis.rules;

import java.util.List;

public class Rule {

    private List<String> right;
    private List<String> left;
    private double confidence;

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    public List<String> getLeft() {
        return left;
    }

    public void setLeft(List<String> left) {
        this.left = left;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "[" + left + " => " +right +", confidence = " + confidence  +"]";
    }
}
