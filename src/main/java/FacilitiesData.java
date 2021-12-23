import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FacilitiesData {

    private final ArrayList<Work> works = new ArrayList<>();
    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<Facility> facilities = new ArrayList<>();
    private final HashMap<String, String> cityNames = new HashMap<>();

    public void readCSV(String path){
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path, Charset.forName("windows-1251")));
            String row;
            csvReader.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            var cityID = 0;
            while ((row = csvReader.readLine()) != null) {
                String[] data = ParseData(row);
                var idFacility = 0;
                try {
                    idFacility = Integer.parseInt(data[0]);
                }catch (Exception e){
                    return;
                }
                var nameFacility = data[1];
                var descriptionFacility = data[4];
                var cityName = data[11];
                var region = data[9];
                Date startDate = null;
                if(data[18].length() != 0)
                    startDate = formatter.parse(data[18]);
                Date endDate = null;
                if(data[19].length() != 0)
                    endDate = formatter.parse(data[19]);
                var workType = data[17];
                var finance = Long.parseLong(data[20]);
                var type = data[43];
                cityID++;
                var city = new City(cityID, cityName, region);
                if(!cityNames.containsKey(cityName) || !cityNames.get(cityName).equals(city.getRegion()))
                    cities.add(city);
                    cityNames.put(city.getName(), city.getRegion());
                var facility = new Facility(idFacility, nameFacility, descriptionFacility, city, type);
                facilities.add(facility);
                works.add(new Work(facility, startDate, endDate, finance, workType));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String[] ParseData(String row){
        var data = new ArrayList<String>();
        var state = new StringBuilder();
        var flagQuot = false;
        for(var i = 0; i < row.length(); i++){
            if(row.charAt(i) == '\"') {
                flagQuot = !flagQuot;
                continue;
            }
            if(row.charAt(i) == ',' && !flagQuot) {
                data.add(state.toString());
                state = new StringBuilder();
                continue;
            }
            state.append(row.charAt(i));
        }
        return data.toArray(String[]::new);
    }

    public ArrayList<Facility> getFacilities(){
        return this.facilities;
    }

    public ArrayList<City> getCities(){
        return this.cities;
    }

    public ArrayList<Work> getWorks(){
        return works;
    }
}
