package com.fetch.consumeAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.consumeAPI.customexception.TodoNotFoundException;
import com.fetch.consumeAPI.entity.Todo;
import com.fetch.consumeAPI.service.RestClientService;

@RestController
@RequestMapping("/restclient")
public class RestClientController {
    @Autowired
    private RestClientService restService;

    @GetMapping("/{path}")
    public List<Todo> getPosts(@PathVariable("path") String path) {
        return restService.fetch(path);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> fetchTodoById(@PathVariable("id") String id) {

        try {
            Integer i = Integer.parseInt(id);
            System.err.println(i);

        } catch (Exception e) {
            throw new TodoNotFoundException("Id MisMatch Exception", "Please check the id format its not correct",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Todo todo = restService.getPostById(Integer.parseInt(id));
        if (todo != null) {
            return ResponseEntity.ok(todo);
        } else {
            throw new TodoNotFoundException("Todo not found with id " + id,
                    "Please enter a valid Todo id. It does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }

}
