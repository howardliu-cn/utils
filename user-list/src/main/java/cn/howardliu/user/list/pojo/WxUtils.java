package cn.howardliu.user.list.pojo;

import cn.howardliu.http.client.simple.SimpleHttpRequester;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 微信接口公共类，获取access_token等。
 *
 * @author liuxh
 */
public final class WxUtils {
    private final static Logger logger = LoggerFactory.getLogger(WxUtils.class);

    private WxUtils() {
        throw new AssertionError("No com.nogemasa.weixin.common.util.WxUtils.WxUtils instances for you!");
    }

    /**
     * 获取access_token的接口地址（GET）
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return accessToken 授权access_token
     * 董瑞龙
     */
    public static AccessToken getAccessToken(String appid, String appsecret) throws IOException, URISyntaxException {
        AccessToken accessToken = null;
        String requestUrl = WxUrls.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = JSONObject.fromObject(SimpleHttpRequester.getHttpRequester().get(requestUrl));
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (Exception e) {
                accessToken = null;
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"), e);
            }
        } else {
            logger.error("获取AccessToken失败，请求url为：{}！", requestUrl);
        }
        return accessToken;
    }


    /**
     * 获取关注用户OpenId列表
     *
     * @param accessToken 授权access_token
     * @return 用户OpenId列表
     */
    public static Set<String> getUserOpenIDs(String accessToken) throws IOException, URISyntaxException {
        return getUserOpenIDs(accessToken, null);
    }

    /**
     * 获取关注用户OpenId列表
     *
     * @param accessToken 授权access_token
     * @param nextOpenId  第一个用户的OpenId
     * @return 用户OpenId列表
     */
    public static Set<String> getUserOpenIDs(String accessToken, String nextOpenId)
            throws IOException, URISyntaxException {
        Set<String> userOpenIDS = new HashSet<>();
        String requestUrl;
        if (nextOpenId == null) {
            requestUrl = WxUrls.ursers_url.replace("ACCESS_TOKEN", accessToken);
        } else {
            requestUrl = WxUrls.ursers_url_paged.replace("ACCESS_TOKEN", accessToken)
                    .replace("NEXT_OPENID", nextOpenId);
        }
        JSONObject jsonObject = JSONObject.fromObject(SimpleHttpRequester.getHttpRequester().get(requestUrl));
        if (null != jsonObject) {
            try {
                logger.debug(jsonObject.toString());
                long total = jsonObject.getLong("total");
                long count = jsonObject.getLong("count");
                if (count > 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray openIDs = data.getJSONArray("openid");
                    for (int i = 0; i < openIDs.size(); i++) {
                        userOpenIDS.add(openIDs.getString(i));
                    }
                    String next_openid = jsonObject.getString("next_openid");
                    if (total > 1000 && count == 1000) {
                        getUserOpenIDs(accessToken, next_openid);
                    }
                }
            } catch (Exception e) {
                logger.error("获取用户列表 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"), e);
            }
        } else {
            logger.error("获取用户列表{}失败！", requestUrl);
        }
        return userOpenIDS;
    }

    /**
     * 批量获取用户信息
     *
     * @param accessToken 授权令牌
     * @param openIds     用户opendId列表
     * @return 用户信息列表
     */
    public static List<MemberInfoPojo> getUserInfoBatch(String accessToken, Set<String> openIds)
            throws IOException, URISyntaxException {
        List<MemberInfoPojo> memberInfoList = new ArrayList<>();
        List<String> openIdList = new ArrayList<>(openIds);
        int size = (openIdList.size() + 100 - 1) / 100;
        for (int i = 0; i < size; i++) {
            List<String> subOpenIdList = openIdList
                    .subList(i * 100, ((i + 1) * 100 > openIdList.size()) ? openIdList.size() : (i + 1) * 100);
            JSONObject json = new JSONObject();
            JSONArray memberList = new JSONArray();
            for (String openId : subOpenIdList) {
                JSONObject j = new JSONObject();
                j.put("openid", openId);
                j.put("lang", "zh-CN");
                memberList.add(j);
            }
            json.put("user_list", memberList);
            String requestUrl = WxUrls.user_info_batch_url.replace("ACCESS_TOKEN", accessToken);
            logger.info("批量获取用户信息，请求地址{}，请求参数{}。", requestUrl, json.toString());
            JSONObject result = JSONObject
                    .fromObject(SimpleHttpRequester.getHttpRequester().post(requestUrl, json.toString()));
            JSONArray userInfoArray = result.getJSONArray("user_info_list");
            for (int j = 0; j < userInfoArray.size(); j++) {
                JSONObject memberInfo = userInfoArray.getJSONObject(j);
                MemberInfoPojo member = (MemberInfoPojo) JSONObject.toBean(memberInfo, MemberInfoPojo.class);
                memberInfoList.add(member);
            }
        }
        return memberInfoList;
    }
}
