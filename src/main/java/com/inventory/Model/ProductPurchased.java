package com.inventory.Model;

import com.inventory.Model.Repositories.PrimaryKey.ProductPurchasedPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProductPurchasedPrimaryKey.class)
@Table(name = "product_purchased")
public class ProductPurchased {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Id
    @Column(name = "purchased_date")
    private LocalDate purchasedDate;

    @Column(name = "purchased_price")
    private int purchasedPrice;

    @Column(name = "quantities")
    private int quantities;

    @Column(name = "total_price")
    private int totalPrice;

}
