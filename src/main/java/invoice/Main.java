package invoice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static invoice.splitter.InvoicesSplitter.splitByBuyer;

class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect usage. Usage: <input_file> <output_directory>");
        }

        Path inputFile = Paths.get(args[0]);
        Path outputDirectory = Paths.get(args[1]);
        if (!Files.exists(inputFile)) {
            System.out.format("Input file '%s' not found.%n", inputFile.getFileName());
            System.exit(1);
        }

        if (!Files.exists(outputDirectory)) {
            System.out.format("Output directory '%s' not found.%n", outputDirectory.getFileName());
            System.exit(1);
        }

        splitByBuyer(inputFile, outputDirectory);
    }
}
