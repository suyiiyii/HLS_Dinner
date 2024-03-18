package top.suyiiyii.dao;

import top.suyiiyii.schemas.User;

public interface Users {
    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(User user);

//    void updateUser(User user);
//
//    void deleteUser(int id);
//
//    int getUserCount();


}
