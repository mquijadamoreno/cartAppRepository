package openboxtd.cartApp.service;

import openboxtd.cartApp.model.Cart;
import openboxtd.cartApp.model.Product;
import openboxtd.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository repository;

    @Override
    public Cart save(Cart cart) {
        return repository.save(cart);
    }

    @Override
    public List<Cart> getAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = false;
        Cart cart = findById(id);
        if(cart != null){
            repository.delete(findById(id));
            deleted = true;
        }
        return deleted;
    }

    @Override
    public Cart findById(Long id) {
        Optional<Cart> cart = repository.findById(id);
        return cart.isPresent() ? cart.get() : null;
    }

    @Override
    public Cart createCart() {
        List<Cart> carts = getAll();
        Cart cart = carts.isEmpty() ? createAndSaveCart() : carts.get(0);
        return cart;
    }

    private Cart createAndSaveCart() {
        Cart cart = Cart.builder().products(new ArrayList<Product>()).build();
        save(cart);
        return cart;
    }

    @Override
    public Cart addProducts(Long idCart, List<Product> products) {
        Cart cart = findById(idCart);
        if(cart != null){
            cart.addProducts(products);
            repository.save(cart);
        }
        return cart;
    }
}
