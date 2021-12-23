public class City {
    private final int id;
    private final String name;
    private final String region;

    public City(int id, String name, String region){
        this.id = id;
        this.region = region;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getRegion(){
        return this.region;
    }
}
