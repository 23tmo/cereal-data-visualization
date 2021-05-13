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
        size(1000, 600);
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
            foundAt = dataset.find("india");
        } else if (key == 'o'){
            dataset.sort();
        }
    }

    public static DataVisualizationApp getApp(){
        return app;
    }

    private void displayRecords(){
        Record[] records = dataset.getRecords();
        text("COUNTRY", 200, 25);
        text("TOTAL INFECTIONS", 400, 25);
        text("DAILY NEW", 600, 25);
        text("TOTAL DEATHS", 800, 25);
        int y = 75;
        for (int i = 0; i < records.length; i++){
            Record record = records[i];

            if (foundAt == i){
                fill(255, 0, 0);
            }
            text(record.getCountry(), 200, y);

            fill(0);
            text(record.getDeaths(), 400, y);
            text(record.getInfections(), 600, y);
            text(record.getNewCases(), 800, y);
            y += 50;
        }
    }
}
