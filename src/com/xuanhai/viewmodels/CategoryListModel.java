/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.repositories.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;

public class CategoryListModel extends DefaultListModel {

    private final CategoryRepository repo;
    private List<LoaiSanPham> data;

    private List<String> tenLoaiSanPham;

    public CategoryListModel() {
        this.tenLoaiSanPham = new ArrayList<>();
        this.repo = new CategoryRepository();
        this.data = repo.get();
        
        tenLoaiSanPham = data.stream().map(LoaiSanPham::getTenLoaiSanPham).collect(Collectors.toList());
    }

    // Allow get data to check if Category contains a name
    public List<LoaiSanPham> getData() {
        return data;
    }

    public void setData(List<LoaiSanPham> data) {
        this.data = data;
    }

    public List<String> getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(List<String> tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public LoaiSanPham getElementAt(int index) {
        return data.get(index);
    }

}
