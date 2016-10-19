/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Admin
 */
public class ProductTableSelectionModel extends DefaultListSelectionModel {

    public ProductTableSelectionModel() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    
    
    
}
