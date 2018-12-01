package cpp.course.dto;

import cpp.course.po.Course;
import cpp.course.po.CourseComment;
import cpp.course.po.CourseContent;
import cpp.course.vo.CourseContentVo;
import cpp.user.dto.UserDto;
import cpp.user.po.User;

import java.util.List;

public class CourseDto {
    Course course;
    UserDto author;
    List<CourseContentVo> contents;
    CourseContent currentCourseContent;
    List<CourseComment> comments;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public List<CourseContentVo> getContents() {
        return contents;
    }

    public void setContents(List<CourseContentVo> contents) {
        this.contents = contents;
    }

    public CourseContent getCurrentCourseContent() {
        return currentCourseContent;
    }

    public void setCurrentCourseContent(CourseContent currentCourseContent) {
        this.currentCourseContent = currentCourseContent;
    }

    public List<CourseComment> getComments() {
        return comments;
    }

    public void setComments(List<CourseComment> comments) {
        this.comments = comments;
    }
}
