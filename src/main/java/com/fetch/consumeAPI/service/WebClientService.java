package com.fetch.consumeAPI.service;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fetch.consumeAPI.customexception.TodoNotFoundException;
import com.fetch.consumeAPI.entity.Todo;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private final WebClient webClient;

    public WebClientService() {
        webClient = WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    public List<Todo> fetch(String path) {
        try {
            ParameterizedTypeReference<List<Todo>> responseType = new ParameterizedTypeReference<List<Todo>>() {
            };

            return webClient.get()
                    .uri("/" + path)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new TodoNotFoundException("Wrong Path ", "Please Check the URL Before Typing",
                        HttpStatus.BAD_REQUEST);
            }
            throw new RuntimeException("Error fetching data from the API", ex);
        }
    }

    public List<Todo> getTodoById(Integer id) {
        return webClient.get()
                .uri("/todos/{id}", id)
                .retrieve()
                .bodyToFlux(Todo.class)
                .collectList()
                .block();
                
    }

    public List<Todo> getTodoByIdAndUserId(Integer id, Integer userId) {
        try {
            List<Todo> todoList = webClient.get()
                    .uri("/todos/{id}?userId={userId}", id, userId)
                    .retrieve()
                    .bodyToFlux(Todo.class)
                    .collectList()
                    .block();
    
            if (todoList == null || todoList.isEmpty()) {
                throw new TodoNotFoundException("Todo not found with id " + id + " and userId " + userId,
                        "Please enter a valid combination of Todo id and userId. No Todos found for the given id and userId.",
                        HttpStatus.NOT_FOUND);
            }
    
            return todoList;
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new TodoNotFoundException("Todo not found with id " + id + " and userId " + userId,
                        "Please enter a valid combination of Todo id and userId. No Todos found for the given id and userId.",
                        HttpStatus.NOT_FOUND);
            }
            throw ex;
        }
    }
    

    public List<Todo> getTodosByStatus(boolean completed) {
        if (completed != true && completed != false)
            throw new TodoNotFoundException("Status can only be true or false",
                    "Please use true of false to get desired result", HttpStatus.BAD_REQUEST);
        List<Todo> list = null;
        try {
            list = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/todos").queryParam("completed", completed).build())
                    .retrieve()
                    .bodyToFlux(Todo.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {

                throw new TodoNotFoundException("Status can only be true or false",
                        "Please use true of false to get desired result", HttpStatus.BAD_REQUEST);
            }

        }
        return list;
    }

    public List<Todo> getTodoByUserId(Integer userId) {
        try {
            List<Todo> todoList = webClient.get()
                    .uri("/todos?userId={userId}", userId)
                    .retrieve()
                    .bodyToFlux(Todo.class)
                    .collectList()
                    .block();
    
            if (todoList == null || todoList.isEmpty()) {
                throw new TodoNotFoundException("Todo not found with userId " + userId,
                        "Please enter a valid userId. No Todos found for the given userId.",
                        HttpStatus.NOT_FOUND);
            }
    
            return todoList;
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new TodoNotFoundException("Todo not found with userId " + userId,
                        "Please enter a valid userId. No Todos found for the given userId.",
                        HttpStatus.NOT_FOUND);
            }
            throw ex;
        }
    }
    

}
