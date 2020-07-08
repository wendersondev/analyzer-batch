package br.com.agibank.analyzer.batch.config.job;

import br.com.agibank.analyzer.batch.domain.Report;
import br.com.agibank.analyzer.batch.job.processors.ProcessAnalyzerProcessor;
import br.com.agibank.analyzer.batch.job.reader.ProcessAnalyzerReader;
import br.com.agibank.analyzer.batch.job.step.ProcessAnalyzerJobListener;
import br.com.agibank.analyzer.batch.job.step.ProcessAnalyzerStepListener;
import br.com.agibank.analyzer.batch.job.writer.ProcessAnalyzerWriter;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Config jobs and steps to function spring batch
 * @author Wenderson Roberto
 */
@Configuration
public class ProcessAnalyzerJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ProcessAnalyzerJobListener processAnalyzerJobListener;

    @Autowired
    private ProcessAnalyzerStepListener processAnalyzerStepListener;

    @Value("${file-watcher.chunk.size}")
    private int chunkSize;

    @Bean
    @Qualifier(Constants.JOBLAUNCHER)
    public JobLauncher jobLauncherAnalyzer(final JobRepository jobRepository) throws Exception {
        final SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return simpleJobLauncher;
    }

    @Bean
    @StepScope
    public ProcessAnalyzerReader createAnalyzerFileItemReader() {
        return new ProcessAnalyzerReader();
    }

    @Bean
    public ProcessAnalyzerProcessor createAnalyzerFileItemProcessor() {
        return new ProcessAnalyzerProcessor();
    }

    @Bean
    public ProcessAnalyzerWriter createAnalyzerFileItemWriter() {
        return new ProcessAnalyzerWriter();
    }

    @Bean
    @Qualifier("jobAnalyzer")
    public Job jobAnalyzer() throws Exception {
        return jobBuilderFactory.get("stepJobAnalyzer")
                .incrementer(new RunIdIncrementer())
                .listener(processAnalyzerJobListener)
                .flow(AnalyzerFileStep())
                .end()
                .build();
    }

    @Bean
    public Step AnalyzerFileStep() {
        return stepBuilderFactory.get("AnalyzerFileStep")
                .<String, Report>chunk(chunkSize)
                .reader(createAnalyzerFileItemReader())
                .processor(createAnalyzerFileItemProcessor())
                .writer(createAnalyzerFileItemWriter())
                .listener(processAnalyzerStepListener)
                .build();
    }


}

