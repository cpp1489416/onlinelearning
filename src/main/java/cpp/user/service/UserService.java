package cpp.user.service;

import cpp.common.Page;
import cpp.user.mapper.UserMapper;
import cpp.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void addUser(User user) {
        user.setHeadImageUrl("/onlinelearning/img/person.jpg");
        userMapper.addUser(user);
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        return userMapper.getUserByUsername(username);
    }

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    public User getUserByUsernameAndPassword(User user) {
        return userMapper.getUserByUsernameAndPassword(user);
    }

    public void modify(User user) {
        userMapper.modify(user);
    }

    public void modifyPassword(User user) {
        userMapper.modifyPassword(user);
    }

    public Page<User> listUsersPage(User entity, Page<User> page) {
        int count = userMapper.getUsersCount(entity);
        page.setItemsTotalCount(count);
        page.setItems(userMapper.listUsersPage(entity, page));
        return page;
    }

    public void modifyRole(User user) {
        userMapper.modifyRole(user);
    }
}
