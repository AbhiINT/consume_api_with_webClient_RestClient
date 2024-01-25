package com.fetch.consumeAPI.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fetch.consumeAPI.customexception.TodoNotFoundException;
import com.fetch.consumeAPI.entity.Todo;

@Service
public class RestTemplateService {
    private final String baseUrl = "https://jsonplaceholder.typicode.com";
    private final RestTemplate restTemplate = new RestTemplate();

    public Todo getTodoById(Integer id) {
        String url = baseUrl + "/todos/{id}";
        ResponseEntity<Todo> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, Todo.class, id);
        } catch (Exception e) {
            throw new TodoNotFoundException("Todo not found with id " + id,
                    "Please enter a valid Todo id. It does not exist in the database", HttpStatus.NOT_FOUND);
        }
        return responseEntity.getBody();
    }

    public List<Todo> fetchAll(String path) {
        String url = baseUrl + "/" + path;
        ResponseEntity<List<Todo>> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Todo>>() {
                    });

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new TodoNotFoundException("Wrong Path ", "Please Check the url Before Typing",
                        HttpStatus.BAD_REQUEST);
            }
        }
        return responseEntity.getBody();
    }
}
