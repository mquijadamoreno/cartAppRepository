package openboxtd.cartApp.service;

import jakarta.transaction.Transactional;
import openboxtd.cartApp.model.Cart;
import openboxtd.cartApp.model.Product;
import openboxtd.cartApp.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void createCartTest() {

        Product product1 = Product.builder().description("producto de cocina").amount(2).build();
        Product product2 = Product.builder().description("producto de baño").amount(1).build();
        Cart cart1 = Cart.builder().products(new ArrayList<>(List.of(product1,product2))).build();
        cart1 = cartRepository.save(cart1);
        assertEquals(cartService.createCart().getCartId(),cart1.getCartId());
    }

    @Test
    void createCartTestNew() {
        Cart cart = cartService.createCart();
        assertEquals(cartService.getAll().get(0).getCartId(),cart.getCartId());
    }

    @Test
    void findByIdTest(){
        Cart cart1 = cartService.createCart();
        Cart cartAux = cartService.findById(cart1.getCartId());
        assertEquals(cart1.getCartId(),cartAux.getCartId());
    }

    @Test
    @Transactional
    void addProductsTest(){
        final String PRODUCTO_1_DESC = "producto de cocina";
        final String PRODUCTO_2_DESC = "producto de baño";
        final int PRODUCTO_1_AMOUNT = 1;
        final int PRODUCTO_2_AMOUNT = 2;

        Cart cart = cartService.createCart();
        Product product1 = Product.builder().description(PRODUCTO_1_DESC).amount(PRODUCTO_1_AMOUNT).build();
        Product product2 = Product.builder().description(PRODUCTO_2_DESC).amount(PRODUCTO_2_AMOUNT).build();

        cartService.addProducts(cart.getCartId(),new ArrayList<>(List.of(product1,product2)));

        for (Product p : cartService.findById(cart.getCartId()).getProducts()) {
            if (p.getAmount() == PRODUCTO_1_AMOUNT){
                assertTrue(p.getDescription().equals(PRODUCTO_1_DESC));
            } else {
                assertTrue(p.getDescription().equals(PRODUCTO_2_DESC));
            }
        }

    }

    @Test
    void deleteCartTest(){
        Cart cart = cartService.createCart();
        cartService.delete(cart.getCartId());
        assertEquals(cartService.findById(cart.getCartId()),null);
    }
}