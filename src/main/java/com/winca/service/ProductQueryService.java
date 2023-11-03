package com.winca.service;

import com.winca.dto.ProductEvent;
import com.winca.entity.Product;
import com.winca.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
    public void processProductEvents(ProductEvent productEvent){

        Product product = productEvent.getProduct();
        if(productEvent.getEventType().equalsIgnoreCase("CreateProduct")){
            productRepository.save(product);
        }
        if(productEvent.getEventType().equalsIgnoreCase("UpdateProduct")){
            Product existingProduct = productRepository.findById(product.getId()).get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            productRepository.save(existingProduct);
        }

        if(productEvent.getEventType().equalsIgnoreCase("DeleteProduct")){
            Product deletedProduct = productRepository.findById(product.getId()).get();
            productRepository.delete(deletedProduct);
        }
    }
}
