package com.hologramsciences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import io.atlassian.fugue.Option;


public class ResourceLoader {
    public static final String readResourceAsString(final String resourceName) throws IOException {
        try (
                final InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            final StringBuilder inputStringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while(line != null){
                inputStringBuilder.append(line);inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
            return inputStringBuilder.toString();
        }
    }

    public static final <T> List<T> parseOptionCSV(final String resourceName, final Function<CSVRecord, Option<T>> parser) throws IOException {
        try (
                final InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName);
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {

            final List<T> result = new ArrayList<>();
            for (final CSVRecord record: CSVParser.parse(bufferedReader, CSVFormat.RFC4180)) {
                parser.apply(record).forEach(t -> result.add(t));
            }

            return result;
        }
    }
}
