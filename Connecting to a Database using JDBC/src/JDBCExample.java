public class JDBCExample {
    public static void main(String[] args) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.createTable();
        dbHelper.insertUser("John Doe", "john.doe@example.com");
        dbHelper.insertUser("Jane Smith", "jane.smith@example.com");
        dbHelper.selectAllUsers();
    }
}
