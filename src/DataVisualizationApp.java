import processing.core.PApplet;

public class DataVisualizationApp extends PApplet {
    private static DataVisualizationApp app;
    private Dataset dataset;
    private int foundAt;

    public static void main(String[] args){
        app = new DataVisualizationApp();
        app.runSketch();
    }

    public DataVisualizationApp(){
        foundAt = -1;
    }

    public void settings(){
        size(1440, 871);
    }

    public void setup(){
        dataset = new Dataset();
        fill(0);
    }

    public void draw(){
        background(255);
        displayRecords();
    }

    public void keyPressed(){
        if (key == 'e'){
            foundAt = dataset.find(0.0);
        } else if (key == 'o'){
            dataset.sort();
        }
    }

    public static DataVisualizationApp getApp(){
        return app;
    }

    private void displayRecords(){
        Record[] records = dataset.getRecords();
        text("NAME", 100, 25);
        text("MANUFACTURER", 300, 25);
        text("TYPE", 500, 25);
        text("SUGARS", 700, 25);
        text("RATING", 900, 25);
        int y = 75;
        for (int i = 0; i < records.length; i++){
            Record record = records[i];

            if (foundAt == i){
                fill(255, 0, 0);
            }
            text(record.getName(), 100, y);
            fill(0);
            text(record.getManuf(), 300, y);
            text(record.getType(), 500, y);
            text(record.getSugars(), 700, y);
            text(Double.toString(record.getRating()), 900, y);
            y += 25;
        }
    }
}
