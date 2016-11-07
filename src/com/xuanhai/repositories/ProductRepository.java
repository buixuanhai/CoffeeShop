/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.SanPham;
import com.xuanhai.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class ProductRepository implements IRepository<SanPham> {

    public ProductRepository() {
    }

    @Override
    public List<SanPham> get() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<SanPham> results = s.createCriteria(SanPham.class).addOrder(Order.asc("loaiSanPham")).list();
        s.getTransaction().commit();
        return results;
    }

    @Override
    public SanPham get(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<SanPham> sanPhams = s.createCriteria(SanPham.class).add(Restrictions.eq("sanPhamId", id)).list();

        s.getTransaction().commit();

        if (sanPhams.isEmpty()) {
            return null;
        } else {
            return sanPhams.get(0);
        }
    }

    @Override
    public int create(SanPham sanPham) {
        if (sanPham == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            int id = (int) s.save(sanPham);
            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public int delete(int sanPhamId) {
        SanPham sp = this.get(sanPhamId);
        if (sp == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(sp);
            s.getTransaction().commit();
            return sanPhamId;
        }
    }

    @Override
    public int update(SanPham sanPham) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(sanPham);
            s.getTransaction().commit();

            return sanPham.getSanPhamId();
        } catch (HibernateException e) {
            return -1;
        }

    }

//    @Override
//    public List<SanPham> getByCategory(int id) {
//        CategoryRepository repo = new CategoryRepository();
//
//        return new ArrayList<>(repo.get(id).getSanPhams());
//        
//    }

}
