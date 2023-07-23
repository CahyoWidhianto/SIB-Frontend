package id.co.mii.clientapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Region;

@Service
public class RegionService {

        @Autowired
        private RestTemplate restTemplate;

        @Value("${server.base.url}/region")
        private String url;

        public List<Region> getAll() {
                // HttpHeaders httpHeaders = new HttpHeaders();
                // httpHeaders.set("Authorization", "Basic dXNlcjp1c2Vy");
                return restTemplate.exchange(url, HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Region>>() {
                                }).getBody();
        }

        public Region getById(Integer id) {
                return restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                                new ParameterizedTypeReference<Region>() {
                                }).getBody();
        }

        public Region create(Region region) {
                return restTemplate
                                .exchange(
                                                url,
                                                HttpMethod.POST,
                                                new HttpEntity(region),
                                                new ParameterizedTypeReference<Region>() {
                                                })
                                .getBody();
        }

        public Region update(Integer id, Region region) {
                return restTemplate
                                .exchange(
                                                url.concat("/" + id),
                                                HttpMethod.PUT,
                                                new HttpEntity(region),
                                                new ParameterizedTypeReference<Region>() {
                                                })
                                .getBody();
        }

        public Region delete(Integer id) {
                return restTemplate
                                .exchange(
                                                url.concat("/" + id),
                                                HttpMethod.DELETE,
                                                null,
                                                new ParameterizedTypeReference<Region>() {
                                                })
                                .getBody();
        }
}
