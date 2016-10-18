/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.SanPham;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class ProductRepository implements IProductRepository {

    public ProductRepository() {
    }

    @Override
    public List<SanPham> Get() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<SanPham> results = s.createCriteria(SanPham.class).list();
        s.getTransaction().commit();
        return results;
    }

    @Override
    public SanPham Get(int id) {
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
//            HibernateUtil.getSessionFactory().close();
            return id;
        }
    }

    @Override
    public int delete(int sanPhamId) {
        SanPham sp = this.Get(sanPhamId);
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

}
