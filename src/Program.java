
import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.EmployeeRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.repositories.TableRepository;
import com.xuanhai.ui.Login;

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

    private static final EmployeeRepository employeeRepo = new EmployeeRepository();
    private static final TableRepository tableRepo = new TableRepository();

    public static void main(String[] args) throws InterruptedException {
        
        employeeRepo.create(new NhanVien("Bùi Xuân Hải", new java.util.Date(93, 1, 9), new java.util.Date(), "admin", "123456"));
        employeeRepo.create(new NhanVien("Nguyễn Hoàng Nam", new java.util.Date(93, 1, 9), new java.util.Date(), "hoangnam", "123456"));
        new Login().setVisible(true);

//        new Main(new EmployeeRepository().getByUsername("admin")).setVisible(true);
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
