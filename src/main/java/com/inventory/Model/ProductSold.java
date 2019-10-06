package com.inventory.Model;

import com.inventory.Model.Repositories.PrimaryKey.ProductSoldPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductSoldPrimaryKey.class)
@Table(name = "product_sold")
public class ProductSold {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Id
    @Column(name = "sold_time")
    private LocalDateTime soldTime;

    @Column(name = "quantities")
    private int quantities;

    @Column(name = "sold_price")
    private int soldPrice;

    @Column(name ="total_sp")
    private int totalSP;

}
