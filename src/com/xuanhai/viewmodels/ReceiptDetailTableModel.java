/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.ChiTietHoaDon;
import com.xuanhai.models.HoaDon;
import com.xuanhai.repositories.ReceiptDetailRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class ReceiptDetailTableModel extends AbstractTableModel {

    private List<ChiTietHoaDon> data;
    private List<Object[]> model;
    private String[] columnNames = {"Id", "Sản phẩm", "Số lượng", "Đơn giá"};

    public ReceiptDetailTableModel(List<ChiTietHoaDon> data) {
        this.data = data;
        model = data.stream().map(e -> new Object[]{e.getChiTietHoaDonId(), e.getSanPham().getTenSanPham(), e.getSoLuong(), e.getSanPham().getDonGia()}).collect(Collectors.toList());

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
