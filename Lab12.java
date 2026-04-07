import java.sql.*;
import java.util.ArrayList;

public class Lab12 {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Usage: java Lab12 <username> <password> <database>");
            return;
        }

        String user = args[0];
        String password = args[1];
        String database = args[2];
        String url = "jdbc:mariadb://localhost:3306/" + database;

        ArrayList<SalesPerson> salesPersonList = new ArrayList<>();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MariaDB database: " + database);

            String query = "SELECT s.name, s.city, s.commission, " +
                           "IFNULL(SUM(o.purchase_amt),0) AS total_sales " +
                           "FROM salesman s " +
                           "LEFT JOIN orders o ON s.salesman_id = o.salesman_id " +
                           "GROUP BY s.salesman_id, s.name, s.city, s.commission";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SalesPerson sp = new SalesPerson(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getDouble("commission"),
                        rs.getDouble("total_sales")
                );
                salesPersonList.add(sp);
            }

            rs.close();
            stmt.close();
            conn.close();

            // Stream operations
            System.out.println("\n=== SalesPerson Names and Total Earnings ===");
            salesPersonList.stream()
                    .forEach(sp -> System.out.printf("%-15s | Total Earnings: $%.2f%n",
                            sp.getName(), sp.getTotalSales()));

            System.out.println("\n=== SalesPerson Names and Total Commissions ===");
            salesPersonList.stream()
                    .forEach(sp -> System.out.printf("%-15s | Total Commissions: $%.2f%n",
                            sp.getName(), sp.getTotalSales() * sp.getCommission()));

        } catch (Exception e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
}

// SalesPerson class
class SalesPerson {
    private String name;
    private String city;
    private double commission;
    private double totalSales;

    public SalesPerson(String name, String city, double commission, double totalSales) {
        this.name = name;
        this.city = city;
        this.commission = commission;
        this.totalSales = totalSales;
    }

    public String getName() { return name; }
    public String getCity() { return city; }
    public double getCommission() { return commission; }
    public double getTotalSales() { return totalSales; }

    @Override
    public String toString() {
        return name + " | " + city + " | Commission: " + commission +
               " | Total Sales: $" + totalSales;
    }
}