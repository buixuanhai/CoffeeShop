/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.GiamGia;
import com.xuanhai.repositories.DiscountRepository;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class DiscountComboBoxModel extends DefaultComboBoxModel<GiamGia> {

    DiscountRepository productRepo = new DiscountRepository();

    public DiscountComboBoxModel(GiamGia[] items) {
        super(items);
    }

    @Override
    public GiamGia getSelectedItem() {
        GiamGia selectedDiscount = (GiamGia) super.getSelectedItem();

        // do something with this job before returning...
        return selectedDiscount;
    }
}
