package com.fetch.consumeAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.consumeAPI.customexception.TodoNotFoundException;
import com.fetch.consumeAPI.entity.Todo;
import com.fetch.consumeAPI.service.WebClientService;


@RestController
@RequestMapping("/webclient")
public class WebClientController {
   
    @Autowired
    private WebClientService webClientService;

     @GetMapping("/{path}")
    public List<Todo> getPosts(@PathVariable("path") String path) {
        return webClientService.fetch(path);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<List<Todo>> fetchTodoById(@PathVariable("id") String id) {

        try {
            Integer i = Integer.parseInt(id);
            System.err.println(i);

        } catch (Exception e) {
            throw new TodoNotFoundException("Id MisMatch Exception", "Please check the id format its not correct",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        List<Todo> todo = webClientService.getTodoById(Integer.parseInt(id));
        if (todo != null) {
            return ResponseEntity.ok(todo);
        } else {
            throw new TodoNotFoundException("Todo not found with id " + id,
                    "Please enter a valid Todo id. It does not exist in the database", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/status")
public ResponseEntity<List<Todo>> getTodosByStatus(@RequestParam("completed") String completed) {
  
        List<Todo> todos = webClientService.getTodosByStatus(Boolean.parseBoolean(completed));
        return ResponseEntity.ok(todos);
  
}

@GetMapping("findByIdandUserId/{id}")
public List<Todo> getTodoByIdAndUserId(@PathVariable(name = "id") Integer id,
                                                       @RequestParam(name = "userId") Integer userId) {
    return webClientService.getTodoByIdAndUserId(id, userId);
          
}

@GetMapping("/getTodoByUserId/{id}")
public List<Todo> getTodoByUserId(@PathVariable("id") Integer id)
{
    return webClientService.getTodoByUserId(id);
}
}
