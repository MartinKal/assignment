package invoice.splitter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class InvoicesSplitter {

    private static final String CSV_HEADER =
            "buyer,image_name,invoice_image,invoice_due_date,invoice_number,invoice_amount,invoice_currency,invoice_status,supplier";

    public static void splitByBuyer(Path inputFile, Path outputDirectory) {
        Map<String, PrintWriter> writerByBuyer = new HashMap<>();
        try {
            Files.lines(inputFile)
                    .skip(1)
                    .forEach(invoice -> writerByBuyer.computeIfAbsent(
                            extractBuyer(invoice),
                            buyer -> prepareWriter(outputDirectory, buyer))
                            .println(invoice));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            writerByBuyer.values().forEach(PrintWriter::close);
        }
    }

    private static String extractBuyer(String row) {
        return row.split(",", 2)[0];
    }

    private static PrintWriter prepareWriter(Path directory, String buyer) {
        try {
            File file = directory.resolve(buyer + ".csv").toFile();
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.println(CSV_HEADER);

            return writer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InvoicesSplitter() {
    }
}
