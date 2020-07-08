package br.com.agibank.analyzer.batch.service.impl;

import br.com.agibank.analyzer.batch.domain.GroupSale;
import br.com.agibank.analyzer.batch.domain.Report;
import br.com.agibank.analyzer.batch.domain.builder.ReportBuilder;
import br.com.agibank.analyzer.batch.domain.enums.TypeData;
import br.com.agibank.analyzer.batch.service.ProcessFileService;
import br.com.agibank.analyzer.batch.utils.Constants;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


/**
 * Service to processing file with data and validations
 *
 * @author Wenderson Roberto
 */
@Service
public class ProcessFileServiceImpl implements ProcessFileService {

    @Override
    public String readAndValidationFile(String filePath) throws Exception {
        validateExtension(filePath);

        final Path pathCreated = Paths.get(filePath);
        return Files.readString(pathCreated);
    }

    @Override
    public Report processAndValidateData(String readFile) {
        Stream<String> lines = readFile.lines();

        lines.forEach(line-> {
            String[] parts = line.split(Constants.DELIMITER);
            try {
                validateCodeSplitFile(parts[0]);
                TypeData.getCodeSplit(parts[0]).importText(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        final GroupSale instance = GroupSale.getInstance();
        return new ReportBuilder()
                .qtdCustomers(instance.getCustomers().size())
                .qtdSalesMan(instance.getSalesmen().size())
                .idSales(instance.getGreaterSale().getId())
                .salesMan(instance.getWorstSalesman() != null ? instance.getWorstSalesman().getName() : "N/A")
                .build();
    }

    @Override
    public void writeAndValidationDataFile(List<? extends Report> report,String pathWriter,String fileName) throws IOException {

        String dataSave =
                report.stream()
                        .map(c-> {
                            return String.format("Número de clientes no arquivo de entrada : %s ",c.getQtdCustomers())
                                    .concat("\n")
                                    .concat(String.format("Número de Vendedores no arquivo de entrada : %s ", c.getQtdSalesMan()))
                                    .concat("\n")
                                    .concat(String.format("Id da venda mais cara : %s", c.getIdSales()))
                                    .concat("\n")
                                    .concat(String.format("Pior Vendedor : %s ", c.getSalesMan()));
                        })
                        .findFirst()
                        .get();


        Path path = Paths.get(pathWriter.concat(fileName).replace(".dat",".done.dat"));
        Files.write(path, dataSave.getBytes());
        GroupSale.closeInstance();


    }

    private void validateCodeSplitFile(String codeFile) throws Exception {
        if(!TypeData.isCodExists(codeFile)){
            throw new Exception("Data type of this line is not valid");
        }
    }

    private void validateExtension(String fileName) throws Exception {

        if(!(fileName.toLowerCase().endsWith(Constants.EXTENSION_ALLOWED)))
            throw new Exception("Invalid format, only allowed " + Constants.EXTENSION_ALLOWED + " file extension");

    }
}
