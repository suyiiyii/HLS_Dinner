package top.suyiiyii.dao;

import top.suyiiyii.schemas.User;

import java.util.NoSuchElementException;

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
