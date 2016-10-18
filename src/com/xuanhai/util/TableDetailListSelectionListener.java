/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.util;

import com.xuanhai.ui.TableDetail;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author buixu
 */
public class TableDetailListSelectionListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            System.out.println("Changed");

            TableDetail tableDetail = new TableDetail();
            tableDetail.setLocationRelativeTo(null);
            tableDetail.setVisible(true);
            
        }
    }

}
