/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.SanPham;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IProductRepository {

    List<SanPham> Get();

    SanPham Get(int id);

    int create(SanPham sanPham);

    int delete(int sanPhamId);

    int update(SanPham sanPham);
    

}
