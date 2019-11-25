package com.inventory.products.Model;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_details")
public class ProductsInfo {

    @Id
    @Column(name = "product_id")
    private int productId ;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_name")
    private String productName;

}
