package cpp.user.mapper;

import cpp.common.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import cpp.user.po.User;

import java.util.List;

@Component
public interface UserMapper {
    void addUser(User user);

    User getUserByUsername(String username);

    User getUserById(int id);

    User getUserByUsernameAndPassword(User user);

    void modify(User user);

    void modifyPassword(User user);

    int getUsersCount(User user);

    List<User> listUsersPage(@Param("entity") User entity, @Param("page") Page page);

    void modifyRole(User user);
}
