import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

public class Dataset implements Searchable, Sortable {
    private final Record[] records;
    private int foundAt = -1;

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
            PImage img = app.loadImage(name + ".jpeg");
            img.resize(125 ,200);
            records[i] = new Record(img, name, manuf, type, sugars, rating);
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

    public void handleMouseClicked(int x, int y){
        // if the (x,y) lies withing the bounds of the the buttons
        // sort button
        if (x > 1250 && x < (1250 + 150) &&
                    y > 10 && y < (10 + 25)){
            sort();
        }

        // find best rated button
        else if (x > 1250 && x < (1250 + 150) &&
                y > 45 && y < (45 + 25)){
            foundAt = find(99.99);
        }

        // "0" button
        else if (x > 1365 && x < (1365 + 35) &&
                y > 105 && y < (105 + 25)){
            foundAt = find(0.0);
        }

        //"20" button
        else if (x > 1365 && x < (1365 + 35) &&
                y > 105 && y < (145 + 25)){
            foundAt = find(20);
        }

        // "40" button
        else if (x > 1365 && x < (1365 + 35) &&
                y > 185 && y < (185 + 25)){
            foundAt = find(40);
        }

        // "60" button
        else if (x > 1365 && x < (1365 + 35) &&
                y > 225 && y < (225 + 25)){
            foundAt = find(60);
        }

        // "80" button
        else if (x > 1365 && x < (1365 + 35) &&
                y > 265 && y < (265 + 25)){
            foundAt = find(80);
        }
    }

    public int getFoundAt(){
        return foundAt;
    }
}
