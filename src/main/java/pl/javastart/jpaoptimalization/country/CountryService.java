package pl.javastart.jpaoptimalization.country;

import org.springframework.stereotype.Service;
import pl.javastart.jpaoptimalization.countrylanguage.CountryLanguage;

import java.util.*;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> countriesSortedAsc() {
        return countryRepository.findAllByCountryNameAscWithLanguages();
    }

    public List<Country> countriesSortedAscByCapital() {
        return countryRepository.findAllByCountryNameAscWithTheBiggestCity();
    }

    public Map<String, List<String>> getLanguagesAndCountries(List<CountryLanguage> countryLanguages) {
        List<Country> countries = countriesSortedAsc();
        Map<String, List<String>> languagesAndCountries = new TreeMap<>(Comparator.comparing(String::new));

        for (CountryLanguage countryLanguage : countryLanguages) {
            if (!languagesAndCountries.containsKey(countryLanguage.getLanguage())) {
                languagesAndCountries.put(countryLanguage.getLanguage(), new ArrayList<>());
            }
        }
        for (Country country : countries) {
            for (CountryLanguage language : country.getLanguages()) {
                if (languagesAndCountries.containsKey(language.getLanguage())) {
                    languagesAndCountries.get(language.getLanguage()).add(country.getName());
                }
            }
        }
        return languagesAndCountries;
    }
}