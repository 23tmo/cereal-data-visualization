public class Record {
    private String name;
    private String manuf;
    private String type;
    private int sugars;
    private double rating;

    /**
     * A record is a cereal has the following traits:
     * @param name name of the cereal (ie Fruity Pebbles)
     * @param manuf what brand made the cereal (P = post cereals)
     * @param type if the cereal is hot or cold (C = cold)
     * @param sugars amount of sugars in grams per serving (12g)
     * @param rating rating from the dataset out of 100 for healthiness (28.025765)
     */
    public Record(String name, String manuf, String type, int sugars, double rating){
        this.name = name;
        this.manuf = manuf;
        this.type = type;
        this.sugars = sugars;
        this.rating = rating;
    }

    public String getName(){
        return name;
    }

    public String getManuf() {
        return manuf;
    }

    public String getType() {
        return type;
    }


    public int getSugars() {
        return sugars;
    }

    public double getRating() {
        return rating;
    }
}