package com.example.M_11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service {
    private final List<Model> models=new ArrayList<>();
    public Model addModel(Model model){
         models.add(model);
         return model;
    }
    public List<Model> getAllTransaction(){
        return models;
    }
    public Optional<Model> getById(Long id){
        return models.stream().filter(t->t.getId().equals(id)).findFirst();
    }
    public boolean deleteById(Long id){
        return models.removeIf(t->t.getId().equals(id));
    }
}
