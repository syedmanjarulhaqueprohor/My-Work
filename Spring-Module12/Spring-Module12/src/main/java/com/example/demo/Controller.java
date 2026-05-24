package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class Controller {
    private final BookService bookService;
    @GetMapping
    public ResponseEntity<List<Model>> getAllBooks(){
        List<Model> models=bookService.getAllBooks();
        return ResponseEntity.ok(models);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Model> getBookById(@PathVariable Long Id){
       return bookService.getBookById(Id).map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Model> createBook(@RequestBody Model model){
        try {
            Model book=bookService.createBook(model);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try{
            bookService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        try {
            bookService.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Model> updateBook(@PathVariable Long id,@RequestBody Model model){
        try{
            Model model1=bookService.updateBook(id,model);
            return ResponseEntity.ok(model1);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
