package br.com.agibank.analyzer.batch.job.processors;

import br.com.agibank.analyzer.batch.domain.Report;
import br.com.agibank.analyzer.batch.domain.GroupSale;
import br.com.agibank.analyzer.batch.domain.builder.ReportBuilder;
import br.com.agibank.analyzer.batch.domain.enums.TypeData;
import br.com.agibank.analyzer.batch.job.reader.StepParameters;
import br.com.agibank.analyzer.batch.service.ProcessFileService;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Processing file mapper text in object of type Report
 * @author Wenderson Roberto
 */
@Component
public class ProcessAnalyzerProcessor implements ItemProcessor<String, Report> {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessAnalyzerProcessor.class);

    @Autowired
    private StepParameters stepParameters;

    @Autowired
    private ProcessFileService processFileService;

    @Override
    public Report process(String readFile) throws Exception,IOException {

        ProcessAnalyzerProcessor.LOG.info("ProcessAnalyzerProcessor - Getting Started ItemProcessor...");

        Report report = processFileService.processAndValidateData(readFile);

        ProcessAnalyzerProcessor.LOG.info("ProcessAnalyzerProcessor - Finishing ItemProcessor with {} read");

        return report;
    }
}
