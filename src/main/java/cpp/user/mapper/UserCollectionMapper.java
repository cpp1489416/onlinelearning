package cpp.user.mapper;

import cpp.user.po.UserCollection;
import cpp.user.po.UserCourseContent;
import java.util.List;

public interface UserCollectionMapper {
    void add(UserCollection collection);

    UserCollection getByUserIdAndCourseId(UserCollection collection);

    void removeByUserIdAndCourseId(UserCollection collection);

    List<UserCollection> listCourseCollectionsByUserId(int userId);
}
