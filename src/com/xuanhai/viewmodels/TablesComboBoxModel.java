/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.Ban;
import com.xuanhai.repositories.TableRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class TablesComboBoxModel extends DefaultComboBoxModel<Ban> {

    TableRepository tableRepo = new TableRepository();

    public TablesComboBoxModel(Ban[] items) {
        super(items);
    }

    @Override
    public Ban getSelectedItem() {
        Ban selectedTable = (Ban) super.getSelectedItem();

        // do something with this job before returning...
        return selectedTable;
    }
}
