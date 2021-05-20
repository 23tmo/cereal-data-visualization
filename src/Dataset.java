import processing.data.Table;
import processing.data.TableRow;

public class Dataset implements Searchable, Sortable {
    private final Record[] records;

    public Dataset() {
        DataVisualizationApp app = DataVisualizationApp.getApp();
        Table table = app.loadTable("data/CerealsDataset.csv", "header");
        records = new Record[table.getRowCount()];
        for (int i = 0; i < records.length; i++) {
            TableRow row = table.getRow(i);
            String name = row.getString("Name");
            String manuf = row.getString("Manuf");
            String type = row.getString("Type");
            int sugars = row.getInt("Sugars");
            double rating = row.getDouble("Rating");
            records[i] = new Record(name, manuf, type, sugars, rating);
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
    public int find(double rating) {
        return SearchingMethods.search(records, rating);
    }
}
