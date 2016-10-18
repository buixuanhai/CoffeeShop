/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.SanPham;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class ProductRepositoryTest {

    public ProductRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ProductRepository.
     */
    @Test
    public void testCreate() {
        // Arrange
        String tenSanPham = "Test Product";
        SanPham sanPham = new SanPham();
        sanPham.setTenSanPham(tenSanPham);
        ProductRepository instance = new ProductRepository();

        // Act
        int id = instance.create(sanPham);

        // Assert
        assertEquals(tenSanPham, instance.Get(id).getTenSanPham());
    }

    /**
     * Test of Get method, of class ProductRepository.
     */
    @Test
    public void testGetAll() {
        ProductRepository instance = new ProductRepository();
        List<SanPham> result = instance.Get();
        assertNotNull(result);
    }

    /**
     * Test of Get method, of class ProductRepository.
     */
    @Test
    public void testGetById() {
        int id = 1;
        ProductRepository instance = new ProductRepository();
        SanPham result = instance.Get(id);
        assertEquals(1, result.getSanPhamId());
    }

    /**
     * Test of Get method, of class ProductRepository.
     */
    @Test
    public void testGetByIdNotExist() {
        int id = -1;
        ProductRepository instance = new ProductRepository();
        SanPham result = instance.Get(id);
        assertNull(result);
    }

    /**
     * Test of update method, of class ProductRepository.
     */
    @Test
    public void testUpdateSuccess() {
        // Arrange
        ProductRepository instance = new ProductRepository();
        SanPham sp = instance.Get(1);
        String expected = "Updated";
        sp.setTenSanPham(expected);

        // Act
        instance.update(sp);

        // Assert
        assertEquals(expected, instance.Get(1).getTenSanPham());

    }

    /**
     * Test of update method, of class ProductRepository.
     */
    @Test
    public void testUpdateFail() {
        // Arrange
        ProductRepository instance = new ProductRepository();
        SanPham sp = new SanPham();

        // Act
        int actual = instance.update(sp);

        // Assert
        assertEquals(-1, actual);

    }

    /**
     * Test of delete method, of class ProductRepository.
     */
    @Test
    public void testDelete() {
        // Arrange
        ProductRepository instance = new ProductRepository();
        List<SanPham> spList = instance.Get();
        SanPham spToDelete = spList.get(spList.size() - 1);
        int spToDeleteId = spToDelete.getSanPhamId();

        // Act
        instance.delete(spToDeleteId);

        // Assert
        assertNull(instance.Get(spToDeleteId));
    }

}
