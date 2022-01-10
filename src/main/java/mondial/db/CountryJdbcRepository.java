package mondial.db;

import java.sql.*;
import java.util.Locale;

public class CountryJdbcRepository implements CountryRepository {

    //wäre schöner in einer Konfigurationsdatei
    private String url = "jdbc:postgresql://45.79.249.134:5432/postgres";

    @Override
    public Country findCountryByCode(String code) {

        Connection con = this.getConnection();

        try {

            PreparedStatement stmt = con.prepareStatement(
                    "SELECT name, code, capital, province, area, population FROM country WHERE code = ?");
            stmt.setString(1, code);

            ResultSet rSet = stmt.executeQuery();

            if (rSet.next()) {

                return new Country(rSet.getString("name"),
                        rSet.getString("code"),
                        rSet.getString("capital"),
                        rSet.getString("province"),
                        rSet.getInt("area"),
                        rSet.getInt("population"));
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Country[] getCountriesLike(String name) {

        Connection con = this.getConnection();

        try {

            PreparedStatement stmt = con.prepareStatement(
                    "SELECT name, code, capital, province, area, population FROM country WHERE lower(name) Like ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, "%" +  name.toLowerCase() + "%");

            ResultSet rSet = stmt.executeQuery();

            int rowcount = 0;
            if (rSet.last()) {
                rowcount = rSet.getRow();
                rSet.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            Country[] countries = new Country[rowcount];

            int count = 0;

            while (rSet.next()) {

                countries[count] = new Country(rSet.getString("name"),
                        rSet.getString("code"),
                        rSet.getString("capital"),
                        rSet.getString("province"),
                        rSet.getInt("area"),
                        rSet.getInt("population"));

                count++;
            }

            stmt.close();
            con.close();

            return countries;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    private Connection getConnection() {
        try {
            return DriverManager.getConnection(this.url, "postgres", "passwordJonas22");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
