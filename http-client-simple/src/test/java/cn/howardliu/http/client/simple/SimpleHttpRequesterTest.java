package cn.howardliu.http.client.simple;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertNotNull;

public class SimpleHttpRequesterTest {
    @Test
    public void testGet() throws Exception {
        assertNotNull(SimpleHttpRequester.getHttpRequester().get("http://www.baidu.com"));
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("wd", "faljfdlasjflkjasdlfjlasjflkajflkdajslkfjsa");
        assertNotNull(SimpleHttpRequester.getHttpRequester().get("http://www.baidu.com/s", paramsMap));
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("wd", "faljfdlasjflkjasdlfjlasjflkajflkdajslkfjsa"));
        assertNotNull(SimpleHttpRequester.getHttpRequester().get("http://www.baidu.com/s", paramsList));
    }

    @Test
    public void testPost() throws Exception {
        assertNotNull(SimpleHttpRequester.getHttpRequester()
                .post("http://10.6.2.48:8042/pcm-inner-sdc/organization/selectShop.htm", "{\"itemCode\":40000100}"));
    }

    @Test
    public void testDelete() throws Exception {
        String url = "http://localhost:8983/solr/solr-exercise-time_relica/schema/analysis/synonyms/managedSynonym/";
        JSONObject json = JSONObject.fromObject(
                "{\"responseHeader\":{\"status\":0,\"QTime\":1},\"synonymMappings\":{\"initArgs\":{\"ignoreCase\":false},\"initializedOn\":\"2015-10-29T04:48:12.156Z\",\"updatedSinceInit\":\"2015-10-29T05:45:37.158Z\",\"managedMap\":{\"aaabar\":[\"aaabar\",\"aaafoo\"],\"aaafoo\":[\"aaabar\",\"aaafoo\"],\"adidas\":[\"adidas\",\"阿迪\",\"阿迪达斯\"],\"ascis\":[\"ascis\",\"asics\",\"亚瑟士\"],\"asics\":[\"ascis\",\"asics\",\"亚瑟士\"],\"baraaa\":[\"baraaa\",\"bazaaa\",\"fooaaa\"],\"bazaaa\":[\"baraaa\",\"bazaaa\",\"fooaaa\"],\"bbbbar\":[\"bbbbar\",\"bbbfoo\"],\"bbbfoo\":[\"bbbbar\",\"bbbfoo\",\"bbbfoo\\u0000bbbbar\"],\"bbbfoo\\u0000bbbbar\":[\"bbbfoo\",\"bbbfoo\\u0000bbbbar\"],\"burnetie\":[\"burnetie\",\"伯内谛\"],\"cccbar\":[\"cccbar\",\"cccbaz\",\"cccfoo\"],\"cccbar\\u0000cccbaz\":[\"cccbar\\u0000cccbaz\",\"cccfoo\"],\"cccbaz\":[\"cccbar\",\"cccbaz\",\"cccfoo\"],\"cccfoo\":[\"cccbar\",\"cccbar\\u0000cccbaz\",\"cccbaz\",\"cccfoo\"],\"converse\":[\"converse\",\"匡威\"],\"ecco\":[\"ecco\",\"爱步\"],\"fooaaa\":[\"baraaa\",\"bazaaa\",\"fooaaa\"],\"gib\":[\"GB\",\"gib\",\"gigabyte\",\"gigabytes\"],\"gigabyte\":[\"GB\",\"gib\",\"gigabyte\",\"gigabytes\"],\"gigabytes\":[\"GB\",\"gib\",\"gigabyte\",\"gigabytes\"],\"macwish\":[\"macwish\",\"麦科威\",\"麦科威诗\"],\"megabyte\":[\"MB\",\"megabyte\",\"megabytes\",\"mib\"],\"megabytes\":[\"MB\",\"megabyte\",\"megabytes\",\"mib\"],\"mib\":[\"MB\",\"megabyte\",\"megabytes\",\"mib\"],\"nike\":[\"nike\",\"耐克\"],\"pixima\":[\"pixima\",\"pixma\"],\"pixma\":[\"pixima\",\"pixma\"],\"t-shirt\":[\"T恤\",\"t-shirt\",\"tshirt\"],\"tshirt\":[\"T恤\",\"t-shirt\",\"tshirt\"],\"watchjady\":[\"watchjady\",\"维界\"],\"上衣\":[\"上衣\",\"上装\"],\"上装\":[\"上衣\",\"上装\"],\"亚瑟士\":[\"ascis\",\"asics\",\"亚瑟士\"],\"伯内谛\":[\"burnetie\",\"伯内谛\"],\"匡威\":[\"converse\",\"匡威\"],\"同义词\":[\"同义词\",\"近义词\"],\"女\":[\"女\",\"女士\",\"女式\"],\"女士\":[\"女\",\"女士\",\"女式\"],\"女式\":[\"女\",\"女士\",\"女式\"],\"拉杆箱\":[\"拉杆箱\",\"行李箱\"],\"文胸\":[\"文胸\",\"胸罩\"],\"泳衣\":[\"泳衣\",\"泳装\"],\"泳装\":[\"泳衣\",\"泳装\"],\"测试1\":[\"测试2\"],\"测试2\":[\"测试1\"],\"添柏岚\":[\"Timberland\",\"添柏岚\"],\"爱步\":[\"ecco\",\"爱步\"],\"男\":[\"男\",\"男士\",\"男式\"],\"男士\":[\"男\",\"男士\",\"男式\"],\"男式\":[\"男\",\"男士\",\"男式\"],\"维界\":[\"watchjady\",\"维界\"],\"耐克\":[\"nike\",\"耐克\"],\"胸罩\":[\"文胸\",\"胸罩\"],\"行李箱\":[\"拉杆箱\",\"行李箱\"],\"衬衣\":[\"衬衣\",\"衬衫\"],\"衬衫\":[\"衬衣\",\"衬衫\"],\"裤袜\":[\"裤袜\",\"连裤袜\"],\"西服\":[\"西服\",\"西装\"],\"西装\":[\"西服\",\"西装\"],\"近义词\":[\"同义词\",\"近义词\"],\"连裤袜\":[\"裤袜\",\"连裤袜\"],\"阿迪\":[\"adidas\",\"阿迪\",\"阿迪达斯\"],\"阿迪达斯\":[\"adidas\",\"阿迪\",\"阿迪达斯\"],\"非类\":[\"FEELEI-KISS\",\"非类\"],\"麦科威\":[\"macwish\",\"麦科威\",\"麦科威诗\"],\"麦科威诗\":[\"macwish\",\"麦科威\",\"麦科威诗\"]}}}");
        JSONObject synonymMappings = json.getJSONObject("synonymMappings");
        JSONObject managedMap = synonymMappings.getJSONObject("managedMap");
        Iterator iterator = managedMap.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String rtnText = SimpleHttpRequester.getHttpRequester().delete(url + key);
            System.out.println(rtnText);
        }
    }
}