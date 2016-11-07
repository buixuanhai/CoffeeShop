/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.EmployeeRepository;
import com.xuanhai.repositories.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class EmployeeTableModel extends AbstractTableModel {

    private EmployeeRepository repo;
    private List<NhanVien> data;
    private List<Object[]> model;
    private String[] columnNames = {"Id", "Họ tên", "Ngày sinh",
        "Ngày vào làm"};

    public EmployeeTableModel() {
        repo = new EmployeeRepository();
        this.data = repo.get();
        model = data.stream().map(e -> new Object[]{e.getNhanVienId(), e.getHoTen(), e.getNgaySinh(), e.getNgayVaoLam()}).collect(Collectors.toList());
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
