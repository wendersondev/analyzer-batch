package br.com.agibank.analyzer.batch.job.reader;

import br.com.agibank.analyzer.batch.service.ProcessFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * read files get string and next validation data and processing
 * @author Wenderson Roberto
 */
@Component
@StepScope
public class ProcessAnalyzerReader implements ItemReader<String> {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessAnalyzerReader.class);

    @Autowired
    private StepParameters stepParameters;

    @Autowired
    private ProcessFileService processFileService;

    private boolean processed;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!processed) {

            ProcessAnalyzerReader.LOG.info("ProcessAnalyzerReader - Getting Started ItemReader - processed = {}", processed);

            String read = processFileService.readAndValidationFile(stepParameters.getFilePath());

            ProcessAnalyzerReader.LOG.info("ProcessAnalyzerReader - File reading in FileWatcher ==> {}", stepParameters.getFilePath());
            processed = true;

            return read;
        }
        return null;
    }
}
