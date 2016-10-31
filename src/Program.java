
import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.ui.Main;

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

//        TableRepository repo = new TableRepository();
//        repo.create(4,4);
        
        new Main().setVisible(true);

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
