package cpp.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cpp.common.Page;
import cpp.course.mapper.CourseCommentMapper;
import cpp.course.po.Course;
import cpp.course.po.CourseComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCommentService {
    @Autowired
    CourseCommentMapper courseCommentMapper;

    public List<CourseComment> listComments(CourseComment comment) {
        return courseCommentMapper.listComments(comment);
    }

    public void add(CourseComment comment) {
        courseCommentMapper.add(comment);
    }

    public Page<CourseComment> listCommentsPage(CourseComment entity, Page<CourseComment> page) {
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        List<CourseComment> items = courseCommentMapper.listComments(entity);
        PageInfo<CourseComment> pageInfo = new PageInfo<>(items);
        page.setItemsTotalCount((int)pageInfo.getTotal());
        page.setItems(items);
        return page;
    }

    public void removeById(int id) {
        courseCommentMapper.removeById(id);
    }

    public CourseComment getById(int id) {
        return courseCommentMapper.getById(id);
    }
}
