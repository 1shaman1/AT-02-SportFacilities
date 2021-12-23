import org.sqlite.JDBC;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBWorker {

    private static final String CON_STR = "jdbc:sqlite:facilities";

    private static DBWorker instance = null;

    public static synchronized DBWorker getInstance() throws SQLException {
        if (instance == null)
            instance = new DBWorker();
        return instance;
    }


    private Connection connection;

    private DBWorker() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public BigDecimal getMidFinanceFromYear() {

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery
                    ("SELECT AVG(financing) FROM works WHERE startDate BETWEEN '2012-01-01' AND '2012-12-31'");
            resultSet.next();
            return resultSet.getBigDecimal(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMaxFinancingFacility(){
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery
                    ("SELECT facilities.name, works.financing FROM facilities LEFT JOIN works ON facilities.ID = works.facilityID\n" +
                            "WHERE facilities.type = 'многофункциональный спортивный комплекс' OR facilities.type = 'стадион'\n" +
                            "ORDER BY works.financing DESC LIMIT 1");
            resultSet.next();
            return String.format("Название постройки: %s \nФинансирование: %s",
                            resultSet.getString(1), resultSet.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Long> getFinanceByDate(){
        var result = new HashMap<String, Long>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery
                    ("SELECT  strftime('%Y', endDate) as year, SUM(financing) FROM works WHERE work_type = 'строительство'\n" +
                            "GROUP BY strftime('%Y', endDate);");
            while (resultSet.next()){
                if(resultSet.getString(1) == null)
                    continue;
                result.put(resultSet.getString(1), resultSet.getLong(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void removeAllData() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute
                    ("DELETE FROM works");
            statement.execute
                    ("DELETE FROM facilities");
            statement.execute
                    ("DELETE FROM cities");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setData(ArrayList<City> cities, ArrayList<Facility> facilities, ArrayList<Work> works){
        setIntoCities(cities);
        setIntoFacilities(facilities);
        setIntoWorks(works);
    }

    private void  setIntoCities(ArrayList<City> cities){
        for(var city : cities){
            try (PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO cities(ID, cityName, region) " +
                            "VALUES(?, ?, ?)")) {
                statement.setObject(1, city.getId());
                statement.setObject(2, city.getName());
                statement.setObject(3, city.getRegion());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void  setIntoFacilities(ArrayList<Facility> facilities){
        for(var facility : facilities){

            try (PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO facilities(ID, name, description, type, city) " +
                            "VALUES(?, ?, ?, ?, ?)")) {
                statement.setObject(1, facility.getId());
                statement.setObject(2, facility.getName());
                statement.setObject(3, facility.getDescription());
                statement.setObject(4, facility.getType());
                statement.setObject(5, facility.getCity().getId());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setIntoWorks(ArrayList<Work> works){
        for(var work : works){
            try (PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO works(facilityID, startDate, endDate, financing, work_type) " +
                            "VALUES(?, ?, ?, ?, ?)")) {
                statement.setObject(1, work.getFacility().getId());
                if(work.getStartDate() != null)
                    statement.setObject(2, formatDate(work.getStartDate()));
                if(work.getEndDate() != null)
                    statement.setObject(3, formatDate(work.getEndDate()));
                statement.setObject(4, work.getFinancing());
                statement.setObject(5, work.getWorkType());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatDate(Date date){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
