/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class ProductTableModelTest {
    
    public ProductTableModelTest() {
    }

    /**
     * Test of getRowCount method, of class ProductTableModel.
     */
    @Test
    public void testGetRowCount() {
        
    }

    /**
     * Test of getColumnCount method, of class ProductTableModel.
     */
    @Test
    public void testGetColumnCount() {
        ProductTableModel instance = new ProductTableModel();
        int expResult = 5;
        int result = instance.getColumnCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueAt method, of class ProductTableModel.
     */
    @Test
    public void testGetValueAt() {
        System.out.println("getValueAt");
        int rowIndex = 0;
        int columnIndex = 0;
        ProductTableModel instance = new ProductTableModel();
        Object expResult = null;
        Object result = instance.getValueAt(rowIndex, columnIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
