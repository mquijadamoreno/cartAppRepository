package openboxtd.cartApp.service;

import openboxtd.cartApp.model.Product;
import openboxtd.cartApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id).get();
    }
}
