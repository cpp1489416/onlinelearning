package cpp.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cpp.common.Page;
import cpp.user.mapper.UserCollectionMapper;
import cpp.user.mapper.UserCourseContentMapper;
import cpp.user.po.UserCollection;
import cpp.user.po.UserCourseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectionService {
    @Autowired
    UserCollectionMapper userCollectionMapper;

    public void add(UserCollection collection) {
        userCollectionMapper.add(collection);
    }

    public UserCollection getByUserIdAndCourseId(UserCollection collection) {
        return userCollectionMapper.getByUserIdAndCourseId(collection);
    }

    public void removeByUserIdAndCourseId(UserCollection collection) {
        userCollectionMapper.removeByUserIdAndCourseId(collection);
    }

    public List<UserCollection> listCourseCollectionsByUserId(int userId) {
        return userCollectionMapper.listCourseCollectionsByUserId(userId);
    }

    public Page<UserCollection> listCourseCollectionsPageByUserId(int id, Page<UserCollection> page) {
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        List<UserCollection> items = userCollectionMapper.listCourseCollectionsByUserId(id);
        PageInfo<UserCollection> pageInfo = new PageInfo<>(items);
        page.setItemsTotalCount((int)pageInfo.getTotal());
        page.setItems(items);
        return page;
    }
}
