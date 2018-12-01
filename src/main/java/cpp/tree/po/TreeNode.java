package cpp.tree.po;

import java.util.List;

public class TreeNode {
    public int id;
    public List<TreeNode> children;
    public String name;
    public String type; // category, course, content
    public boolean isLeaf;
    public int depth;
    public String description;
    public boolean isObtained = true; // 是否获得课程，以及课程中的章节

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isObtained() {
        return isObtained;
    }

    public void setObtained(boolean affordable) {
        isObtained = affordable;
    }
}
