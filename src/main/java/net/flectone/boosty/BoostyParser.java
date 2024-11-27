package net.flectone.boosty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BoostyParser {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {

            System.out.println();
            System.out.println("To use, you need to enter arguments:");
            System.out.println("1 - input path of file.csv");
            System.out.println("2 - output path");
            System.out.println("3 - filter path");
            System.out.println();
            System.out.println("Example: java -jar BoostyParser-1.0.0.jar \"input.csv\" \"output.txt\" \"filter.txt\"");

            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String filterPath = args[2];

        process(inputPath, outputPath, filterPath);
    }

    public static void process(String inputPath, String outputPath, String filterPath) throws IOException {
        String filter = Files.readString(Paths.get(filterPath));

        List<String> subscribers = Files.readAllLines(Paths.get(inputPath))
                .stream()
                .skip(1)
                .map(Subscriber::parse)
                .filter(Objects::nonNull)
                .filter(subscriber -> {

                    if (!subscriber.levelName().equalsIgnoreCase(filter)) return false;

                    if (subscriber.endDate() != null) {
                        return !subscriber.endDate().before(new Date());
                    }

                    return true;
                })
                .sorted(Comparator.comparing(Subscriber::startDate))
                .map(Subscriber::name)
                .toList();

        Files.write(Paths.get(outputPath), subscribers);
        System.out.println("Saved in " + outputPath);
    }
}