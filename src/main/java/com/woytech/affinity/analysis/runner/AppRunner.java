package com.woytech.affinity.analysis.runner;

import com.woytech.affinity.analysis.AprioriAffinityAnalysis;
import com.woytech.affinity.analysis.data.SampleDataGenerator;

public class AppRunner {

    public static void main(String[] args) {
        AprioriAffinityAnalysis aprioriAffinityAnalysis = new AprioriAffinityAnalysis(SampleDataGenerator.getSampleData(), 0.22, 0.7);
        aprioriAffinityAnalysis.execute();
    }

}
