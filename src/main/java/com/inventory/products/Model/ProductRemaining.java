package com.inventory.products.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_remaining")
public class ProductRemaining {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "quantities")
    private int quantities;

}
