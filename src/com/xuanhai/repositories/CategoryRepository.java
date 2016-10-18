/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class CategoryRepository implements ICategoryRepository {

    @Override
    public int create(LoaiSanPham loaiSanPham) {
        if (loaiSanPham == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().getCurrentSession();
            s.beginTransaction();

            int id = (int) s.save(loaiSanPham);

            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public List<LoaiSanPham> get() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<LoaiSanPham> results = s.createCriteria(LoaiSanPham.class).list();

        s.getTransaction().commit();
        return results;
    }

    @Override
    public LoaiSanPham get(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<LoaiSanPham> result = s.createCriteria(LoaiSanPham.class).add(Restrictions.eq("loaiSanPhamId", id)).list();

        s.getTransaction().commit();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public int delete(int loaiSanPhamId) {
        LoaiSanPham loaiSanPham = this.get(loaiSanPhamId);
        if (loaiSanPham == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            s.delete(loaiSanPham);

            s.getTransaction().commit();
            return loaiSanPhamId;
        }
    }

    @Override
    public int update(LoaiSanPham loaiSanPham) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            s.update(loaiSanPham);

            s.getTransaction().commit();

            return loaiSanPham.getLoaiSanPhamId();
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }

    }

}
