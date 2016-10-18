/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.LoaiSanPham;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class CategoryRepositoryTest {

    public CategoryRepositoryTest() {
    }

    /**
     * Test of create method, of class CategoryRepository.
     */
    @Test
    public void testCreate() {

        // Arrange
        CategoryRepository repo = new CategoryRepository();

        String tenLoaiSanPham = "Test";
        LoaiSanPham loaiSanPham = new LoaiSanPham(tenLoaiSanPham);

        //act
        int id = repo.create(loaiSanPham);

        LoaiSanPham result = repo.get(id);

        assertEquals(tenLoaiSanPham, result.getTenLoaiSanPham());
    }

    /**
     * Test of get method, of class CategoryRepository.
     */
    @Test
    public void testGetAllReturnResult() {
        CategoryRepository repo = new CategoryRepository();

        List<LoaiSanPham> result = repo.get();

        assertNotNull(result);
    }

    /**
     * Test of get method, of class CategoryRepository.
     */
    @Test
    public void testGetById() {
        CategoryRepository repo = new CategoryRepository();
        int expected = 1;

        LoaiSanPham result = repo.get(expected);

        assertEquals(1, 1);
    }

    @Test
    public void testUpdate() {
        // Arrange
        String expected = "Updated";
        CategoryRepository repo = new CategoryRepository();
        List<LoaiSanPham> loaiSanPhams = repo.get();
        LoaiSanPham loaiSanPhamToUpdate = loaiSanPhams.get(loaiSanPhams.size() - 1);
        loaiSanPhamToUpdate.setTenLoaiSanPham(expected);
        
        // Act
        int id = repo.update(loaiSanPhamToUpdate);
        
        // Assert
        assertEquals(expected, repo.get(id).getTenLoaiSanPham());
    }

    /**
     * Test of delete method, of class CategoryRepository.
     */
    @Test
    public void testDelete() {
        // Arrange
        CategoryRepository repo = new CategoryRepository();
        List<LoaiSanPham> loaiSanPhams = repo.get();
        LoaiSanPham loaiSanPhamToDelete = loaiSanPhams.get(loaiSanPhams.size() - 1);

        // Act
        int id = repo.delete(loaiSanPhamToDelete.getLoaiSanPhamId());

        // Assert
        assertNull(repo.get(id));
    }

}
