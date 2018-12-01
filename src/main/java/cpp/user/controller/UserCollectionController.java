package cpp.user.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.course.po.Course;
import cpp.course.service.CourseService;
import cpp.user.po.User;
import cpp.user.po.UserCollection;
import cpp.user.service.UserCollectionService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("collection")
public class UserCollectionController {
    @Autowired
    UserCollectionService userCollectionService;
    @Autowired
    CourseService courseService;

    @RequestMapping("isCollected.action")
    @ResponseBody
    public OutInfo isCollect(@RequestParam("courseId")int courseId) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        UserCollection collection = new UserCollection();
        collection.setCourseId(courseId);
        Course course = courseService.getCourse(courseId);
        collection.setUserId(user.getId());
        collection.setName(course.getName());
        if (userCollectionService.getByUserIdAndCourseId(collection) == null) {
            return OutInfo.no();
        } else {
            return OutInfo.yes();
        }
    }

    @RequestMapping("swapCollection.action")
    @ResponseBody
    public OutInfo collect(@RequestParam("courseId") int courseId) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        UserCollection collection = new UserCollection();
        collection.setCourseId(courseId);
        collection.setUserId(user.getId());
        Course course = courseService.getCourse(courseId);
        collection.setName(course.getName());
        if (userCollectionService.getByUserIdAndCourseId(collection) == null) {
            userCollectionService.add(collection);
            return OutInfo.yes();
        } else {
            userCollectionService.removeByUserIdAndCourseId(collection);
            return OutInfo.no();
        }
    }

    @RequestMapping("collections.action")
    @ResponseBody
    public List<UserCollection> collections() {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }

        return userCollectionService.listCourseCollectionsByUserId(user.getId());
    }

    static class CollectionsPageIn {
        public Page<UserCollection> page;
    }

    @RequiresAuthentication
    @RequestMapping("collectionsPage.action")
    @ResponseBody
    public Page<UserCollection> collectionsPage(@RequestBody CollectionsPageIn in) {
        User user = MySession.getUser();
        return userCollectionService.listCourseCollectionsPageByUserId(user.getId(), in.page);
    }
}
