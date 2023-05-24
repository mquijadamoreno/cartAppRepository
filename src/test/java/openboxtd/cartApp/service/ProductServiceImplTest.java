package openboxtd.cartApp.service;

import openboxtd.cartApp.model.Product;
import openboxtd.cartApp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void getAll() {
        Product product = Product.builder().description("Descripcion1").amount(1).build();
        Product product2 = Product.builder().description("Descripcion2").amount(2).build();
        productRepository.save(product2);
        productRepository.save(product);
        List<Product> productList = productService.getAll();
        assertTrue(productList.size() == 2);
    }

    @Test
    void findById() {
        Product product = Product.builder().description("Descripcion1").amount(1).build();
        productRepository.save(product);
        List<Product> productList = productService.getAll();
        Product productAux = productList.get(0);
        assertEquals(productAux,productService.findById(productAux.getId()));
    }
}