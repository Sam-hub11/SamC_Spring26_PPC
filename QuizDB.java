import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizDB {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Usage: java QuizDB <username> <password> <database>");
            return;
        }


        String user = args[0];
        String password = args[1];
        String database = args[2];
        String url = "jdbc:mariadb://localhost:3306/" + database;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter salesman name to filter (leave blank for all): ");
        String salesmanInput = scanner.nextLine();

        // ArrayList to store Sales objects
        ArrayList<Sales> salesList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MariaDB database: " + database);

            String query =
                    "SELECT o.order_no, c.customer_name, c.city AS customer_city, " +
                    "s.name AS salesman_name, o.purchase_amt, (o.purchase_amt * s.commission) AS commission_amount " +
                    "FROM orders o " +
                    "JOIN customer c ON o.customer_id = c.customer_id " +
                    "JOIN salesman s ON o.salesman_id = s.salesman_id";
            
            
            if (!salesmanInput.isEmpty()) {
                query += " WHERE s.name = ?";
            }

            PreparedStatement stmt = conn.prepareStatement(query);

            if (!salesmanInput.isEmpty()) {
                stmt.setString(1, salesmanInput);
            }

            ResultSet rs = stmt.executeQuery();

            boolean found = false;

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("order_no"),
                        rs.getString("customer_name"),
                        rs.getString("customer_city"),
                        rs.getString("salesman_name"),
                        rs.getDouble("purchase_amt"),
                        rs.getDouble("commission_amount")
                );

                salesList.add(sale);
                System.out.println(sale);
                found = true;
            }
            //nothing was found
            if (!found) {
                System.out.println("No sales records found for that salesman.");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            //if there is an error, print it out
            System.out.println("Database error:");
            e.printStackTrace();
        }

        scanner.close();
    }
}
