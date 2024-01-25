package com.fetch.consumeAPI.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.fetch.consumeAPI.customexception.TodoNotFoundException;
import com.fetch.consumeAPI.entity.Todo;

@Service
public class RestClientService {

    RestClient client;

    public RestClientService() {
        client = RestClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    public List<Todo> fetch(String path) {
        List<Todo> list = null;

        try {
            ParameterizedTypeReference<List<Todo>> responseType = new ParameterizedTypeReference<List<Todo>>() {
            };
            list = client.get().uri("/" + path).retrieve().toEntity(responseType).getBody();

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new TodoNotFoundException("Wrong Path ", "Please Check the url Before Typing",
                        HttpStatus.BAD_REQUEST);
            }
        }
        return list;

    }

    public Todo getPostById(Integer id) {
        try {
            Todo t = client.get().uri("/todos/" + id).retrieve().body(Todo.class);
            return t;

        } catch (HttpClientErrorException.NotFound ex) {

            throw new TodoNotFoundException("Todo not found with id " + id,
                    "Please enter a valid Todo id. It does not exist in the database", HttpStatus.NOT_FOUND);
        }

    }

}
