package cpp.course.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.course.po.Course;
import cpp.course.po.CourseComment;
import cpp.course.service.CourseCommentService;
import cpp.course.service.CourseService;
import cpp.user.po.User;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("comment")
public class CourseCommentController {
    @Autowired
    CourseCommentService courseCommentService;
    @Autowired
    CourseService courseService;

    @RequestMapping("comment.action")
    @ResponseBody
    public OutInfo commentCourse(CourseComment comment) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        comment.setUserId(user.getId());
        courseCommentService.add(comment);
        return OutInfo.success();
    }

    @RequestMapping("comments.action")
    @ResponseBody
    public List<CourseComment> comments(CourseComment comment) {
        return courseCommentService.listComments(comment);
    }

    static class CommentsPageIn {
        public CourseComment comment;
        public Page<CourseComment> page;
    }

    @RequestMapping("commentsPage.action")
    @ResponseBody
    Page<CourseComment> commentsPage(@RequestBody CommentsPageIn in) {
        return courseCommentService.listCommentsPage(in.comment, in.page);
    }

    @RequiresRoles("teacher")
    @RequestMapping("removeComment.action")
    @ResponseBody
    public OutInfo removeComment(CourseComment comment) {
        User user = MySession.getUser();
        CourseComment oldComment = courseCommentService.getById(comment.getId());
        Course course = courseService.getCourse(oldComment.getCourseId());
        if (user.getRole() == 1 && user.getId() != course.getUserId()) {
            return OutInfo.failure("您不能修改其他人的评论");
        }
        courseCommentService.removeById(comment.getId());
        return OutInfo.success();
    }
}
