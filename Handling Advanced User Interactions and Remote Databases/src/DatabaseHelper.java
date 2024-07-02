import java.sql.*;

public class DatabaseHelper {
    private static final String SQLITE_URL = "jdbc:sqlite:sample.db";

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(SQLITE_URL);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "email TEXT NOT NULL"
                + ");";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER NOT NULL,"
                + "product_name TEXT NOT NULL,"
                + "amount REAL NOT NULL,"
                + "FOREIGN KEY (user_id) REFERENCES users(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = (conn != null) ? conn.createStatement() : null) {
            if (stmt != null) {
                stmt.execute(createUsersTable);
                stmt.execute(createOrdersTable);
                System.out.println("Tables created successfully.");
            } else {
                System.out.println("Statement creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertUser(String name, String email) {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(sql) : null) {
            if (pstmt != null) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
                System.out.println("User inserted successfully.");
            } else {
                System.out.println("PreparedStatement creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertOrder(int userId, String productName, double amount) {
        String sql = "INSERT INTO orders(user_id, product_name, amount) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(sql) : null) {
            if (pstmt != null) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, productName);
                pstmt.setDouble(3, amount);
                pstmt.executeUpdate();
                System.out.println("Order inserted successfully.");
            } else {
                System.out.println("PreparedStatement creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(sql) : null) {
            if (pstmt != null) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("PreparedStatement creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(sql) : null) {
            if (pstmt != null) {
                pstmt.setInt(1, orderId);
                pstmt.executeUpdate();
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("PreparedStatement creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void selectAllUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = connect();
             Statement stmt = (conn != null) ? conn.createStatement() : null;
             ResultSet rs = (stmt != null) ? stmt.executeQuery(sql) : null) {

            if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                            rs.getString("name") + "\t" +
                            rs.getString("email"));
                }
            } else {
                System.out.println("ResultSet creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void selectAllOrders() {
        String sql = "SELECT * FROM orders";

        try (Connection conn = connect();
             Statement stmt = (conn != null) ? conn.createStatement() : null;
             ResultSet rs = (stmt != null) ? stmt.executeQuery(sql) : null) {

            if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                            rs.getInt("user_id") + "\t" +
                            rs.getString("product_name") + "\t" +
                            rs.getDouble("amount"));
                }
            } else {
                System.out.println("ResultSet creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
