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
import com.fetch.consumeAPI.service.RestTemplateService;

@RestController
@RequestMapping("/resttemplate")
public class RestTemplateController {

    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping("/{path}")
    public List<Todo> getPosts(@PathVariable("path") String path) {
        return restTemplateService.fetchAll(path);
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

        Todo todo = restTemplateService.getTodoById(Integer.parseInt(id));

        return ResponseEntity.ok(todo);

    }

}
