package id.co.mii.clientapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Country;
import lombok.AllArgsConstructor;

@Service
// @AllArgsConstructor
public class CountryService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/country")
    private String url;

    public List<Country> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Country>>() {
                }).getBody();
    }

    public Country getById(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                new ParameterizedTypeReference<Country>() {
                }).getBody();
    }

    public Country create(Country country) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(country),
                new ParameterizedTypeReference<Country>() {
                }).getBody();
    }

    public Country update(Integer id, Country country) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.PUT, new HttpEntity(country),
                new ParameterizedTypeReference<Country>() {
                }).getBody();
    }

    public Country delete(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Country>() {
                }).getBody();
    }
}
