package cpp.tree.service;

import cpp.course.po.Course;
import cpp.course.service.CourseContentService;
import cpp.course.service.CourseService;
import cpp.tree.mapper.TreeMapper;
import cpp.tree.po.ContentTreeNode;
import cpp.tree.po.CourseTreeNode;
import cpp.tree.po.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TreeService {
    @Autowired
    private TreeMapper treeMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseContentService courseContentService;

    public List<TreeNode> listRoot() {
        List<TreeNode> ans = treeMapper.listRootCategories();
        ans.addAll(treeMapper.listCoursesByCategoryNode(new TreeNode()));
        return ans;
    }

    public List<TreeNode> listChildren(TreeNode node, int userId) {
        List<TreeNode> ans = new LinkedList<>();
        switch (node.getType()) {
            case "category":
                List<TreeNode> categories = treeMapper.listCategoriesByCategoryNode(node);
                List<CourseTreeNode> courses = treeMapper.listCoursesByCategoryNode(node);
                for (TreeNode courseNode : courses) {
                    courseNode.setObtained(courseService.isCourseObtained(courseNode.getId(), userId));
                }
                categories.addAll(courses);
                return categories;
            case "course":
                if (!courseService.isCourseObtained(node.getId(), userId)) {
                    return ans;
                }
                List<ContentTreeNode> contents = treeMapper.listContentsByCourseNode(node);
                ans.addAll(contents);
                return ans;
            case "content":
                if (!courseContentService.isCourseContentObtained(node.getId(), userId)) {
                    return ans;
                }
                List<ContentTreeNode> children = treeMapper.listContentsByContentNode(node);
                ans.addAll(children);
                return ans;
        }
        return null;
    }

    public void addChildrenCategories(TreeNode category) {
        List<TreeNode> list = treeMapper.listCategoriesByCategoryNode(category);
        if (list == null || list.size() == 0) {
            category.setLeaf(true);
            return;
        }
        category.setLeaf(false);
        category.setChildren(list);
        for (TreeNode child : category.getChildren()) {
            addChildrenCategories(child);
        }
    }

    public List<TreeNode> listCategories() {
        TreeNode root = new TreeNode();
        root.setId(0);
        root.setName("所有");
        root.setLeaf(false);
        root.setType("category");
        addChildrenCategories(root);
        List<TreeNode> ans = new ArrayList<>();
        ans.add(root);
        return ans;
    }
}
