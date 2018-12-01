package cpp.course.mapper;

import cpp.course.po.Course;
import cpp.course.po.CourseContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseContentMapper {
    List<CourseContent> listCourseContentsByCourseId(int courseId);

    CourseContent getById(int id);

    CourseContent getMaxSortBeforeContent(CourseContent content);

    CourseContent getMinSortAfterContent(CourseContent content);

    void modify(CourseContent content);

    void moveSortBeforeRemove(CourseContent content);

    void remove(CourseContent content);

    void removeSubContentsByParentId(int parentId);

    int getMaxSortByCourseIdAndParentId(CourseContent content);

    void addContent(CourseContent content);

    void modifyText(CourseContent content);
}
