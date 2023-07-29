package com.jdbcCrashCourse;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class jdbcCrashCourse {
    public static void main (String []args) {
        DataSource dataSource = createDataSource();

        //try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';")) {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("connection.isValid(0): " + connection.isValid(0));

            //  CRUD

            //  SELECT
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE name != ?");
            preparedStatement.setString(1, "Imad");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " -- " + resultSet.getString("name"));
            }

            //  INSERT
            PreparedStatement insertPreparedStatement = connection.prepareStatement("INSERT INTO USERS (name) VALUES (?)");
            insertPreparedStatement.setString(1, "Ali");

            int insertCount = insertPreparedStatement.executeUpdate();
            System.out.println("insertCount = " + insertCount);

            //  UPDATE
            PreparedStatement updatePreparedStatement = connection.prepareStatement("UPDATE USERS SET name = ? WHERE id = ?");
            updatePreparedStatement.setString(1, "Saida");
            updatePreparedStatement.setInt(2, 3);

            int updateCount = updatePreparedStatement.executeUpdate();
            System.out.println("updateCount = " + updateCount);

            //  DELETE
            PreparedStatement deletePreparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE name = ?");
            deletePreparedStatement.setString(1, "Saida");

            int deleteCount = deletePreparedStatement.executeUpdate();
            System.out.println("deleteCount = " + deleteCount);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';");
        //  dataSource.setUsername("root");
        //  dataSource.setPassword("root");

        return dataSource;
    }
}
