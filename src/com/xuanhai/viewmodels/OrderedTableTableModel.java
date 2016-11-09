/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.viewmodels;

import com.xuanhai.models.DatBan;
import com.xuanhai.repositories.OrderedTableRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class OrderedTableTableModel extends AbstractTableModel {

    private final OrderedTableRepository orderedTableRepo;
    private final List<DatBan> data;
    private List<Object[]> model = null;
    private final String[] columnNames = {"Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};

    public OrderedTableTableModel(int tableId) {

        orderedTableRepo = new OrderedTableRepository();
        this.data = orderedTableRepo.getByTableId(tableId);

        if (data != null) {
            model = data.stream()
                    .map(d -> new Object[]{
                d.getSanPham(),
                d.getSoLuong(),
                d.getSanPham().getDonGia().intValue(),
                d.getSanPham().getDonGia().intValue() * d.getSoLuong()
            })
                    .collect(Collectors.toList());
        }

    }

    @Override
    public int getRowCount() {
        return model != null ? model.size() : 0;
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
