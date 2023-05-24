package openboxtd.cartApp.service;

import openboxtd.cartApp.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product findById(Long id);
}
