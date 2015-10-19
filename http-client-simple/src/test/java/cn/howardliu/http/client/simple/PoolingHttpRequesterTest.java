package cn.howardliu.http.client.simple;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class PoolingHttpRequesterTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testGet() throws Exception {
        logger.debug(PoolingHttpRequester.getHttpRequester().get("http://www.baidu.com"));
        logger.debug(PoolingHttpRequester.getHttpRequester().get("http://www.baidu.com"));
    }

    @Test
    public void testGetHttpClient() throws Exception {
        HttpRequester poolingHttpRequester = PoolingHttpRequester.getHttpRequester();
        int count = 2000;
        Map<Integer, Integer> map = new HashMap<>(count, 1);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                CloseableHttpClient client = poolingHttpRequester.getHttpClient();
                if (map.containsKey(client.hashCode())) {
                    map.put(client.hashCode(), map.get(client.hashCode()) + 1);
                } else {
                    map.put(client.hashCode(), 1);
                }
                logger.debug("生成的http client为：" + client.toString());
                try {
                    poolingHttpRequester.get("http://www.baidu.com");
                } catch (URISyntaxException | IOException ignored) {
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        for (Map.Entry entry : map.entrySet()) {
            logger.debug(entry.getKey() + "=" + entry.getValue());
        }
    }
}