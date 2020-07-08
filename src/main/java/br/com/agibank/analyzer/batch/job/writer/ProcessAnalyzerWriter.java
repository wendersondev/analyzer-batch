package br.com.agibank.analyzer.batch.job.writer;

import br.com.agibank.analyzer.batch.domain.Report;
import br.com.agibank.analyzer.batch.job.reader.StepParameters;
import br.com.agibank.analyzer.batch.service.ProcessFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * writer file with data analyzers
 *
 * @author Wenderson Roberto
 */
@StepScope
@Component
public class ProcessAnalyzerWriter implements ItemWriter<Report> {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessAnalyzerWriter.class);

    @Autowired
    private StepParameters stepParameters;

    @Autowired
    private ProcessFileService processFileService;

    @Override
    public void write(List<? extends Report> items) throws Exception {

        ProcessAnalyzerWriter.LOG.info("ProcessAnalyzerWriter - Getting Started ItemWriter");

        processFileService.writeAndValidationDataFile(items,stepParameters.getPathWriter(),stepParameters.getFileName());

        ProcessAnalyzerWriter.LOG.info("ProcessAnalyzerWriter - Finishing ItemWriter");

    }

}
