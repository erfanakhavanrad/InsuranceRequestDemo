package com.example.demo.dao;

import com.example.demo.model.Element;
import com.example.demo.model.InsurancePlan;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Random;

@Service
public class ICalculationImpl implements ICalculation {
    @Override
    public InsurancePlan yearlyFeeCalculator(Element element) throws Exception {

        if (trackingCodeValidator(element.getTrackingCode())) {

            if (element.getBodyInsuranceAmount() > element.getThirdPartyInsuranceAmount()) {
                System.out.println("Body has higher level");
                return bodyPrioritizedPlan(element);
            } else if (element.getBodyInsuranceAmount() < element.getThirdPartyInsuranceAmount()) {
                System.out.println("Third has higher level");
                return thirdPartyPrioritizedPlan(element);
            } else {
                System.out.println("Same");
                return normalPlan(element);
            }
        } else {
            System.out.println("Tracking code is not validated yet.");
            return null;
        }


    }

    @Override
    public Element saveInsuranceRequest(Element element) throws Exception {
        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        int userTrackingCode = trackingCodeGenerator();

//        String sqlQuery = "INSERT INTO Requests (name) " + "VALUES ('" + element.getName() + "')";
        String sqlQuery = "INSERT INTO Requests (name, lastName, nationalCode, trackingCode, verified) " +
                "VALUES ('" + element.getName() + "', '" + element.getLastName() + "', '" + element.getNationalCode() + "'," + userTrackingCode + ", 0)";
        int i = statement.executeUpdate(sqlQuery);
//        System.out.println("INSERTING" + i);

        if (i == 1) {
            String sql = "SELECT id, name FROM Requests";
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("**********");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
            }
            rs.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Failed to insert Log.");
        }

        element.setTrackingCode(userTrackingCode);


        return element;
    }

    @Override
    public Element approveRequest(Element element) throws Exception {


        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        int userTrackingCode = trackingCodeGenerator();

//        String sqlQuery = "INSERT INTO Requests (name) " + "VALUES ('" + element.getName() + "')";
        String sqlQuery = "UPDATE Requests SET " +
                " VERIFIED = " + element.isVerified() + " WHERE trackingCode = " + element.getTrackingCode();
        int i = statement.executeUpdate(sqlQuery);
//        System.out.println("INSERTING" + i);

        if (i == 1) {
            String sql = "SELECT id, name FROM Requests WHERE trackingCode = " + element.getTrackingCode();
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("**********");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
            }
            rs.close();
            statement.close();
            connection.close();
        } else {
            System.out.println("Failed to insert Log.");
        }

        return element;
    }

    @Override
    public boolean trackingCodeValidator(int trackingCode) throws Exception {


        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        int userTrackingCode = trackingCodeGenerator();

//        String sqlQuery = "INSERT INTO Requests (name) " + "VALUES ('" + element.getName() + "')";
//        String sqlQuery = "INSERT INTO Requests (name, lastName, nationalCode, trackingCode, verified) " +
//                "VALUES ('" + element.getName() + "', '" + element.getLastName() + "', '" + element.getNationalCode() + "'," + userTrackingCode + ", 0)";
//        int i = statement.executeUpdate(sqlQuery);
//        System.out.println("INSERTING" + i);

//        if (i == 1) {
        String sql = "SELECT * FROM Requests WHERE trackingCode = " + trackingCode;
        ResultSet rs = statement.executeQuery(sql);
        System.out.println("**********");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            System.out.print("ID: " + id);
            System.out.print(", name: " + name);
        }
        rs.close();
        statement.close();
        connection.close();
//        } else {
//            System.out.println("Failed to insert Log.");
//        }

//        element.setTrackingCode(userTrackingCode);


        return false;
    }

    private InsurancePlan bodyPrioritizedPlan(Element element) throws Exception {
        InsurancePlan insurancePlan = null;
        if (element.getBodyInsuranceAmount() <= 50000000) {
            insurancePlan = new InsurancePlan("Silver Body Based Plan", 1000000, 12);
            return insurancePlan;
        } else if (element.getBodyInsuranceAmount() > 50000000 && element.getBodyInsuranceAmount() <= 1000000000) {
            insurancePlan = new InsurancePlan("Golden Body Based Plan", 2000000, 12);
            return insurancePlan;
        }
        throw new Exception("Value doesn't fall within supported range");
    }

    private InsurancePlan thirdPartyPrioritizedPlan(Element element) throws Exception {
        InsurancePlan insurancePlan = null;
        if (element.getThirdPartyInsuranceAmount() <= 50000000) {
            insurancePlan = new InsurancePlan("Silver Third Party Based Plan", 1500000, 18);
            return insurancePlan;
        } else if (element.getThirdPartyInsuranceAmount() > 50000000 && element.getThirdPartyInsuranceAmount() <= 1000000000) {
            insurancePlan = new InsurancePlan("Golden Third Party Based Plan", 3000000, 18);

            return insurancePlan;
        }
        throw new Exception("Value doesn't fall within supported range");
    }

    private InsurancePlan normalPlan(Element element) throws Exception {
        InsurancePlan insurancePlan = null;
        if (element.getThirdPartyInsuranceAmount() <= 50000000) {
            insurancePlan = new InsurancePlan("Silver Plan", 2000000, 12);
            return insurancePlan;
        } else if (element.getThirdPartyInsuranceAmount() > 50000000 && element.getThirdPartyInsuranceAmount() <= 1000000000) {
            insurancePlan = new InsurancePlan("Golden Plan", 3200000, 18);
            return insurancePlan;
        }
        throw new Exception("Value doesn't fall within supported range");
    }


    private static Connection connectToDatabase() {
        final String JDBC_DRIVER = "org.h2.Driver";
//        final String DB_URL = " jdbc:h2:mem:test";
        final String DB_URL = "jdbc:h2:mem:testdb";

        //  Database credentials
        final String USER = "sa";
        final String PASS = "1";


        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");

            // STEP 5: Clean-up environment
//            rs.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }


    private static void connectToDatabase2() {
        final String JDBC_DRIVER = "org.h2.Driver";
//        final String DB_URL = " jdbc:h2:mem:test";
        final String DB_URL = "jdbc:h2:mem:testdb";

        //  Database credentials
        final String USER = "sa";
        final String PASS = "1";


        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String sql = "SELECT id, name FROM Requests";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                int id = rs.getInt("id");
                String name = rs.getString("name");
//                String first = rs.getString("first");
//                String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", name: " + name);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
            }
            // STEP 5: Clean-up environment
            rs.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");


    }


    private int trackingCodeGenerator() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }


}
