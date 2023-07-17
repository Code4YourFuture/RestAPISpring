package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam Optional<String> name, @RequestParam Optional<BigDecimal> lowerRange, @RequestParam Optional<BigDecimal> upperRange){
        if(name.isPresent()){
            return this.productRepository.findByNameContaining(name.get());
        }
        else if(lowerRange.isPresent() && upperRange.isPresent()){
            return this.productRepository.findByPriceBetween(lowerRange.get(), upperRange.get());
        }
        else{
            return this.productRepository.findAll();
        }
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable int id){
        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent()){
            return product.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not product with that id");
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product){
        return this.productRepository.save(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id){
        this.productRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable int id){
        Optional<Product> productToUpdate = this.productRepository.findById(id);

        if(productToUpdate.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        productToUpdate.get().setPrice(product.getPrice());
        productToUpdate.get().setDescription(product.getDescription());

        return this.productRepository.save(productToUpdate.get());
    }
}
