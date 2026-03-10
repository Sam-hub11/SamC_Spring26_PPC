import java.sql.*;
import java.util.Scanner;

public class DisplayCountries {

    public static void main(String[] args) {

        String url = "jdbc:mariadb://localhost:3306/nation";
        String user = "root";
        String password = "thePassword";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter language: ");
        String language = scanner.nextLine();

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MariaDB");

            String query =
                    "SELECT c.name " +
                    "FROM countries c " +
                    "JOIN country_languages cl ON c.country_id = cl.country_id " +
                    "JOIN languages l ON cl.language_id = l.language_id " +
                    "WHERE l.language = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, language);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Countries speaking " + language + ":");

            boolean found = false;

            while (rs.next()) {
                System.out.println(rs.getString("name"));
                found = true;
            }

            if (!found) {
                System.out.println("No countries found.");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }

        scanner.close();
    }
}