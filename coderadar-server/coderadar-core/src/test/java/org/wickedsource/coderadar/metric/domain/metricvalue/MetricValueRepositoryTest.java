package org.wickedsource.coderadar.metric.domain.metricvalue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.wickedsource.coderadar.testframework.template.IntegrationTestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MetricValueRepositoryTest extends IntegrationTestTemplate{

    @Autowired
    private MetricValueRepository repository;

    @Test
    public void testFileCount() {
        List<String> metrics = Arrays.asList("checkstyle:com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck","checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck");

        System.out.println(repository.findValuesAggregatedByCommitAndMetric(1L, 2, metrics));
        System.out.println(repository.countFilesPerCommitAndMetric(1L, 2, metrics));
    }
}