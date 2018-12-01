package cpp.course.mapper;

import cpp.common.Page;
import cpp.course.po.Course;
import cpp.user.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseMapper {
    List<Course> listAllCourses();

    List<Course> listCourses(Course course);

    Course getCourse(int courseId);

    List<Course> listCoursesByUserIdAndName(Course course);

    void addCourse(Course course);

    int getCoursesByUserIdAndNameCount(Course course);

    List<Course> listCoursesByUserIdAndNamePage(@Param("course") Course course, @Param("page") Page<Course> page);

    void modifyCourse(Course course);

    void removeCourse(int id);

    int getCoursesByNameCount(Course course);

    List<Course> listCoursesByNamePage(@Param("course") Course course, @Param("page") Page<Course> page);

    int getCountPeopleByCourseId(int courseId);

    void modifyCourseSecretCode(Course course);

    String getCourseSecretCode(int courseId);
}