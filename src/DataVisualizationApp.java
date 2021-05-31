import processing.core.PApplet;

public class DataVisualizationApp extends PApplet {
    private static DataVisualizationApp app;
    private Dataset dataset;

    public static void main(String[] args){
        app = new DataVisualizationApp();
        app.runSketch();
    }

    public DataVisualizationApp(){
    }

    public void settings(){
        size(1440, 872);
    }

    public void setup(){
        fill(0);
        dataset = new Dataset();
    }

    public void draw(){
        background(237, 196, 100);
        displayRecords();
    }

    public void mouseClicked() {
        super.mouseClicked(); // calling PApplet version of the same methods
        dataset.handleMouseClicked(mouseX, mouseY);
    }

    public static DataVisualizationApp getApp(){
        return app;
    }

    private void displayRecords(){
        int x = 150;
        int y = 120;
        int z = 60;
        Record[] records = dataset.getRecords();
        // displaying all cereals and tinting everything but the cereal found through search
        for (int i = 0; i < records.length; i++) {
            app.image(records[i].getImage(), 0 + x, 0 + y);
            if (dataset.getFoundAt() == i) { // once it finds the cereal you're looking for, make it blink
                tint(255, 255);
                app.image(records[dataset.getFoundAt()].getImage(), 0 + x, 0 + y);
                tint(31, 29, 27, 200);
            }
            y += 250;
            if (y > 700) {
                y = 120;
                x += 150;
            }
        }
        // "Cereals" title
        textSize(50);
        fill(255, 0, 0);
        text("C", width/2-120, z);
        fill(255, 145, 0);
        text("e", width/2-80, z);
        fill(255, 242, 0);
        text("r", width/2-40, z);
        fill(145, 255, 0);
        text("e", width/2, z);
        fill(0, 238, 255);
        text("a", width/2+40, z);
        fill(0, 72, 255);
        text("l", width/2+80, z);
        fill(187, 0, 255);
        text("s", width/2+120, z);
        fill(255, 0, 225);
        text("!", width/2+160, z);
        fill(0);
        textSize(17);
        text("sort by sugar (least to most from left to right)", width/2-165, z+23);
        text("or search for the best rated cereal (high rating = more nutritious)", width/2-250, z+43);

       // "Sort by sugar" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1250, 10, 150, 25);
        fill(37, 174, 219);
        textSize(15);
        text("sort by sugar",1275, 28);

        // "find best rated" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1250, 45, 150, 25);
        fill(37, 174, 219);
        textSize(15);
        text("find best rated",1275, 63);

        // "find rating closest to:" text
        fill(14, 43, 204);
        text("find rating closest to:",1250, 93);

        // "0" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1365, 105, 35, 25);
        fill(37, 174, 219);
        textSize(15);
        text("0",1378, 123);

        // "20" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1365, 145, 35, 25);
        fill(37, 174, 219);
        textSize(15);
        text("20",1373, 163);

        // "40" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1365, 185, 35, 25);
        fill(37, 174, 219);
        textSize(15);
        text("40",1373, 203);

        // "60" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1365, 225, 35, 25);
        fill(37, 174, 219);
        textSize(15);
        text("60",1373, 243);

        // "80" button
        fill(255);
        stroke(37, 174, 219);
        strokeWeight(5);
        rect(1365, 265, 35, 25);
        fill(37, 174, 219);
        textSize(15);
        text("80",1373, 283);
    }
}
