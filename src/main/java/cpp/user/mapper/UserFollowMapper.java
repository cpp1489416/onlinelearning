package cpp.user.mapper;

import cpp.common.Page;
import cpp.user.po.User;
import cpp.user.po.UserCollection;
import cpp.user.po.UserFollow;
import cpp.user.po.UserFollowStudyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowMapper {

    void add(UserFollow follow);

    void removeByUserIdAndFollowUserId(UserFollow follow);

    UserFollow getByUserIdAndFollowUserId(UserFollow follow);

    List<UserFollowStudyRecord> listUserFollowStudyRecord(int userId);

    int getUserFollowStudyRecordCount(int userId);

    List<UserFollowStudyRecord> listUserFollowStudyRecordPage(@Param("entity") User entity, @Param("page") Page<UserFollowStudyRecord> page);

    List<User> listFollowings(int userId);
}