package openboxtd.cartApp.controller;

import openboxtd.cartApp.model.Cart;
import openboxtd.cartApp.model.Product;
import openboxtd.cartApp.service.CartCleanupService;
import openboxtd.cartApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartCleanupService cartCleanupService;

    @GetMapping
    public List<Cart> getCarts(){
        return cartService.getAll();
    }

    @PostMapping("/new")
    public Cart createCart(){
        Cart cart = cartService.createCart();
        cartCleanupService.checkCart(cart.getCartId());
        return cart;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCart(@PathVariable Long id){
        Cart cart = cartService.findById(id);
        ResponseEntity<String> response;
        if(cart != null){
            cartCleanupService.updateCheck(cart.getCartId());
            response = ResponseEntity.ok().body(cart.toString());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Identificador de carro incorrecto.");
        }
        return response;
    }


    @PutMapping("/{idCart}/products")
    public ResponseEntity<String> addProducts(@PathVariable Long idCart, @RequestBody List<Product> products){
        ResponseEntity<String> response;
        if(products != null && !products.isEmpty()){
            Cart cart = cartService.addProducts(idCart, products);
            if(cart != null){
                cartCleanupService.updateCheck(cart.getCartId());
                response = ResponseEntity.ok().body(
                        "Producto añadido con éxito. \n" + cart.toString());

            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Identificador de carro incorrecto.");
            }
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lista de productos incorrecta.");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id){
        ResponseEntity<String> response;
        if(id != null){
            if(cartService.delete(id)){
                response = ResponseEntity.ok().body("Carro borrado con éxito");
                cartCleanupService.deleteCheck(id);
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Identificador de carro no encontrado");
            }
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Identificador de carro no válido.");
        }
        return response;
    }
}
