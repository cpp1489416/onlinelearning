package cpp.tree.mapper;

import cpp.tree.po.ContentTreeNode;
import cpp.tree.po.CourseTreeNode;
import cpp.tree.po.TreeNode;

import java.util.List;

public interface TreeMapper {
    List<TreeNode> listRootCategories();

    List<TreeNode> listCategoriesByCategoryNode(TreeNode node);

    List<CourseTreeNode> listCoursesByCategoryNode(TreeNode node);

    List<ContentTreeNode> listContentsByCourseNode(TreeNode node);

    List<ContentTreeNode> listContentsByContentNode(TreeNode node);
}
