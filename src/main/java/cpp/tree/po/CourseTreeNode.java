package cpp.tree.po;

public class CourseTreeNode extends TreeNode {
    public String nickname;
    public String headImageUrl;
    public int userId;
    public String getNickname() {
        return nickname;
    }
    public String level;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
