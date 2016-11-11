/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.HoaDon;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class ReceiptTableModel extends AbstractTableModel {

    private ReceiptRepository repo;
    private List<HoaDon> data;
    private List<Object[]> model;
    private String[] columnNames = {"Id", "Ngày hóa đơn", "Giảm giá",
        "Tổng trị giá", "Bàn", "Người lập"};

    public ReceiptTableModel() {
        repo = new ReceiptRepository();
        this.data = repo.get();
        model = data.stream().map(e -> new Object[]{e.getHoaDonId(), e.getNgayHoaDon(), e.getGiamGia(), e.getTongTriGia(), e.getBan().getBanId(), e.getNhanVien()}).collect(Collectors.toList());
    }

    public ReceiptTableModel(List<HoaDon> data) {
        this.data = data;
        model = data.stream().map(e -> new Object[]{e.getHoaDonId(), e.getNgayHoaDon(), e.getGiamGia(), e.getTongTriGia(), e.getBan().getBanId(), e.getNhanVien()}).collect(Collectors.toList());

    }

    @Override
    public int getRowCount() {
        return model.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
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
