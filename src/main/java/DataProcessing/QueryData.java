package DataProcessing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryData {
    // 查询城市疫情数据并返回一个JSONArray
    public static JSONArray queryData(String provinceName) {
        try (Connection conn = ConnectMysql.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * from provinces_covid " +
                     "where province_name = ?")) {
            ps.setObject(1, provinceName);
            try (ResultSet rs = ps.executeQuery()) {
                JSONArray provinces = new JSONArray();
                while (rs.next()) {
                    JSONObject province = new JSONObject();
                    province.put("provinceName", rs.getString("province_name"));
                    province.put("lat", rs.getString("lat"));
                    province.put("long", rs.getString("long_data"));
                    province.put("confirmed", rs.getInt("confirmed"));
                    province.put("recovered", rs.getInt("recovered"));
                    province.put("deaths", rs.getInt("deaths"));
                    province.put("updated", rs.getString("updated"));
                    province.put("countryId", rs.getInt("country_id"));
                    provinces.add(province);
                }
                return provinces;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
