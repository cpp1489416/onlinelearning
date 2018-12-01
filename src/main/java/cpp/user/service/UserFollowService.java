package cpp.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cpp.common.Page;
import cpp.user.mapper.UserFollowMapper;
import cpp.user.po.User;
import cpp.user.po.UserFollow;
import cpp.user.po.UserFollowStudyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFollowService {
    @Autowired
    UserFollowMapper userFollowMapper;

    public void add(UserFollow follow) {
        userFollowMapper.add(follow);
    }

    public void removeByUserIdAndFollowUserId(UserFollow follow){
        userFollowMapper.removeByUserIdAndFollowUserId(follow);
    }

    public UserFollow getByUserIdAndFollowUserId(UserFollow follow) {
        return userFollowMapper.getByUserIdAndFollowUserId(follow);
    }

    public List<UserFollowStudyRecord> listUserFollowStudyRecord(int userId) {
        return userFollowMapper.listUserFollowStudyRecord(userId);
    }

    public Page<UserFollowStudyRecord> listUserFollowRecordPage(User entity, Page<UserFollowStudyRecord> page) {
        int count = userFollowMapper.getUserFollowStudyRecordCount(entity.getId());
        page.setItemsTotalCount(count);
        page.setItems(userFollowMapper.listUserFollowStudyRecordPage(entity, page));
        return page;
    }

    public Page<User> listFollowings(int userId, Page<User> page) {
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        List<User> items = userFollowMapper.listFollowings(userId);
        PageInfo<User> pageInfo = new PageInfo<>(items);
        page.setItemsTotalCount((int)pageInfo.getTotal());
        page.setItems(items);
        return page;
    }
}
