package com.example.M_11;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class Controller {
    private Service service;
    public Controller(Service service){
        this.service=service;
    }
    @GetMapping
    public List<Model> getAllTransaction(){
         return service.getAllTransaction();
    }
    @GetMapping("/{id}")
    public Optional<Model> getById(@PathVariable Long id){
        return service.getById(id);
    }
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }
    @PostMapping
    public Model addModel(@RequestBody Model model){
        return service.addModel(model);
    }
}
