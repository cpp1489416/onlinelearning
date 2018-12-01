package cpp.course.mapper;

import cpp.course.po.CourseCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseCategoryMapper {
    List<CourseCategory> listCategories();

    CourseCategory getById(int id);

    List<CourseCategory> listChildren(int id);

    List<CourseCategory> getSubCategoriesById(int id);

    void removeCategory(int id);

    void removeChildren(int id);

    void updateCourseCategoryIdTo0(int categoryId);

    void modifyCategory(CourseCategory category);

    void addCategory(CourseCategory category);

}
