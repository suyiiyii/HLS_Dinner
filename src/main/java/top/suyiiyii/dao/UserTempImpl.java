package top.suyiiyii.dao;

import top.suyiiyii.schemas.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UserTempImpl implements Users {
    private static final List<User> users = new LinkedList<>();

    @Override
    public User getUserById(int id) {
        return users.stream().filter(user1 -> user1.uid == id).findFirst().orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return users.stream().filter(user1 -> user1.username.equals(username)).findFirst().orElse(null);
    }

    @Override
    public User createUser(User user) {
        user.uid = users.size() + 1;
        users.add(user);
        return user;
    }
}
