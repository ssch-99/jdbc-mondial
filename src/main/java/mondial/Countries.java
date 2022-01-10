package mondial;

import mondial.db.Country;
import mondial.db.CountryJdbcRepository;
import mondial.db.CountryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Countries {

  public static void main(String[] args) {
    Connection con;
    PreparedStatement stmt;
    ResultSet rSet;


    CountryRepository countryRepository = new CountryJdbcRepository();

    Country country = countryRepository.findCountryByCode("CH");

    System.out.println(country.getName() + "; " + country.getPopulation());

    System.out.println("--------SEARCH COUNTRIES BY NAME--------");

    Country[] countries = countryRepository.getCountriesLike("sw");

    for (Country c : countries){

      System.out.println(c.getName());

    }


   /* try {
      //	    String url = "jdbc:mariadb://localhost:3306/Mondial";
      String url = "jdbc:postgresql://45.79.249.134:5432/postgres";
      con = DriverManager.getConnection(url, "postgres", "passwordJonas22");
      stmt = con.prepareStatement(
          "SELECT code, name FROM country WHERE name like ?");
      stmt.setString(1, "S%");

      rSet = stmt.executeQuery();

			while (rSet.next()) {
				System.out.println(rSet.getString(1) + "\t" + rSet.getString(2));
			}

      stmt.close();
      con.close();
    } catch (SQLException e) {
      System.out.println("Fehler bei Tabellenabfrage" + e);
      return;
    }*/
  }
}	


