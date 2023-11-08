package pl.javastart.jpaoptimalization.countrylanguage;

import org.springframework.stereotype.Service;
import pl.javastart.jpaoptimalization.country.Country;
import pl.javastart.jpaoptimalization.country.CountryRepository;

import java.util.*;

@Service
public class CountryLanguageService {

    private final CountryLanguageRepository countryLanguageRepository;
    private final CountryRepository countryRepository;

    public CountryLanguageService(CountryLanguageRepository countryLanguageRepository, CountryRepository countryRepository) {
        this.countryLanguageRepository = countryLanguageRepository;
        this.countryRepository = countryRepository;
    }

    public Map<String, List<String>> getLanguagesAndCountries() {
        List<Country> countries = countryRepository.findAllByCountryNameAscWithLanguages();
        List<CountryLanguage> countryLanguages = countryLanguageRepository.findAll();
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
