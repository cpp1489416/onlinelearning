package cpp.user.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.user.po.User;
import cpp.user.po.UserFollow;
import cpp.user.po.UserFollowStudyRecord;
import cpp.user.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("follow")
public class UserFollowController {
    @Autowired
    UserFollowService userFollowService;

    @RequestMapping("swapFollow.action")
    @ResponseBody
    public OutInfo swapFollow(UserFollow follow) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        follow.setUserId(user.getId());
        UserFollow oldFollow = userFollowService.getByUserIdAndFollowUserId(follow);
        if (oldFollow == null) {
            userFollowService.add(follow);
            return OutInfo.yes();
        } else {
            userFollowService.removeByUserIdAndFollowUserId(follow);
            return OutInfo.no();
        }
    }

    @RequestMapping("isFollowed.action")
    @ResponseBody
    public OutInfo isFollowed(UserFollow follow) {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        }
        follow.setUserId(user.getId());
        UserFollow oldFollow = userFollowService.getByUserIdAndFollowUserId(follow);
        if (oldFollow == null) {
            return OutInfo.no();
        } else {
            return OutInfo.yes();
        }
    }

    @RequestMapping("studyRecords.action")
    @ResponseBody
    public List<UserFollowStudyRecord> listUserFollowRecord(UserFollow follow) {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }

        return userFollowService.listUserFollowStudyRecord(user.getId());
    }

    @RequestMapping("studyRecordsPage.action")
    @ResponseBody
    public Page<UserFollowStudyRecord> listUserFollowRecordPage(Page<UserFollowStudyRecord> page) {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }

        return userFollowService.listUserFollowRecordPage(user, page);
    }

    @RequestMapping("followingsPage.action")
    @ResponseBody
    public Page<User> followersPage(Page<User> page) {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }
        return userFollowService.listFollowings(user.getId(), page);
    }

}
