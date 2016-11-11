
import com.xuanhai.models.Ban;
import com.xuanhai.models.DatBan;
import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.EmployeeRepository;
import com.xuanhai.repositories.OrderedTableRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.repositories.TableRepository;
import com.xuanhai.ui.Login;
import com.xuanhai.ui.Main;
import java.util.List;

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

    public static void main(String[] args) throws InterruptedException {

//        new Login().setVisible(true);

        employeeRepo.create(new NhanVien("Bùi Xuân Hải", new java.util.Date(1993, 1, 9), new java.util.Date(), "admin", "123456"));
        new Main(new EmployeeRepository().getByUsername("admin")).setVisible(true);

//        TableRepository tableRepository = new TableRepository();
//        ProductRepository productRepository = new ProductRepository();
//        OrderedTableRepository orderedTableRepository = new OrderedTableRepository();
//
//        List<DatBan> datBans = orderedTableRepository.getByTableId(1);
//
//        if (datBans != null) {
//            for (DatBan datBan : datBans) {
//                System.out.println(datBan.getSanPham());
//            }
//        }
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
