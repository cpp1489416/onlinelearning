package cpp.course.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.course.dto.CourseDto;
import cpp.course.po.Course;
import cpp.course.po.CourseComment;
import cpp.course.po.CourseContent;
import cpp.course.service.CourseCommentService;
import cpp.course.service.CourseContentService;
import cpp.course.service.CourseService;
import cpp.course.vo.CourseContentVo;
import cpp.user.dto.UserDto;
import cpp.user.po.User;
import cpp.user.po.UserCourseContent;
import cpp.user.service.UserCourseContentService;
import cpp.user.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseContentService courseContentService;
    @Autowired
    UserService userService;
    @Autowired
    UserCourseContentService userCourseContentService;
    @Autowired
    CourseCommentService courseCommentService;

    @RequestMapping("courses.action")
    @ResponseBody
    public List<Course> courses(Course course) {
        return courseService.listCourses(course);
    }

    @RequestMapping("course.action")
    @ResponseBody
    public CourseDto course(@RequestParam("courseId") int courseId) {
        int loggedUserId = MySession.getUserId();
        CourseDto dto = new CourseDto();
        if (courseService.getCourse(courseId) == null) {
            return dto;
        }
        dto.setCourse(courseService.getCourse(courseId));
        if (courseService.isCourseObtained(courseId, loggedUserId)) {
            dto.getCourse().setObtained(true);
            dto.setContents(courseContentService.listCourseContentsByCourseId(courseId));
        } else {
            dto.setContents(new ArrayList<>());
        }
        User user = userService.getUserById(dto.getCourse().getUserId());
        UserDto userDto = new UserDto();
        System.out.println(user);
        System.out.println(userDto);
        org.springframework.beans.BeanUtils.copyProperties(user, userDto); // 使用Spring的beanUtils
        dto.setAuthor(userDto);
        UserCourseContent ucc = new UserCourseContent();
        ucc.setCourseId(courseId);
        ucc.setUserId(MySession.getUserId());
        ucc = userCourseContentService.getLatest(ucc);
        if (dto.getCurrentCourseContent() == null) {
            dto.setCurrentCourseContent(new CourseContent());
        }
        CourseComment comment = new CourseComment();
        comment.setCourseId(courseId);
        comment.setContentId(0);
        dto.setComments(courseCommentService.listComments(comment));
        return dto;
    }

    @RequestMapping("contents.action")
    @ResponseBody
    public List<CourseContentVo> contents(@RequestParam("courseId") int courseId) {
        int userId = MySession.getUserId();
        if (!courseService.isCourseObtained(courseId, userId)) {
            return null;
        }
        return courseContentService.listCourseContentsByCourseId(courseId);
    }

    @RequestMapping("content.action")
    @ResponseBody
    public CourseContent content(CourseContent courseContent) {
        int userId = MySession.getUserId();
        courseContent = courseContentService.getById(courseContent.getId());
        if (!courseService.isCourseObtained(courseContent.getCourseId(), userId)) {
            return null;
        }
        User user = MySession.getUser();
        if (user != null) {
            UserCourseContent ucc = new UserCourseContent();
            ucc.setUserId(user.getId());
            ucc.setCourseId(courseContent.getCourseId());
            ucc.setContentId(courseContent.getId());
            UserCourseContent oldUcc = userCourseContentService.getByUserIdAndContentId(ucc);
            if (oldUcc != null) {
                ucc.setUpdateTime(new Date());
                userCourseContentService.modify(ucc);
            } else {
                userCourseContentService.add(ucc);
            }
        }
        return courseContentService.getById(courseContent.getId());
    }

    @RequiresRoles("teacher")
    @RequestMapping("myCourses.action")
    @ResponseBody
    public List<Course> myCourses(Course course) {
        User user = MySession.getUser();
        if (user == null) {
            return new LinkedList<>();
        }

        course.setUserId(user.getId());
        return courseService.listCoursesByUserIdAndName(course);
    }

    private static class MyCoursesPageInInfo {
        public Course course;
        public Page<Course> page;
    }

    @RequiresRoles("teacher")
    @RequestMapping("myCoursesPage.action")
    @ResponseBody
    public Page<Course> myCoursesPage(@RequestBody MyCoursesPageInInfo inInfo) {
        User user = MySession.getUser();
        if (user == null) {
            return new Page<Course>();
        }
        inInfo.course.setUserId(user.getId());
        return courseService.listCoursesByUserIdAndNamePage(inInfo.course, inInfo.page);
    }

    @RequiresRoles("teacher")
    @RequestMapping("allCoursesPage.action")
    @ResponseBody
    public Page<Course> allCoursesPage(@RequestBody MyCoursesPageInInfo inInfo) {
        User user = MySession.getUser();
        if (user == null) {
            return new Page<Course>();
        }
        inInfo.course.setUserId(user.getId());
        return courseService.listCoursesPage(inInfo.course, inInfo.page);
    }

    @RequestMapping("coursesPage.action")
    @ResponseBody
    public Page<Course> coursesPage(@RequestBody MyCoursesPageInInfo inInfo) {
        return courseService.listCoursesPage(inInfo.course, inInfo.page);
    }

    @RequiresRoles("teacher")
    @RequestMapping("addCourse.action")
    @ResponseBody
    public OutInfo addCourse(Course course) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }

        course.setUserId(user.getId());
        courseService.addCourse(course);
        OutInfo out = OutInfo.success();
        out.setId(course.getId());
        return out;
    }

    @RequiresRoles("teacher")
    @RequestMapping("modifyCourse.action")
    @ResponseBody
    public OutInfo modifyCourse(Course course) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }

        Course oldCourse = courseService.getCourse(course.getId());
        if (user.getRole() != 2 && oldCourse.getUserId() != user.getId()) {
            return OutInfo.failure("您不能修改他人的课程");
        }

        course.setUserId(user.getId());
        courseService.modifyCourse(course);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("removeCourse.action")
    @ResponseBody
    public OutInfo removeCourse(Course course) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }

        Course oldCourse = courseService.getCourse(course.getId());
        if (user.getRole() != 2 && oldCourse.getUserId() != user.getId()) {
            return OutInfo.failure("您不能修改其他人的课程");
        }

        courseService.removeCourse(course.getId());
        return OutInfo.success();
    }

    @RequestMapping("obtainCourse.action")
    @ResponseBody
    public OutInfo obtainCourse(@RequestParam("courseId") int courseId, @RequestParam("secretCode") String secretCode) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        int userId = user.getId();
        if (courseService.obtainCourse(courseId, userId, secretCode)) {
            return OutInfo.success();
        } else {
            return OutInfo.failure("对不起，您输入的密码不对");
        }
    }

    @RequiresRoles("teacher")
    @RequestMapping("modifyCourseSecretCode.action")
    @ResponseBody
    public OutInfo modifyCourseSecret(@RequestParam("id")int id, @RequestParam("secretCode")String secretCode) {
        User user = MySession.getUser();
        Course course = courseService.getCourse(id);
        if (course == null || user.getRole() != 2 && user.getId() != course.getUserId()) {
            return OutInfo.failure("您没有权限更改别人的课程密码");
        }
        course.setSecretCode(secretCode);
        courseService.modifyCourseSecretCode(course);
        return OutInfo.success();
    }

    @RequiresRoles("teacher")
    @RequestMapping("courseSecretCode.action")
    @ResponseBody
    public Map<String, Object> courseSecretCode(@RequestParam("courseId") int courseId) throws Exception {
        Map<String, Object> out = new HashMap<>();
        Course course = courseService.getCourse(courseId);
        User user = MySession.getUser();
        if (user.getRole() == 2 || course.getUserId() == user.getId()) {
            org.apache.commons.beanutils.BeanUtils.populate(OutInfo.success(), out); // 一定是Apache的BeanUtils
            String secretCode = courseService.getCourseSecretCode(courseId);
            out.put("secretCode", secretCode);
        } else {
            org.apache.commons.beanutils.BeanUtils.populate(OutInfo.failure("您没有权限更改其他人的课程"), out);
        }
        return out;
    }
}
