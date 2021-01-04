package DataProcessing;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


class Spider {
    static String getData(String uri) {
        // 创建HttpGet实例
        HttpGet get = new HttpGet(uri);

        // 使用HttpClient发送get请求，获得返回结果HttpResponse
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // (3) 读取返回结果
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            try (InputStream in = entity.getContent()) {
                return getResponse(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    // 解析Response
    private static String getResponse(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String responseLine = null;
        StringBuilder response = new StringBuilder();
        while (true) {
            try {
                if ((responseLine = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.append(responseLine);
        }
        return response.toString();
    }
}
