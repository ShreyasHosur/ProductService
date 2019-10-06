package com.inventory.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "current_selling_price")
public class CurrentSellingPrice {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "current_sp")
    private int currentSP;
}
