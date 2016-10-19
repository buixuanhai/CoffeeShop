
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.ui.Main;
import com.xuanhai.util.HibernateUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class Program {

    private static final CategoryRepository repo = new CategoryRepository();

    public static void main(String[] args) throws InterruptedException {
//        SanPham sp = getSanPham();
//        
//        Thread.sleep(1000);
//        System.out.println(sp.getLoaiSanPham().getTenLoaiSanPham());

        new Main().setVisible(true);

//        HibernateUtil.getSessionFactory().close();
    }

//    public static SanPham getSanPham() {
//        ProductRepository repo = new ProductRepository();
//        SanPham sp = repo.get(1);
//        return sp;
//    }

}
