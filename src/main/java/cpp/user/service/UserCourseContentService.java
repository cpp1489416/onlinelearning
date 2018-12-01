package cpp.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cpp.common.Page;
import cpp.user.mapper.UserCourseContentMapper;
import cpp.user.po.UserCourseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseContentService {
    @Autowired
    UserCourseContentMapper userCourseContentMapper;

    public UserCourseContent getLatest(UserCourseContent ucc) {
        return userCourseContentMapper.getLatest(ucc);
    }

    public UserCourseContent getByUserIdAndContentId(UserCourseContent ucc) {
        return userCourseContentMapper.getByUserIdAndContentId(ucc);
    }

    public void add(UserCourseContent ucc) {
        userCourseContentMapper.add(ucc);
    }

    public void modify(UserCourseContent ucc) {
        userCourseContentMapper.modify(ucc);
    }

    public List<UserCourseContent> listByUserId(int userId) {
        return userCourseContentMapper.listByUserId(userId);
    }

    public Page<UserCourseContent> listByUserIdPage(int userId, Page<UserCourseContent> page) {
        System.out.println(page);
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        List<UserCourseContent> items = userCourseContentMapper.listByUserId(userId);
        PageInfo<UserCourseContent> pageInfo = new PageInfo<>(items);
        page.setItemsTotalCount((int)pageInfo.getTotal());
        page.setItems(items);
        return page;
    }
}
