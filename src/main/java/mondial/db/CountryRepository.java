package mondial.db;

public interface CountryRepository {
    public Country findCountryByCode (String code ) ;
    public Country [] getCountriesLike ( String name ) ;
}
