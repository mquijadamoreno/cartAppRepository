package openboxtd.cartApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name ="cartId",
            referencedColumnName = "cartId"
    )
    private List<Product> products;

    public void addProducts(List<Product> products) { this.products.addAll(products); }

    @Override
    public String toString() {
        String toString = "Id : " + cartId + "\nProductos : ";
        if(products != null && !products.isEmpty()){
            for (Product p : products) {
                toString += "\n\t{ Id : " + p.getId() + "\n\t  Descripcion : " + p.getDescription() + "\n\t  Cantidad : " + p.getAmount() + " }";
            }
        }

        return toString;
    }
}
