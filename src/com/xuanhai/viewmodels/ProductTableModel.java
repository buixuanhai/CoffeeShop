/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class ProductTableModel extends AbstractTableModel {

    private ProductRepository repo;
    private List<SanPham> data;
    private List<Object[]> model;
    private String[] columnNames = { "ID", "Tên sản phẩm", "Đơn giá",
        "Số lượng" };

    public ProductTableModel() {
        repo = new ProductRepository();
        this.data = repo.get();

        model = data.stream().map(d -> new Object[]{d.getSanPhamId(), d.getTenSanPham(), d.getDonGia(), d.getSoLuong()}).collect(Collectors.toList());

    }

    @Override
    public int getRowCount() {
        return model.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return model.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    

}
