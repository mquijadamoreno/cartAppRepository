package openboxtd.cartApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CartCleanupService {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final int TIMEOUT = 10;

    private ScheduledFuture<?> task;

    private Long currentCartId;

    @Autowired
    private CartService cartService;

    public void checkCart(Long cartId){
        if(currentCartId == null){
            currentCartId = cartId;
            System.out.println("Creando timeout carro con Id: " + cartId);
            task = executor.schedule(() -> {
                System.out.println("Timeout para el carro : " + cartId + " alcanzado, borrando..");
                cartService.delete(cartId);
            }, TIMEOUT, TimeUnit.MINUTES);
        }
    }

    public void updateCheck(long cartId){
        System.out.println("Actualizando timeout carro con Id: " + cartId);
        deleteCheck(cartId);
        checkCart(cartId);
    }

    public void deleteCheck(long cartId){
        task.cancel(true);
        currentCartId = null;
    }

}
