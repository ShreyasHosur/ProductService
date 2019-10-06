package com.inventory.Model.Repositories.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductSoldPrimaryKey implements Serializable {
    private int productId;
    private LocalDateTime soldTime;
}
