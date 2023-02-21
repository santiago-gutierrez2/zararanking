package com.inditex.zboost.entity;

import java.io.Serializable;

public class ProductOrderItem extends Product implements Serializable {

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
