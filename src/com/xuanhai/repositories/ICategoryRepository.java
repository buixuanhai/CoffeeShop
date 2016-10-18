/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.LoaiSanPham;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ICategoryRepository {

    List<LoaiSanPham> get();

    LoaiSanPham get(int id);

    int create(LoaiSanPham sanPham);

    int delete(int sanPhamId);

    int update(LoaiSanPham sanPham);

}