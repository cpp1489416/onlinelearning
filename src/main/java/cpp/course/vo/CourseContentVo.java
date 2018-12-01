package cpp.course.vo;

import cpp.course.po.CourseContent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 包含子目录的课程章节
public class CourseContentVo  extends CourseContent {
    List<CourseContent> contents = new ArrayList<>();

    public List<CourseContent> getContents() {
        return contents;
    }

    public void setContents(List<CourseContent> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "CourseContentVo{" +
                "contents=" + contents +
                '}';
    }
}
