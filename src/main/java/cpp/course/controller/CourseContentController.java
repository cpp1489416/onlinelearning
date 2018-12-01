package cpp.course.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.course.po.Course;
import cpp.course.po.CourseContent;
import cpp.course.service.CourseContentService;
import cpp.course.service.CourseService;
import cpp.user.po.User;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("course")
public class CourseContentController {
    @Autowired
    CourseContentService courseContentService;
    @Autowired
    CourseService courseService;

    @RequiresRoles("teacher")
    @RequestMapping("moveBeforeContent.action")
    @ResponseBody
    public OutInfo moveBeforeContent(@RequestParam("id")int id) {
        CourseContent content = courseContentService.getById(id);
        Course course = courseService.getCourse(content.getCourseId());
        User user = MySession.getUser();
        if (user.getId() != course.getUserId()) {
            return OutInfo.failure("您不能修改他人的课程");
        }

        courseContentService.moveBefore(content);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("moveAfterContent.action")
    @ResponseBody
    public OutInfo moveAfterContent(@RequestParam("id")int id) {
        CourseContent content = courseContentService.getById(id);
        Course course = courseService.getCourse(content.getCourseId());
        User user = MySession.getUser();
        if (user.getId() != course.getUserId()) {
            return OutInfo.failure("您不能修改他人的课程");
        }


        courseContentService.moveAfter(content);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("removeContent.action")
    @ResponseBody
    public OutInfo removeContent(@RequestParam("id")int id) {
        CourseContent content = courseContentService.getById(id);
        Course course = courseService.getCourse(content.getCourseId());
        User user = MySession.getUser();
        if (user.getId() != course.getUserId()) {
            return OutInfo.failure("您不能修改他人的课程");
        }

        courseContentService.removeContent(content);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("addContent.action")
    @ResponseBody
    public OutInfo addContent(CourseContent content) {
        Course course = courseService.getCourse(content.getCourseId());
        User user = MySession.getUser();
        if (user.getId() != course.getUserId()) {
            return OutInfo.failure("您不能修改他人的课程");
        }

        courseContentService.addContent(content);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("modifyContentText.action")
    @ResponseBody
    public OutInfo modifyContentText(CourseContent content) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        CourseContent oldContent = courseContentService.getById(content.getId());
        Course course = courseService.getCourse(oldContent.getCourseId());
        if (course.getUserId() != user.getId()) {
            return OutInfo.failure("您不能修改其他人的课程");
        }

        oldContent.setText(content.getText());
        courseContentService.modifyText(content);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("modifyContentName.action")
    @ResponseBody
    public OutInfo modifyContentName(CourseContent  content) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        CourseContent oldContent = courseContentService.getById(content.getId());
        Course course = courseService.getCourse(oldContent.getCourseId());
        if (course.getUserId() != user.getId()) {
            return OutInfo.failure("您不能修改其他人的课程");
        }

        oldContent.setName(content.getName());
        courseContentService.modifyName(content);
        return OutInfo.success();
    }
}
