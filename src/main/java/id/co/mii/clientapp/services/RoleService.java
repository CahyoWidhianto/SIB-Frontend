package id.co.mii.clientapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Role;

@Service
public class RoleService {
    @Value("${server.base.url}/role")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Role> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Role>>() {
                }).getBody();
    }

    public Role getById(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null,
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }

    public Role create(Role role) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(role),
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }

    public Role update(Integer id, Role role) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.PUT, new HttpEntity(role),
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }

    public Role delete(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Role>() {
                }).getBody();
    }
}
