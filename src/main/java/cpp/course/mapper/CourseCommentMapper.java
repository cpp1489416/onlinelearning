package cpp.course.mapper;

import cpp.common.Page;
import cpp.course.po.CourseComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCommentMapper {
    List<CourseComment> listComments(CourseComment comment);

    void add(CourseComment comment);

    int getCommentsCount(CourseComment entity);

    List<CourseComment> listCommentsPage(@Param("entity")CourseComment entity, @Param("page") Page<CourseComment> page);

    CourseComment getById(int id);

    void removeById(int id);
}
