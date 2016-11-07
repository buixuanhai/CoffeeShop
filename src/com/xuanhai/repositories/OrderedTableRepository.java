/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.Ban;
import com.xuanhai.models.DatBan;
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
public class OrderedTableRepository {

    public List<DatBan> getByTableId(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        TableRepository tableRepo = new TableRepository();
        Ban ban = tableRepo.get(id);

        if (ban != null) {
            List<DatBan> datBans = s.createCriteria(DatBan.class)
                    .add(Restrictions.eq("ban", ban))
                    .add(Restrictions.ge("createDate", ban.getUpdateDate()))
                    .list();

            s.getTransaction().commit();

            if (datBans.isEmpty()) {
                return null;
            } else {
                return datBans;
            }
        }

        return null;

    }

    public int createOrUpdate(DatBan datBan) {

        Ban ban = datBan.getBan();

        if (ban.getConTrong()) {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            int id = (int) s.save(datBan);
            s.getTransaction().commit();

            ban.setConTrong(false);
            new TableRepository().update(ban);

            return id;
        } else {

            // Create list of DatBan by table Id
            List<DatBan> datBans = getByTableId(datBan.getBan().getBanId());
            SanPham sanPham = datBan.getSanPham();

            /**
             * Loop through list of DatBan if there is a row with same product
             * update quantity
             */
            for (DatBan db : datBans) {
                if (db.getSanPham().getTenSanPham().equals(sanPham.getTenSanPham())) {
                    db.setSoLuong(datBan.getSoLuong());

                    Session s = HibernateUtil.getSessionFactory().openSession();
                    s.beginTransaction();
                    s.update(db);
                    s.getTransaction().commit();

                    return db.getDatBanId();
                }
            }

            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.save(datBan);
            s.getTransaction().commit();

            return -1;
        }

    }

    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int update(DatBan object) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            object.setUpdateDate(new java.util.Date());
            s.update(object);
            s.getTransaction().commit();

            return object.getBan().getBanId();
        } catch (HibernateException e) {
            return -1;
        }
    }

}
