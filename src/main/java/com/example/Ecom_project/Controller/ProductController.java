package com.example.Ecom_project.Controller;


import com.example.Ecom_project.Models.Product;
import com.example.Ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getALlProducts(){
        List<Product> send = service.getAllProducts();
        return new ResponseEntity<>(send, HttpStatus.OK);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product item = service.getProduct(id);
        if(item !=null)
           return new ResponseEntity<>(item,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException {
        try {
            return new ResponseEntity<>(service.addProduct(product, imageFile), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = service.getProduct(productId);
        byte[] image = product.getImageData();
        return ResponseEntity.ok().body(image);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
                                           @RequestPart MultipartFile imageFile ) throws IOException {
        Product product1 = service.updateProduct(product,imageFile);
        if(product1 !=null){
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to update",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable int id){
        service.deleteProduct(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
