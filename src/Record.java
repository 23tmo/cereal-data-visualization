public class Record {
    private String country;
    private int infections;
    private int newCases;
    private int deaths;

    public Record(String country, int infections, int newCases, int deaths){
        this.country = country;
        this.infections = infections;
        this.newCases = newCases;
        this.deaths = deaths;
    }

    public String getCountry(){
        return country;
    }

    public int getInfections() {
        return infections;
    }

    public int getNewCases() {
        return newCases;
    }

    public int getDeaths() {
        return deaths;
    }


}