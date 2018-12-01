package cpp.course.service;

import cpp.course.mapper.CourseCategoryMapper;
import cpp.course.po.CourseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseCategoryService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    private CourseService courseService;

    public List<CourseCategory> listCategories() {
        List<CourseCategory> list = courseCategoryMapper.listCategories();
        HashMap<Integer, CourseCategory> map = new HashMap<>();
        List<CourseCategory> ans = new LinkedList<>();
        for (int i = 0; i < list.size(); ++i) {
            map.put(list.get(i).getId(), list.get(i));
        }
        for (int i = 0; i < list.size(); ++i) {
            CourseCategory category = list.get(i);
            if (category.getParentId() == 0) {
                ans.add(category);
            } else {
                CourseCategory parent = map.get(category.getParentId());
                parent.getCategories().add(category);
            }
        }

        for (int i = 0; i < list.size(); ++i) {
            CourseCategory category = list.get(i);
            if (category.getCategories() != null) {
                Collections.sort(category.getCategories(), new Comparator<CourseCategory>() {
                    @Override
                    public int compare(CourseCategory o1, CourseCategory o2) {
                        return o1.getSort() - o2.getSort();
                    }
                });
            }
        }
        return ans;
    }

    public void removeCategory(int id) {
        courseCategoryMapper.updateCourseCategoryIdTo0(id);
        List<CourseCategory> children = courseCategoryMapper.listChildren(id);
        for (CourseCategory category: children) {
            removeCategory(category.getId());
        }
        courseCategoryMapper.removeChildren(id);
        courseCategoryMapper.removeCategory(id);
    }

    public void modifyCategory(CourseCategory category) {
        courseCategoryMapper.modifyCategory(category);
    }

    public boolean addCategory(CourseCategory category) {
        int parentId = category.getParentId();
        if (parentId != 0 && courseCategoryMapper.getById(parentId) == null) {
            return false;
        }
        courseCategoryMapper.addCategory(category);
        return true;
    }

    public CourseCategory getCategory(int id) {
        return courseCategoryMapper.getById(id);
    }
}
