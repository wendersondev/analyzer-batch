package br.com.agibank.analyzer.batch.job.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProcessAnalyzerStepListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessAnalyzerStepListener.class);

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        ProcessAnalyzerStepListener.LOG.info("ProcessAnalyzerStep - Step started: {}", jobExecution);
        super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        ProcessAnalyzerStepListener.LOG.info("ProcessAnalyzerStep - Step ended: {}", jobExecution);
        super.afterJob(jobExecution);
    }

}
