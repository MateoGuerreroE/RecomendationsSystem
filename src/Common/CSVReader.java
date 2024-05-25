package Common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class CSVReader<T, U> {

    private final String filePath;
    private Class<T> type;

    public CSVReader(String fileName, Class<T> type) {
        this.filePath = "E:\\Programming\\JAVA\\PersonalTesting\\src\\Step1\\data\\" + fileName;
        this.type = type;
    }

    public List<Map<String, String>> rawParseCSV() {
        List<Map<String, String>> records = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> record = new HashMap<>();
                csvRecord.toMap().forEach(record::put);
                records.add(record);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
        return records;
    }

    public List<T> parseCSV() {
        List<T> records = new ArrayList<T>();
        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                T record = type.getDeclaredConstructor().newInstance();

                for (String header : csvRecord.toMap().keySet()) {
                    Field field;
                    try {
                        field = type.getDeclaredField(header);
                    } catch (NoSuchFieldException e) {
                        // If the field doesn't exist, skip it
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(record, convertValue(field.getType(), csvRecord.get(header)));
                }

                records.add(record);
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return records;
    }

    private Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == int.class || fieldType == Integer.class)
            return Integer.parseInt(value);
        else if (fieldType == double.class || fieldType == Double.class)
            return Double.parseDouble(value);
        else if (fieldType == boolean.class || fieldType == Boolean.class)
            return Boolean.parseBoolean(value);
        else
            return value;
    }



}
