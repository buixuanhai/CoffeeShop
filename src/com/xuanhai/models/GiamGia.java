/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "giam_gia", catalog = "coffee_shop")
public class GiamGia implements Serializable {

    private int giamGiaId;
    private int phanTram;

    public GiamGia() {

    }

    public GiamGia(int phanTram) {
        this.phanTram = phanTram;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giam_gia_id", unique = true, nullable = false)
    public int getGiamGiaId() {
        return giamGiaId;
    }

    public void setGiamGiaId(int giamGiaId) {
        this.giamGiaId = giamGiaId;
    }

    @Column(name = "phan_tram", unique = true, nullable = false)
    public int getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(int phanTram) {
        this.phanTram = phanTram;
    }

    @Override
    public String toString() {
        return Integer.toString(phanTram);
    }

}
