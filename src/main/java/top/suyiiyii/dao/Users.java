package top.suyiiyii.dao;

import top.suyiiyii.models.User;

import java.util.NoSuchElementException;


/**
 * UsersDAO 接口
 *
 * @author suyiiyii
 */
public interface Users {
    User getUserById(int id);

    User getUserByUsername(String username) throws NoSuchElementException;

    User createUser(User user);

//    void updateUser(User user);
//
//    void deleteUser(int id);
//
//    int getUserCount();


}
