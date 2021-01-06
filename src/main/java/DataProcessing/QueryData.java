package DataProcessing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryData {
    // 查询城市疫情数据并返回一个JSONArray
    public static JSONArray queryCityData(String provinceName) {
        try (Connection conn = ConnectMysql.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * from provinces_covid " +
                     "INNER JOIN countries_covid c ON provinces_covid.country_id = c.id " +
                     "where provinces_covid.province_name = ?")) {
            ps.setObject(1, provinceName);
            try (ResultSet rs = ps.executeQuery()) {
                JSONArray provinces = new JSONArray();
                while (rs.next()) {
                    JSONObject province = new JSONObject(true);
                    province.put("countryName", rs.getString("country_name"));
                    province.put("countryConfirmed", rs.getInt("c.confirmed"));
                    province.put("countryRecovered", rs.getInt("c.recovered"));
                    province.put("countryDeaths", rs.getInt("c.deaths"));
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

    // 查询国家疫情数据并返回一个JSONArray
    public static JSONArray queryCountryData(String countryName) {
        try (Connection conn = ConnectMysql.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * from countries_covid " +
                     "where country_name = ?")) {
            ps.setObject(1, countryName);
            try (ResultSet rs = ps.executeQuery()) {
                JSONArray countries = new JSONArray();
                while (rs.next()) {
                    JSONObject country = new JSONObject(true);
                    country.put("countryName", rs.getString("country_name"));
                    country.put("confirmed", rs.getInt("confirmed"));
                    country.put("recovered", rs.getInt("recovered"));
                    country.put("deaths", rs.getInt("deaths"));
                    country.put("population", rs.getInt("population"));
                    country.put("sq_km_area", rs.getInt("sq_km_area"));
                    country.put("life_expectancy", rs.getString("life_expectancy"));
                    country.put("elevation_in_meters", rs.getString("elevation_in_meters"));
                    country.put("continent", rs.getString("continent"));
                    country.put("abbreviation", rs.getString("abbreviation"));
                    country.put("location", rs.getString("location"));
                    country.put("iso", rs.getInt("iso"));
                    country.put("capital_city", rs.getString("capital_city"));
                    countries.add(country);
                }
                return countries;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
