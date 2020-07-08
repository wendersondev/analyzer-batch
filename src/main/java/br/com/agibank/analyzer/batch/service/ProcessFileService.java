package br.com.agibank.analyzer.batch.service;

import br.com.agibank.analyzer.batch.domain.Report;

import java.io.IOException;
import java.util.List;

/**
 * Interface to for implemented in service
 *
 * @author Wenderson Roberto
 */
public interface ProcessFileService {

    public String readAndValidationFile(String filePath) throws Exception;

    public Report processAndValidateData(String readFile);

    public void writeAndValidationDataFile(List<? extends Report> report,String pathWriter,String fileName) throws IOException;

}
