package UserManager.dao;

import UserManager.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {

    // THÊM USER
    public void insertUser(User user) throws SQLException;

    // TÌM (HOẶC HIỂN THỊ) 1 USER
    public User selectUser(int id);

    // HIỂN THỊ DAH SÁCH
    public List<User> selectAllUsers();

    public boolean deleteUser(int id) throws SQLException;

    public boolean updateUser(User user) throws SQLException;
}
