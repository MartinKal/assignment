package invoice.splitter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static invoice.splitter.InvoicesSplitter.splitByBuyer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvoicesSplitterTest {

    @Test
    void assertCorrectSplitByBuyer(@TempDir Path outputDirectory) {
        splitByBuyer(getResourcePath(Paths.get("invoices.csv")), outputDirectory);

        List<String> expectedFileNames = List.of("Axtronics.csv", "South African Gold Mines Corp.csv", "Traksas.csv");
        String[] actualFileNames = outputDirectory.toFile().list();

        assertNotNull(actualFileNames);
        assertEquals(expectedFileNames, List.of(actualFileNames));
        expectedFileNames.forEach(fileName ->
                assertEqualFiles(getResourcePath(Paths.get("output/" + fileName)), outputDirectory.resolve(fileName)));
    }

    private static void assertEqualFiles(Path expectedFile, Path actualFile) {
        try {
            assertEquals(Files.readAllLines(expectedFile), Files.readAllLines(actualFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getResourcePath(Path relativePath) {
        try {
            return Paths.get(ClassLoader.getSystemResource("./invoice/splitter").toURI()).resolve(relativePath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}