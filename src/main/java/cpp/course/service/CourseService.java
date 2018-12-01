package cpp.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.course.dto.CourseDto;
import cpp.course.mapper.CourseContentMapper;
import cpp.course.mapper.CourseMapper;
import cpp.course.po.Course;
import cpp.user.mapper.UserCourseContentMapper;
import cpp.user.po.User;
import cpp.user.po.UserCourseContent;
import cpp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class  CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserCourseContentMapper userCourseContentMapper;
    @Autowired
    UserService userService;

    public List<Course> listAllCourses() {
        return courseMapper.listAllCourses();
    }

    public List<Course> listCourses(Course course) {
        return courseMapper.listCourses(course);
    }

    public Course getCourse(int courseId) {
        Course course = courseMapper.getCourse(courseId);
        if (course == null) {
            return null;
        }
        course.setCountPeople(courseMapper.getCountPeopleByCourseId(courseId));
        return course;
    }

    public List<Course> listCoursesByUserIdAndName(Course course) {
        return courseMapper.listCoursesByUserIdAndName(course);
    }

    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }

    public Page<Course> listCoursesByUserIdAndNamePage(Course course, Page<Course> page) {
        int count = courseMapper.getCoursesByUserIdAndNameCount(course);
        page.setItemsTotalCount(count);
        page.setItems(courseMapper.listCoursesByUserIdAndNamePage(course, page));
        return page;
    }

    // 更改课程信息，不包含课程的密码
    public void modifyCourse(Course course) {
        courseMapper.modifyCourse(course);
    }

    public void removeCourse(int id) {
        courseMapper.removeCourse(id);
    }

    public Page<Course> listCoursesPage(Course course, Page<Course> page) {
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        if ("name".equals(page.getOrderBy())) {
            PageHelper.orderBy("convert(name using gbk)");
        } else if ("updateTime".equals(page.getOrderBy())) {
            PageHelper.orderBy("updateTime desc");
        }
        List<Course> items = courseMapper.listCourses(course);
        PageInfo<Course> pageInfo = new PageInfo<>(items);
        page.setItemsTotalCount((int)pageInfo.getTotal());
        page.setItems(items);
        return page;
    }

    public boolean isCourseObtained(int courseId, int userId) {
        Course course = courseMapper.getCourse(courseId);
        if (course == null) {
            return false;
        }
        User user = userService.getUserById(userId);
        if ("".equals(course.getSecretCode()) || userId == course.getUserId() || user != null && user.getRole() == 2) {
            return true;
        } else {
            if (userCourseContentMapper.getByUserIdAndCourseIdAndContentIdEquals0(userId, courseId) != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    // 用户输入密码验证课程，插入user_course_content中，contentId是0，这是特殊情况
    public boolean obtainCourse(int courseId, int userId, String secretCode) {
        Course course = courseMapper.getCourse(courseId);
        if (course == null){
            return false;
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            return false;
        }
        if (!course.getSecretCode().equals(secretCode)) {
            return false;
        }
        UserCourseContent ucc = new UserCourseContent();
        ucc.setCourseId(courseId);
        ucc.setUserId(userId);
        ucc.setContentId(0);
        ucc.setUpdateTime(new Date());
        userCourseContentMapper.add(ucc);
        return true;
    }

    public void modifyCourseSecretCode(Course course) {
        courseMapper.modifyCourseSecretCode(course);
    }

    public String getCourseSecretCode(int courseId) {
        return courseMapper.getCourseSecretCode(courseId);
    }
}
