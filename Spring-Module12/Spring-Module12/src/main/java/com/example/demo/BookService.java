package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
   private final BookRepository repository;

   public List<Model> getAllBooks(){
       return repository.findAll();
   }

   public Optional<Model> getBookById(Long Id){
       return Optional.of(repository.findById(Id).orElseThrow(() -> new RuntimeException("Book not found with id:" + Id)));
   }

   public void deleteById(Long id){
       if(!repository.existsById(id)){
           throw new RuntimeException("Book dose not exists");
       }
       repository.deleteById(id);
   }

   public void deleteAll(){
       repository.deleteAll();
   }

   public Model createBook(Model model){
       return repository.save(model);
   }

   public Model updateBook(Long id,Model model){
       return repository.findById(id).map(model1 -> {
           model1.setTitle(model.getTitle());
           model1.setAuthor(model.getAuthor());
           model1.setPublication(model.getPublication());
           model1.setPublicationYear(model.getPublicationYear());
           model1.setAvailableCopies(model.getAvailableCopies());
           return repository.save(model1);
       }).orElseThrow(()->new RuntimeException("Update not saved"));
   }
}
