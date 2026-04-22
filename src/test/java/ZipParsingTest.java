import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipParsingTest {

    private ClassLoader cl = ZipParsingTest.class.getClassLoader();

    private InputStream getFileFromZip(String fileName) throws Exception {
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("files.zip"));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().startsWith("__MACOSX")) {
                continue;
            }
            if (entry.getName().equals(fileName)) {
                return zis;
            }
        }
        throw new Exception("Файл" + fileName + " не найден в архиве");
    }

    @Test
    void csvFileParsingTest() throws Exception {
        try (InputStream is = getFileFromZip("csvTest.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(is));
            List<String[]> data = csvReader.readAll();
            Assertions.assertEquals(2, data.size());
            Assertions.assertArrayEquals(
                    new String[]{"name", " age"},
                    data.get(0)
            );
            Assertions.assertArrayEquals(
                    new String[]{"Max", " 25"},
                    data.get(1)
            );
        }
    }
    @Test
    void pdfFileParsingTest() throws Exception {
        try (InputStream is = getFileFromZip("pdfTest.pdf")) {
            PDF pdf = new PDF(is);
            Assertions.assertEquals("Hello PDF", pdf.text.trim());
        }
    }

    @Test
    void xlsxFileParsingTest() throws Exception {
        try (InputStream is = getFileFromZip("xlsxTest.xlsx")) {
            XLS xls = new XLS(is);
            String actualValue = xls.excel.getSheetAt(0).getRow(2).getCell(0).getStringCellValue();
            Assertions.assertTrue(actualValue.contains("Maksim"));
        }
    }
}
