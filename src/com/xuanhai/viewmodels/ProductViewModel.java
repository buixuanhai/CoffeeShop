/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class ProductViewModel {

    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private int loaiSanPhamId;

    public ProductViewModel(int id, String name, BigDecimal price, int quantity, int loaiSanPhamId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.loaiSanPhamId = loaiSanPhamId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLoaiSanPhamId() {
        return loaiSanPhamId;
    }

    public void setLoaiSanPhamId(int loaiSanPhamId) {
        this.loaiSanPhamId = loaiSanPhamId;
    }

}
