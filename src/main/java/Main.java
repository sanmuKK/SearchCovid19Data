import DataProcessing.AnalyzeJson;
import DataProcessing.QueryData;

import com.alibaba.fastjson.JSONArray;

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
                System.out.println("更新成功");
            } else if (choice == 2) {
                input.nextLine();
                System.out.println("请输入要查询的城市名称:");
                String province = input.nextLine();
                JSONArray result = QueryData.queryData(province);
                if (result != null && !result.isEmpty()) {
                    System.out.println("查询成功");
                    System.out.println(result);
                } else
                    System.out.println("查询失败");
            }
        }
    }
}
