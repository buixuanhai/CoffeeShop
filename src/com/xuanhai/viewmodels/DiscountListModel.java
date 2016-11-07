/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.GiamGia;
import com.xuanhai.repositories.DiscountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;

/**
 *
 * @author Admin
 */
public class DiscountListModel extends DefaultListModel {

    private final DiscountRepository repo;
    private List<GiamGia> data;

    private List<Integer> discountPercents;

    public DiscountListModel() {
        this.discountPercents = new ArrayList<>();
        this.repo = new DiscountRepository();
        this.data = repo.get();

        discountPercents = data.stream().map(GiamGia::getPhanTram).collect(Collectors.toList());
    }

    // Allow get data to check if Category contains a name
    public List<GiamGia> getData() {
        return data;
    }

    public void setData(List<GiamGia> data) {
        this.data = data;
    }

    public List<Integer> getDiscountPercents() {
        return discountPercents;
    }

    public void setDiscountPercents(List<Integer> discountPercents) {
        this.discountPercents = discountPercents;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public GiamGia getElementAt(int index) {
        return data.get(index);
    }
}
