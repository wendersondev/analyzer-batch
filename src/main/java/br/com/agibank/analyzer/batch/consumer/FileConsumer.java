package br.com.agibank.analyzer.batch.consumer;

import br.com.agibank.analyzer.batch.helpers.JobParameterBuilderHelper;
import br.com.agibank.analyzer.batch.utils.Constants;
import br.com.agibank.analyzer.batch.watchers.WatcherEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Consuming news files in fileWatch and starting new job
 *
 * @author Wenderson Roberto
 */
@Component
public class FileConsumer implements BiConsumer<WatcherEvent, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConsumer.class);

    @Autowired
    @Qualifier("jobAnalyzer")
    private Job jobAnalyzer;

    @Autowired
    @Qualifier(Constants.JOBLAUNCHER)
    private JobLauncher jobLauncherAnalyzer;

    @Value("${file-watcher.pathWriter}")
    private String pathWriter;

    @Override
    public void accept(WatcherEvent watcherEvent, String s) {

        final UUID uuid = UUID.randomUUID();
        final JobParameters jobParameters = JobParameterBuilderHelper.create(watcherEvent, uuid,System.getProperty(Constants.HOMEPATH).concat(pathWriter));

        try {
            FileConsumer.LOGGER.info("Consumer receives the FileWatcher file and calls the JOB");

            jobLauncherAnalyzer.run(jobAnalyzer, jobParameters);

        } catch (final Exception e) {
            FileConsumer.LOGGER.error("m=accept, watcherEvent={}, uuid={}", watcherEvent, uuid.toString(), e);
        }

    }
}
