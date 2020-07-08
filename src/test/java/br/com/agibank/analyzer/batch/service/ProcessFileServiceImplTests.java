package br.com.agibank.analyzer.batch.service;

import br.com.agibank.analyzer.batch.domain.GroupSale;
import br.com.agibank.analyzer.batch.domain.Report;
import br.com.agibank.analyzer.batch.fixture.DataFileFixture;
import br.com.agibank.analyzer.batch.service.impl.ProcessFileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ProcessFileServiceImplTests {

    @MockBean
    ProcessFileService service;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readingFileAndValidationWithSucess() {
        String message = "Invalid format, only allowed .dat file extension";
        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();
        try {
            fileReaderService.readAndValidationFile("arquivo.json");
            GroupSale.closeInstance();
        } catch (Exception e) {
            assertTrue(" Message should be " + message, e.getMessage().equals(message));
        }
    }

    @Test(expected = Exception.class)
    public void loadDataFileinReadWithException() throws Exception {

        final File tempFile = tempFolder.newFile("fileTest.txt");
        Path path = Paths.get(tempFile.toURI());
        Files.write(path, DataFileFixture.loadDataToFileTests().getBytes());

        final String s = Files.readString(path);

        Assert.assertEquals(DataFileFixture.loadDataToFileTests(), s);

        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();

        String getReturn = fileReaderService.readAndValidationFile("fileTest.txt");
        GroupSale.closeInstance();

        Assert.assertTrue(Objects.nonNull(getReturn));
        assertTrue(getReturn.equals(DataFileFixture.loadDataToFileTests()));
    }

    @Test
    public void loadDataFileinReadWithSucess() throws Exception {

        final File tempFile = tempFolder.newFile("loadDataFileinReadWithSucess.dat");
        Path path = Paths.get(tempFile.toURI());
        Files.write(path, DataFileFixture.loadDataToFileTests().getBytes());
        final String s = Files.readString(path);

        Assert.assertEquals(DataFileFixture.loadDataToFileTests(), s);

        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();

        String getReturn = fileReaderService.readAndValidationFile(path.toAbsolutePath().toString());
        GroupSale.closeInstance();

        Assert.assertTrue(Objects.nonNull(getReturn));
        assertTrue(getReturn.equals(DataFileFixture.loadDataToFileTests()));
    }

    @Test
    public void processingDataAndValidationException() throws Exception {

        final File tempFile = tempFolder.newFile("processingDataAndValidationException.dat");
        Path path = Paths.get(tempFile.toURI());
        Files.write(path, DataFileFixture.loadDataToFileWithNumberNotExistsTests().getBytes());

        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();

        try {
            String getReturn = fileReaderService.readAndValidationFile(path.toAbsolutePath().toString());

            fileReaderService.processAndValidateData(getReturn);
            GroupSale.closeInstance();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Data type of this line is not valid"));
        }
    }

    @Test
    public void processingSucess() throws Exception {

        final File tempFile = tempFolder.newFile("processingDataWithSucess.dat");
        Path path = Paths.get(tempFile.toURI());
        Files.write(path, DataFileFixture.loadDataToFileTests().getBytes());

        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();

        String getReturn = fileReaderService.readAndValidationFile(path.toAbsolutePath().toString());

        Report result = fileReaderService.processAndValidateData(getReturn);

        GroupSale.closeInstance();

        assertTrue(result.getQtdSalesMan() == 2);
        assertTrue(result.getQtdCustomers() == 2);
        assertTrue(result.getSalesMan().equals("Paulo"));
        assertTrue(result.getIdSales() == 10);

    }

    @Test
    public void processingDataWithSucessWithFourSalesMan() throws Exception {

        final File tempFile = tempFolder.newFile("processingDataWithSucessWithFourSalesMan.dat");
        Path path = Paths.get(tempFile.toURI());
        Files.write(path, DataFileFixture.loadDataToFileWithFourSalesMan().getBytes());

        ProcessFileServiceImpl fileReaderService = new ProcessFileServiceImpl();

        String getReturn = fileReaderService.readAndValidationFile(path.toAbsolutePath().toString());

        Report result = fileReaderService.processAndValidateData(getReturn);
        GroupSale.closeInstance();

        assertTrue(result.getQtdSalesMan() == 4);
        assertTrue(result.getQtdCustomers() == 2);
        assertTrue(result.getSalesMan().equals("David"));
        assertTrue(result.getIdSales() == 22);

    }

}
