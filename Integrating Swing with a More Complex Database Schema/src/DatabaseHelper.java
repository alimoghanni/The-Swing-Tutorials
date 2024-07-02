import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:sample.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "email TEXT)";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY, " +
                "user_id INTEGER, " +
                "product_name TEXT, " +
                "amount REAL, " +
                "FOREIGN KEY(user_id) REFERENCES users(id))";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createOrdersTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(String name, String email) {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertOrder(int userId, String productName, double amount) {
        String sql = "INSERT INTO orders(user_id, product_name, amount) VALUES(?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, productName);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAllUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAllOrders() {
        String sql = "SELECT o.id, u.name, o.product_name, o.amount " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("product_name") + "\t" +
                        rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Other CRUD operations...
}
