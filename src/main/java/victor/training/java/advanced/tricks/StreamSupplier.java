package victor.training.java.advanced.tricks;

import org.jooq.lambda.Unchecked;

import java.io.File;
import java.nio.file.Files;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class StreamSupplier {
    public static void main(String[] args) {
        supplierOfStream();
    }

    private static void supplierOfStream() {
        File file = new File("test.ok.txt");
        processRecords(Unchecked.supplier(() -> Files.lines(file.toPath())));
    }

    private static void processRecords(Supplier<Stream<String>> lineSupplier) {
        try (Stream<String> lines = lineSupplier.get()) {
            System.out.println("Lines count: " + lines.count());
        }
        try (Stream<String> lines = lineSupplier.get()) {
            System.out.println("Longest line: " + lines.max(comparing(String::length)));
        }
    }
}

