package cpp.user.mapper;

import cpp.user.po.UserCourseContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCourseContentMapper {
    UserCourseContent getLatest(UserCourseContent ucc);

    void modify(UserCourseContent ucc);

    void add(UserCourseContent ucc);

    UserCourseContent getByUserIdAndContentId(UserCourseContent ucc);

    List<UserCourseContent> listByUserId(int userId);

    UserCourseContent getByUserIdAndCourseIdAndContentIdEquals0(@Param("userId")int userId, @Param("courseId")int courseId);
}
