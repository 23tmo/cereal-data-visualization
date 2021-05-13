import processing.data.Table;
import processing.data.TableRow;

public class Dataset implements Searchable, Sortable {
    private final Record[] records;
    // test comment
    //test comment
    public Dataset() {
        DataVisualizationApp app = DataVisualizationApp.getApp();
        Table table = app.loadTable("data/tabularData.csv", "header");
        records = new Record[table.getRowCount()];
        for (int i = 0; i < records.length; i++) {
            TableRow row = table.getRow(i);
            String country = row.getString("Country");
            int infections = row.getInt("Total number of infections");
            int newCases = row.getInt("Average daily number of new cases in last 7 days");
            int deaths = row.getInt("Total number of deaths");
            records[i] = new Record(country, infections, newCases, deaths);
        }
    }

    public Record[] getRecords() {
        return records;
    }

    @Override
    public void sort() {
        SortingMethods.sort(records);
    }

    @Override
    public int find(String country) {
        return SearchingMethods.search(records, country);
    }
}
