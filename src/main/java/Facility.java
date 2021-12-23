public class Facility {
    private final int id;
    private final String name;
    private final String description;
    private final City city;
    private final String type;

    public Facility(int id, String name, String description, City city, String type){
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.type = type;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public City getCity(){
        return city;
    }

    public String getType(){
        return type;
    }
}
