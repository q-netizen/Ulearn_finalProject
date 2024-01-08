import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static ArrayList<Country> parseCSV() throws IOException {
        var countryList = new ArrayList<Country>();
        var firstRow = true;
        List<String[]> list = null;
        try {
            var reader = new CSVReader(new FileReader("Показатель счастья по странам 2015.csv"));
            list = reader.readAll();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        for (var data : list) {
            if (firstRow) {
                firstRow = false;
                continue;
            }
            countryList.add(new Country(data[0], data[1], Integer.parseInt(data[2]),
                    Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                    Double.parseDouble(data[5]), Double.parseDouble(data[6]),
                    Double.parseDouble(data[7]), Double.parseDouble(data[8]),
                    Double.parseDouble(data[9]), Double.parseDouble(data[10]),
                    Double.parseDouble(data[11])));
        }
        return countryList;
    }
}