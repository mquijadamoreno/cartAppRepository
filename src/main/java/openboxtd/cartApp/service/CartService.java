package openboxtd.cartApp.service;

import openboxtd.cartApp.model.Cart;
import openboxtd.cartApp.model.Product;

import java.util.List;

public interface CartService {

    Cart save(Cart cart);

    List<Cart> getAll();

    boolean delete(Long id);

    Cart findById(Long id);

    Cart createCart();
    Cart addProducts(Long idCart, List<Product> product);
}
