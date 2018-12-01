package cpp.course.service;


import cpp.course.mapper.CourseContentMapper;
import cpp.course.mapper.CourseMapper;
import cpp.course.po.Course;
import cpp.course.po.CourseContent;
import cpp.course.vo.CourseContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Service
public class CourseContentService {
    @Autowired
    CourseContentMapper courseContentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseService courseService;

    public List<CourseContentVo> listCourseContentsByCourseId(int courseId) {
        List<CourseContentVo> ans = new LinkedList<>();
        Map<Integer, CourseContentVo> map = new LinkedHashMap<>();
        List<CourseContent> contents = courseContentMapper.listCourseContentsByCourseId(courseId);
        for (int i = 0; i < contents.size(); ++i) {
            CourseContent content = contents.get(i);
            if (content.getParentId() == 0) {
                CourseContentVo vo = new CourseContentVo();
                BeanUtils.copyProperties(content, vo);
                map.put(content.getId(), vo);
            }
        }

        System.out.print(map);

        for (int i = 0; i < contents.size(); ++i) {
            CourseContent content = contents.get(i);
            if (content.getParentId() != 0) {
                map.get(content.getParentId()).getContents().add(content);
            }
        }

        for (CourseContentVo vo : map.values()) {
            vo.getContents().sort(new Comparator<CourseContent>() {
                @Override
                public int compare(CourseContent o1, CourseContent o2) {
                    return o1.getSort() - o2.getSort();
                }
            });
            ans.add(vo);
        }
        ans.sort(new Comparator<CourseContentVo>() {
            @Override
            public int compare(CourseContentVo o1, CourseContentVo o2) {
                return o1.getSort() - o2.getSort();
            }
        });

        return ans;
    }

    public CourseContent getById(int id) {
        return courseContentMapper.getById(id);
    }

    public boolean moveBefore(CourseContent content) {
        CourseContent preContent = courseContentMapper.getMaxSortBeforeContent(content);
        if (preContent == null) {
            return false;
        }
        int preSort = preContent.getSort();
        int nxtSort = content.getSort();
        preContent.setSort(nxtSort);
        content.setSort(preSort);
        courseContentMapper.modify(preContent);
        courseContentMapper.modify(content);
        return true;
    }

    public boolean moveAfter(CourseContent content) {
        CourseContent preContent = courseContentMapper.getMinSortAfterContent(content);
        if (preContent == null) {
            return false;
        }
        int preSort = preContent.getSort();
        int nxtSort = content.getSort();
        preContent.setSort(nxtSort);
        content.setSort(preSort);
        courseContentMapper.modify(preContent);
        courseContentMapper.modify(content);
        return true;
    }

    public void removeContent(CourseContent content) {
        if (content.getParentId() == 0) {
            int parentId = content.getId();
            courseContentMapper.removeSubContentsByParentId(parentId);
        }
        courseContentMapper.remove(content);
    }

    public void addContent(CourseContent content) {
        content.setSort(courseContentMapper.getMaxSortByCourseIdAndParentId(content) + 1);
        if (content.getText() == null) {
            content.setText("这门个小节刚创建，请添加课程内容。");
        }
        courseContentMapper.addContent(content);
    }

    public void modifyText(CourseContent content) {
        courseContentMapper.modifyText(content);
    }

    public void modifyName(CourseContent content) {
        courseContentMapper.modify(content);
    }

    public boolean isCourseContentObtained(int contentId, int userId) {
        CourseContent content = courseContentMapper.getById(contentId);
        if (content == null) {
            return false;
        }
        Course course = courseMapper.getCourse(content.getCourseId());
        if (course == null) {
            return false;
        }
        return courseService.isCourseObtained(course.getId(), userId);
    }
}
