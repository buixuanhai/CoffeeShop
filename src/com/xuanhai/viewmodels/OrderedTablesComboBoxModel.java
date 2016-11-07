/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.Ban;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class OrderedTablesComboBoxModel extends DefaultComboBoxModel<Ban> {

    public OrderedTablesComboBoxModel(Ban[] items) {
        super(items);
    }

    @Override
    public Ban getSelectedItem() {
        Ban selectedTable = (Ban) super.getSelectedItem();

        // do something with this job before returning...
        return selectedTable;
    }
}
