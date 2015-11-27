package cn.howardliu.user.list.controller;

import cn.howardliu.user.list.pojo.AccessToken;
import cn.howardliu.user.list.pojo.MemberInfoPojo;
import cn.howardliu.user.list.pojo.WxUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <br/>create at 15-11-27
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserListController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMapping("/list")
    @SuppressWarnings("unchecked")
    public String getUserList(String appid, String appSecret,
            @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit, Model model,
            HttpSession session) {
        if (StringUtils.isBlank(appid) || StringUtils.isBlank(appSecret)) {
            model.addAttribute("list", new ArrayList<>());
            return "member-list";
        }
        if (start == null || start < 0) {
            start = 0;
        }
        if (limit == null || limit < 0) {
            limit = 50;
        }
        List<MemberInfoPojo> list = (List<MemberInfoPojo>) session.getAttribute("userList");
        if (list == null) {
            list = new ArrayList<>();
            try {
                AccessToken accessToken = WxUtils.getAccessToken(appid, appSecret);
                Set<String> users = WxUtils.getUserOpenIDs(accessToken.getToken());
                list = WxUtils.getUserInfoBatch(accessToken.getToken(), users);
            } catch (Exception e) {
                logger.error("获取错误", e);
            }
            session.setAttribute("userList", list);
        }
        model.addAttribute("list", list.subList(start, list.size() < (start + limit) ? list.size() : (start + limit)));
        if (start != 0) {
            model.addAttribute("preUrl", "appid=" + appid + "&appSecret=" + appSecret +
                    "&start=" + ((start - limit) < 0 ? 0 : (start - limit)) + "&limit=" + limit);
        } else {
            model.addAttribute("preUrl", "#");
        }
        if ((start + limit) < list.size()) {
            model.addAttribute("postUrl", "appid=" + appid + "&appSecret=" + appSecret +
                    "&start=" + (start + limit) + "&limit=" + limit);
        } else {
            model.addAttribute("postUrl", "#");
        }
        return "member-list";
    }
}
