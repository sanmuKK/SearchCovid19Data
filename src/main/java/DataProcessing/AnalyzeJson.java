package DataProcessing;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;


public class AnalyzeJson {
    public static void saveData(String url, String[] countriesName) {
        String response = Spider.getData(url);
        JSONObject data = JSONObject.parseObject(response);
        for (String countryName : countriesName) {
            JSONObject countryData = data.getJSONObject(countryName);
            JSONObject countryAllData = countryData.getJSONObject("All");
            Connection conn = ConnectMysql.getConnection();
            try { // 事务管理
                conn.setAutoCommit(false);
                int countryId = saveDataAll(countryAllData); // 解析All部分数据并返回自增主键值
                countryData.remove("All");
                for (String key : countryData.keySet()) {
                    saveDataProvince(countryData.getJSONObject(key), key, countryId);// 解析其他部分数据
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static int saveDataAll(JSONObject data) {
        try (Connection conn = ConnectMysql.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                     "countries_covid(confirmed,recovered,deaths,country_name,population," +
                     "sq_km_area,life_expectancy,elevation_in_meters,continent," +
                     "abbreviation,location,iso,capital_city)" +
                     "VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, data.getIntValue("confirmed"));
            ps.setObject(2, data.getIntValue("recovered"));
            ps.setObject(3, data.getIntValue("deaths"));
            ps.setObject(4, data.getString("country"));
            ps.setObject(5, data.getIntValue("population"));
            ps.setObject(6, data.getIntValue("sq_km_area"));
            ps.setObject(7, data.getString("life_expectancy"));
            ps.setObject(8, data.getString("elevation_in_meters"));
            ps.setObject(9, data.getString("continent"));
            ps.setObject(10, data.getString("abbreviation"));
            ps.setObject(11, data.getString("location"));
            ps.setObject(12, data.getIntValue("iso"));
            ps.setObject(13, data.getString("capital_city"));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static void saveDataProvince(JSONObject data, String provinceName, int countryId) {
        try (Connection conn = ConnectMysql.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                     "provinces_covid(province_name,lat,long_data,confirmed,recovered," +
                     "deaths,updated,country_id)" +
                     "VALUE (?,?,?,?,?,?,?,?)")) {
            ps.setObject(1, provinceName);
            ps.setObject(2, data.getString("lat"));
            ps.setObject(3, data.getString("long"));
            ps.setObject(4, data.getIntValue("confirmed"));
            ps.setObject(5, data.getIntValue("recovered"));
            ps.setObject(6, data.getIntValue("deaths"));
            ps.setObject(7, data.getString("updated"));
            ps.setObject(8, countryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
