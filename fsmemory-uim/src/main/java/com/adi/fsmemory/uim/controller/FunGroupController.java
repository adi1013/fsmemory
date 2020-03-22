package com.adi.fsmemory.uim.controller;

import com.adi.fsmemory.entity.uim.ActiveUser;
import com.adi.fsmemory.entity.uim.FunGroup;
import com.adi.fsmemory.entity.uim.FunMemberGroup;
import com.adi.fsmemory.entity.uim.User;
import com.adi.fsmemory.restful.RestResultObj;
import com.adi.fsmemory.restful.RestResultType;
import com.adi.fsmemory.uim.constant.FunUserJoinGroupEnum;
import com.adi.fsmemory.uim.service.FunGroupService;
import com.adi.fsmemory.uim.service.FunUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fun/group")
public class FunGroupController {

    @Autowired
    private FunGroupService groupService;

    @Autowired
    private FunUserRoleService userRoleService;

    /**
     * 创建组织
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "create",method = {RequestMethod.POST,RequestMethod.PUT})
    public RestResultObj create(@RequestBody FunGroup group){
        groupService.addGroup(group, this.getOnlineUser());
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 申请加入组织
     */
    @RequestMapping(value = "apply",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj applyJoin(@RequestBody FunGroup group) {
        groupService.joinApply(group, this.getOnlineUser());
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 邀请用户加入组织
     * @param params
     * @return
     */
    @RequestMapping(value = "invite",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj inviteJoin(@RequestBody Map<String, Object> params) {//Map
        FunGroup group = new FunGroup();
        User member = new User();
        group.setSearchId(params.get("searchId").toString());
        member.setUserName(params.get("userName").toString());
        groupService.joinInvite(member, group, this.getOnlineUser());
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 管理员通过加入组织申请
     * @param params   参数应包含searchId，userName
     */
    @RequestMapping(value = "apply/accept",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj acceptApply(@RequestBody Map<String, Object> params){
        FunGroup group = new FunGroup();
        User applyUser = new User();
        group.setSearchId(params.get("searchId").toString());
        applyUser.setUserName(params.get("userName").toString());
        groupService.joinAccept(group, applyUser);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }
    /**
     * 用户通过加入组织邀请
     */
    @RequestMapping(value = "invite/accept",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj acceptInvite(@RequestBody FunGroup group) {
        groupService.joinAccept(group, this.getOnlineUser());
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 用户获取到被邀请的组织信息
     */
    @RequestMapping(value = "list/invite",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj listInvite(){
        List<FunMemberGroup> memberGroups = groupService.queryInviteGroup(this.getOnlineUser().getUserId());
        return RestResultType.SUCCESS.toRestfulResult(memberGroups);
    }


    /**
     * 管理员查找到申请加入的信息
     */
    @RequestMapping(value = "list/apply",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public RestResultObj listApply(@RequestBody FunGroup group) {
        List<User> users = groupService.queryJoinApply(group.getSearchId(), FunUserJoinGroupEnum.JOIN_APPLY);
        return RestResultType.SUCCESS.toRestfulResult(users);
    }


    /**
     * 删除成员 或者 用户退出组织
     */
    @RequestMapping(value = "remove/member/{searchId}/{userName}")
    public RestResultObj removeMember(@PathVariable("searchId") String searchId,
                                      @PathVariable("userName") String userName) {
        userRoleService.removeMember(userName,searchId);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 解散组织
     */
    @RequestMapping(value = "disband/{searchId}")
    public RestResultObj disbandGroup(@PathVariable("searchId") String searchId) {
        groupService.delGroup(searchId);
        return RestResultType.SUCCESS.toRestfulResult(null);
    }

    /**
     * 查找用户组
     */




    /**
     * 获取此时的登录用户
     * @return
     */
    private User getOnlineUser() {
        Subject subject = SecurityUtils.getSubject();
        return ((ActiveUser)subject.getPrincipal()).getOnlineUser();
    }

}
