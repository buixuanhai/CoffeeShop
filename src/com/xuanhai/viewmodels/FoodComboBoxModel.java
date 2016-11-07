/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.ProductRepository;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class FoodComboBoxModel extends DefaultComboBoxModel<SanPham> {

    ProductRepository productRepo = new ProductRepository();

    public FoodComboBoxModel(SanPham[] items) {
        super(items);
    }

    @Override
    public SanPham getSelectedItem() {
        SanPham selectedTable = (SanPham) super.getSelectedItem();

        // do something with this job before returning...
        return selectedTable;
    }
}
