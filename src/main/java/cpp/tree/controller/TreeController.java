package cpp.tree.controller;

import cpp.common.shrio.MySession;
import cpp.tree.po.TreeNode;
import cpp.tree.service.TreeService;
import cpp.user.po.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("tree")
@Controller
public class TreeController {
    @Autowired
    private TreeService treeService;
    private static Logger logger = Logger.getLogger(TreeController.class);

    @RequestMapping("root.action")
    @ResponseBody
    public List<TreeNode> root() {
        List<TreeNode> ans = treeService.listRoot();
        isLeaf(ans);
        return ans;
    }

    @RequestMapping("children.action")
    @ResponseBody
    public List<TreeNode> children(TreeNode node) {
        User user = MySession.getUser();
        int userId = user == null ? 0 : user.getId();
        List<TreeNode> ans = treeService.listChildren(node, userId);
        isLeaf(ans);
        return ans;
    }

    private void isLeaf(List<TreeNode> ans) {
        User user = MySession.getUser();
        int userId = user == null ? 0 : user.getId();
        for (TreeNode cur : ans) {
            List<TreeNode> children = treeService.listChildren(cur, userId);
            if (children == null || children.size() == 0) {
                cur.setLeaf(true);
            } else {
                cur.setLeaf(false);
            }
        }
    }

    @RequestMapping("categories.action")
    @ResponseBody
    public List<TreeNode> categories() {
        return treeService.listCategories();
    }
}
