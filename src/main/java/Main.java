import DataProcessing.AnalyzeJson;
import DataProcessing.QueryData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String url = "https://covid-api.mmediagroup.fr/v1/cases";
        String[] countriesName = {"China", "US", "United Kingdom", "Japan"};// 传入一个列表使用同一个Response减少请求次数,
        Scanner input = new Scanner(System.in);                             // 也可改成使用Static代码块，但是更新数据不太方便
        int choice = 0;
        while (choice != 3) {
            System.out.println("更新请输入1,查询请输入2,结束请输入3:");
            choice = input.nextInt();
            if (choice == 1) {
                AnalyzeJson.saveData(url, countriesName);
                System.out.println("OK!");
            } else if (choice == 2) {
                input.nextLine();
                int choice2;
                System.out.println("查询国家请输入4,查询城市请输入5,返回请输入6:");
                choice2 = input.nextInt();
                input.nextLine();
                JSONArray result;
                if (choice2 == 4) {
                    System.out.println("请输入要查询的国家名称:");
                    String province = input.nextLine();
                    result = QueryData.queryCountryData(province);
                } else if (choice2 == 5) {
                    System.out.println("请输入要查询的城市名称:");
                    String province = input.nextLine();
                    result = QueryData.queryCityData(province);
                } else
                    continue;
                if (result != null && !result.isEmpty()) {
                    System.out.println("查询成功");
                    String prettyJsonResult = JSON.toJSONString(result, SerializerFeature.PrettyFormat,
                            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                    System.out.println(prettyJsonResult);
                } else
                    System.out.println("查询失败");
            }
        }
    }
}
