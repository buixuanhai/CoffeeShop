
import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.EmployeeRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.ui.Main;
import com.xuanhai.util.HibernateUtil;
import org.hibernate.Session;

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
        new Main().setVisible(true);

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        EmployeeRepository repo = new EmployeeRepository();
        repo.delete(5);

//        HibernateUtil.getSessionFactory().close();
    }

    public static SanPham getSanPham() {
        ProductRepository repo = new ProductRepository();
        SanPham sp = repo.get(1);
        return sp;
    }

    private static LoaiSanPham getLoaiSanPham() {
        CategoryRepository repo = new CategoryRepository();
        return repo.get(1);
    }

}
