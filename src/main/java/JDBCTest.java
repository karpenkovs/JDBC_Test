import jdk.internal.util.xml.BasicXmlPropertiesProvider;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTest {

    public static void main(String[] args) throws SQLException, IOException {

        JDBCTest jdbcTest = new JDBCTest();
        Properties properties = jdbcTest.loadProperties();
        Connection connection = DriverManager.
                getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password"));

        System.out.println("Connection established.");

        jdbcTest.addStudent(connection);
        jdbcTest.printStudents(connection);
    }

    private Properties loadProperties() throws IOException {

        Properties properties = new Properties();
        InputStream stream = getClass().getResourceAsStream("db.properties");
        properties.load(stream);
        return properties;
    }

    public void printStudents(Connection connection) throws SQLException {

        String sql = "select * from students";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            String str = "";
            str += resultSet.getString("id") + ", ";
            str += resultSet.getString("lastname") + ", ";
            str += resultSet.getString("firstname") + ", ";
            str += resultSet.getString("age") + ", ";

            System.out.println(str);
        }

    }

    public void addStudent(Connection connection) throws SQLException {
        String sql = "insert into students(lastname, firstname, age) values(?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "Sidorov");
        preparedStatement.setString(2, "Arkadij");
        preparedStatement.setInt(3, 25);

        preparedStatement.execute();
    }
}
