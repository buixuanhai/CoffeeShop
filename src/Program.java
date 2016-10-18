
import com.xuanhai.repositories.CategoryRepository;
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

    public static void main(String[] args) {
//        Session s = HibernateUtil.getSessionFactory().openSession();

        new Main().setVisible(true);


//        HibernateUtil.getSessionFactory().close();
    }

}
