package UserManager.dao;

import UserManager.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {
    //DEMO THAY ĐỔI THEO ĐƯỜNG DẪN MÌNH MUỐN ĐẾN DATABASE
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "To@nOpen89";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    private static final String SELECT_USERS_BY_COUNTRY = "SELECT * FROM users WHERE country =?";
    private static final String SORT_USERS ="SELECT * FROM users ORDER BY name";

    public UserDao() {
    }

    // Connection KẾT NỐI VỚI DATABASE
    protected Connection getConection(){
        Connection connection = null;
        try {
            // Driver của jdbc
            Class.forName("com.mysql.jdbc.Driver");
            // link đến database, rồi đăng nhập tên, mật khẩu
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // giống signton
        return connection;
    }

    @Override
    public List<User> selectAllUsers() {
        // using try-with-resources to avoid closing resources (boiler plate code)
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConection();
             // Step 2:Create a statement using connection object
             // PreparedStatement của JDBC cho phép viết mã SQL
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            // Phương thức này thực thi câu select SQL, trả về 1 đối tượng ResultSet để chứa 1 danh sách các records thỏa mãn câu select.
            // Đối tượng ResultSet trong lập trình JDBC rất quan trọng, nó lưu giữ các mẫu tin thỏa mãn 1 tính chất nào đó của database
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            // ta có thể đọc 1 mẫu tin bằngphương thức next().
            // Phương thức này trả về false khi không có mẫu tin để đọc (có thể là cuối danh sách)
            // trả về ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    // THÊM USER
    @Override
    public void insertUser(User user){
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConection();
             // PreparedStatement của JDBC cho phép viết mã SQL
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);) {

            // truyền dữ liệu cho database, theo vị trí dấu ? điền 1,2,3
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            // executeUpdate(): Phương thức này dùng để thực thi các câu sql insert, delete, update ...
            // ngoại trừ câu select. Phương thức này trả về số mẫu tin bị ảnh hưởng bởi câu SQL.
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);){
            // theo vị trí ? điền 1,2,3
            preparedStatement.setInt(1, id);

            // Step 3: Execute the query or update query
            // Phương thức này thực thi câu select SQL,
            // trả về 1 đối tượng ResultSet để chứa 1 danh sách các records thỏa mãn câu select.
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            // next() thuộc ResultSet
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }



    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            // executeUpdate: thực hiện đc 1 câu lệnh trả về int = 1
            rowDeleted = statement.executeUpdate()>0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate()>0;
        }
        return false;
    }

    //    ???
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex){
            if(e instanceof SQLException){
                // err: HIỆN CHỮ ĐỎ, out: HIỆN CHỮ THƯỜNG
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public List<User> selectUserByCountry(String country) {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_COUNTRY);){
            //set giá trị cho từng dấu ?
            preparedStatement.setString(1, country);
            //trả về 1 bảng sau khi thực hiện câu lệnh query
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(id, name, email, country));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public List<User> sortListUserByCountry(){
        List<User> users = new ArrayList<>();
        Connection connection = getConection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SORT_USERS);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
