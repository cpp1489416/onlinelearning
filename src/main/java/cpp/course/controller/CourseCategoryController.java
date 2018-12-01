package cpp.course.controller;

import cpp.common.OutInfo;
import cpp.course.po.CourseCategory;
import cpp.course.service.CourseCategoryService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CourseCategoryController {
    @Autowired
    private CourseCategoryService courseCategoryService;

    @RequestMapping("categories.action")
    @ResponseBody
    public List<CourseCategory> categories() {
        List<CourseCategory> ans = courseCategoryService.listCategories();
        CourseCategory all = new CourseCategory();
        all.setName("全部");
        all.setId(0);
        all.setSort(-1);
        ans.add(0, all);
        for (int i = 0; i < ans.size(); ++i) {
            CourseCategory cur = ans.get(i);
            CourseCategory c = new CourseCategory();
            c.setId(cur.getId());
            c.setName("全部");
            c.setSort(-1);
            cur.getCategories().add(0, c);
        }
        return ans;
    }

    @RequestMapping("category.action")
    @ResponseBody
    public CourseCategory category(@RequestParam("id")int id) {
        return courseCategoryService.getCategory(id);
    }

    @RequiresRoles("admin")
    @RequestMapping("removeCategory.action")
    @ResponseBody
    public OutInfo removeCategory(@RequestParam("id") int id) {
        courseCategoryService.removeCategory(id);
        return OutInfo.success();
    }

    @RequiresRoles("admin")
    @RequestMapping("modifyCategory.action")
    @ResponseBody
    public OutInfo modifyCategory(CourseCategory category) {
        courseCategoryService.modifyCategory(category);
        return OutInfo.success();
    }

    @RequiresRoles("admin")
    @RequestMapping("addCategory.action")
    @ResponseBody
    public OutInfo addCategory(CourseCategory category) {
        if (courseCategoryService.addCategory(category)) {
            return OutInfo.success();
        } else {
            return OutInfo.failure("没有父级为" + category.getParentId() + "的分类");
        }
    }
}
