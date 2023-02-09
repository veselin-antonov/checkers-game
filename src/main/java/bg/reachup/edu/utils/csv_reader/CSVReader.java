package bg.reachup.edu.utils.csv_reader;

import bg.reachup.edu.utils.csv_reader.exceptions.FileReadingException;
import bg.reachup.edu.utils.csv_reader.exceptions.InputStreamReadingException;
import bg.reachup.edu.utils.csv_reader.exceptions.ReaderReadingException;

import java.io.*;
import java.util.*;

public class CSVReader implements Iterable<List<String>>, AutoCloseable {
    private final BufferedReader reader;
    private final String delimiter;

    private List<String> headerRow;
    private List<String> headerColumn;

    private List<List<String>> records;

    private boolean readingFromFile = false;
    private boolean readingFromInputStream = false;

    public CSVReader(Reader reader, String delimiter) {
        if (!(reader instanceof BufferedReader bufferedReader)) {
            this.reader = new BufferedReader(reader);
        } else {
            this.reader = bufferedReader;
        }
        this.delimiter = delimiter;
    }

    public CSVReader(Reader reader) {
        this(reader, ",");
    }

    public CSVReader(InputStream inputStream, String delimiter) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.delimiter = delimiter;
        this.readingFromInputStream = true;
    }

    public CSVReader(InputStream inputStream) {
        this(inputStream, ",");
    }

    public CSVReader(String filePath, String delimiter) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(filePath));
        this.delimiter = delimiter;
        this.readingFromFile = true;
    }

    public CSVReader(String filePath) throws FileNotFoundException {
        this(filePath, ",");
    }

    public void close() {
        if (readingFromFile) {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getRecordDelimiter() {
        return delimiter;
    }

    private List<List<String>> getAllRecords() {
        if (this.records == null) {
            List<List<String>> records = new LinkedList<>();
            List<String> lines = new LinkedList<>();
            String currentLine;
            try {
                do {
                    currentLine = reader.readLine();
                    lines.add(currentLine);
                }
                while (currentLine != null);
            } catch (IOException e) {
                close();
                if (readingFromFile) {
                    throw new FileReadingException();
                } else if (readingFromInputStream) {
                    throw new InputStreamReadingException();
                } else {
                    throw new ReaderReadingException();
                }
            }

            lines.remove(lines.size() - 1);

            lines.forEach(line -> records.add(new LinkedList<>(Arrays.asList(line.split(delimiter)))));

            this.headerRow = new LinkedList<>(records.get(0));
            headerRow.remove(0);

            this.headerColumn = records.stream().map(list -> list.get(0)).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
            headerColumn.remove(0);

            records.remove(0);
            records.forEach(list -> list.remove(0));
            this.records = records;
        }
        return this.records;
    }

    public List<String> getHeaderRow() {
        if (headerRow == null) {
            getAllRecords();
        }
        return headerRow;
    }

    public List<String> getHeaderColumn() {
        if (headerColumn == null) {
            getAllRecords();
        }
        return headerColumn;
    }

    public int getColumnsCount() {
        return getHeaderRow().size();
    }

    public int getRecordsCount() {
        return getHeaderColumn().size();
    }

    public Map<String, String> get(int i) {
        if (i < 0 || i >= records.size()) {
            throw new IndexOutOfBoundsException();
        }
        Map<String, String> recordsForColumn = new LinkedHashMap<>();
        for (int j = 0; j < records.size(); j++) {
            recordsForColumn.put(getHeaderColumn().get(j), getAllRecords().get(j).get(i));
        }
        return recordsForColumn;
    }

    public Map<String, String> get(String columnHeader) {
        return get(getHeaderRow().indexOf(columnHeader));
    }

    public List<List<String>> read(int fromRecord, int toRecord) {
        return getAllRecords().subList(fromRecord, toRecord);
    }

    @Override
    public Iterator<List<String>> iterator() {
        return getAllRecords().listIterator();
    }
}
