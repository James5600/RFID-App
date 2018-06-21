package pwr.rfid.dao;

import pwr.rfid.model.Person;
import pwr.rfid.model.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RfidDAO {

    private static final String user = "nex";
    private static final String pass = "nex";
    private static final String database = "NanoRFID";
    private static final String server = "localhost\\sqlexpress";
    private static final int port = 0;
    private Connection connection;

    private static final String jdbcUrl = "jdbc:sqlserver://" + server + ":" + port + ";user=" + user + ";password=" + pass + ";databaseName=" + database + "";

    public RfidDAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connection = DriverManager.getConnection(jdbcUrl);
            System.out.println("Connected to DB");
        } catch (SQLException e) {
            System.out.println("Could not establish connection");
        } catch (ClassNotFoundException e) {
            System.out.println("No driver found");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void savePerson(Person person) { //CREATE
        final String sql = "INSERT INTO Person(name, surname, date_of_birth, sex, PESEL, blood_type, description) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setString(3, person.getDate_of_birth());
            preparedStatement.setString(4, person.getSex());
            preparedStatement.setString(5, person.getPesel());
            preparedStatement.setString(6, person.getBlood_type());
            preparedStatement.setString(7, person.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not save record");
            e.printStackTrace();
        }
    }

    public Person readPerson(String tagInfo) {
        final String sql = "SELECT id_person, name, surname, date_of_birth, sex, PESEL, blood_type, description FROM Person WHERE id_person = (SELECT id_person FROM RfidOutput WHERE tag = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tagInfo);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                Person person = new Person();
                person.setId_person(result.getInt("id_person"));
                person.setName(result.getString("name"));
                person.setSurname(result.getString("surname"));
                person.setDate_of_birth(result.getString("date_of_birth"));
                person.setSex(result.getString("sex"));
                person.setBlood_type(result.getString("blood_type"));
                person.setPesel(result.getString("PESEL"));
                person.setDescription(result.getString("description"));
                return person;
            }
        } catch (SQLException e) {
            System.out.println("Could not get person");
        }
        return null;
    }

    public Integer readPersonId(String pesel) {
        final String sql = "SELECT id_person FROM Person WHERE pesel = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pesel);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("id_person");
            }
        } catch (SQLException e) {
            System.out.println("Could not get person");
        }
        return null;
    }

    public void updatePerson(Person person) {
        final String sql = "UPDATE Person SET name=?, surname=?, date_of_birth=?, sex=?, PESEL=?, blood_type=?, description=? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setString(3, person.getDate_of_birth());
            preparedStatement.setString(4, person.getSex());
            preparedStatement.setString(5, person.getPesel());
            preparedStatement.setString(6, person.getBlood_type());
            preparedStatement.setString(7, person.getDescription());
            preparedStatement.setInt(8, person.getId_person());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update record");
        }
    }

    public void deletePerson(int id) {
        final String sql = "DELETE FROM Person WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete row");
        }
    }

    public void saveTag(Tag tag, Person person) { //CREATE
        final String sql = "INSERT INTO RfidOutput(tag, date, id_person) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tag.getTag());
            preparedStatement.setString(2, tag.getDate());
            preparedStatement.setInt(3, tag.getId_person());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not save record");
            e.printStackTrace();
        }
    }

    public Tag readTag(int id) {
        final String sql = "SELECT id_read, tag, date, id_person FROM RfidOutput WHERE id_read = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                Tag tag = new Tag();
                tag.setTag(result.getString("tag"));
                tag.setDate(result.getString("date"));
                tag.setId_person(result.getInt("id_person"));
                return tag;
            }
        } catch (SQLException e) {
            System.out.println("Could not get employee");
        }
        return null;
    }

    public List<String> readAllTags() {
        final String sql = "SELECT tag FROM RfidOutput";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            List<String> listOfTags = new ArrayList<>();
                while (result.next()) {
                    listOfTags.add(result.getString("tag"));
                }
            return listOfTags;
        } catch (SQLException e) {
            System.out.println("Could not get employee");
        }
        return null;
    }

    public void updateTag(Tag tag) {
        final String sql = "UPDATE RfidOutput SET tag=?, date=?, id_person=? WHERE id_read = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tag.getTag());
            preparedStatement.setString(2, tag.getDate());
            preparedStatement.setInt(3, tag.getId_person());
            preparedStatement.setInt(4, tag.getId_read());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update record");
        }
    }

    public void deleteTag(int id_tag) {
        final String sql = "DELETE FROM RfidOutput WHERE id_tag = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete row");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
