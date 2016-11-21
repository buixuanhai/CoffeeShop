/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.ui;

import com.xuanhai.models.Ban;
import com.xuanhai.models.ChiTietHoaDon;
import com.xuanhai.models.DatBan;
import com.xuanhai.models.GiamGia;
import com.xuanhai.models.HoaDon;
import com.xuanhai.models.LoaiSanPham;
import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.repositories.CategoryRepository;
import com.xuanhai.repositories.DiscountRepository;
import com.xuanhai.repositories.EmployeeRepository;
import com.xuanhai.repositories.OrderedTableRepository;
import com.xuanhai.repositories.ProductRepository;
import com.xuanhai.repositories.ReceiptDetailRepository;
import com.xuanhai.repositories.ReceiptRepository;
import com.xuanhai.repositories.TableRepository;
import com.xuanhai.util.Utilities;
import com.xuanhai.viewmodels.CategoryListModel;
import com.xuanhai.viewmodels.DiscountComboBoxModel;
import com.xuanhai.viewmodels.DiscountListModel;
import com.xuanhai.viewmodels.DrinkComboBoxModel;
import com.xuanhai.viewmodels.EmployeeTableModel;
import com.xuanhai.viewmodels.FoodComboBoxModel;
import com.xuanhai.viewmodels.OrderedTableTableModel;
import com.xuanhai.viewmodels.OrderedTablesComboBoxModel;
import com.xuanhai.viewmodels.ProductTableModel;
import com.xuanhai.viewmodels.ReceiptDetailTableModel;
import com.xuanhai.viewmodels.ReceiptTableModel;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import com.xuanhai.viewmodels.TablesComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Admin
 */
public class Main extends javax.swing.JFrame {

    private final CategoryRepository categoryRepo = new CategoryRepository();

    private final ProductRepository productRepo = new ProductRepository();

    private final DiscountRepository discountRepo = new DiscountRepository();

    private final TableRepository tableRepo = new TableRepository();

    private final EmployeeRepository employeeRepo = new EmployeeRepository();

    private final OrderedTableRepository orderedTableRepo = new OrderedTableRepository();

    private final ReceiptRepository receiptRepository = new ReceiptRepository();

    private final ReceiptDetailRepository receiptDetailRepository = new ReceiptDetailRepository();

    private int editingEmployeeId = 0;

    private NhanVien loggedUser;

    public NhanVien getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(NhanVien loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Creates new form NewJFrame
     */
    public Main() {
        initComponents();
        setResizable(false);
        seed();
        initUIs();
    }

    public Main(NhanVien loggedUser) {
        this();
        this.loggedUser = loggedUser;
        if (!loggedUser.getUsername().equals("admin")) {
            TabbedPane.removeTabAt(3);
            TabbedPane.removeTabAt(3);
            TabbedPane.removeTabAt(3);

        }
    }

    private void initUIs() {
        initCategoryListBox();
        initProductTable();
        initCurrentTables();
        initDiscountList();
        initEmployeeTab();
        initTableOrderTab();
        initReceiptTab(null);
        initReceiptsByMonthTable();

    }

    private void seed() {

        // Tables
        tableRepo.create(1, 10);

        // Discounts
        for (int i = 0; i < 4; i++) {
            discountRepo.create(new GiamGia((i) * 10));
        }

        // createOrUpdate categories
        categoryRepo.create(new LoaiSanPham("Thức uống"));
        categoryRepo.create(new LoaiSanPham("Đồ ăn"));

        LoaiSanPham drink = categoryRepo.get(1);
        LoaiSanPham food = categoryRepo.get(2);
        // createOrUpdate products
        productRepo.create(new SanPham("Coffee đen", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Coffee sữa", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Pepsi ", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Trà đào", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Nước suối", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Seven up", new BigDecimal(10000), 0, drink));
        productRepo.create(new SanPham("Cola", new BigDecimal(10000), 0, drink));

        productRepo.create(new SanPham("Hạt hướng dương", new BigDecimal(10000), 0, food));
        productRepo.create(new SanPham("Hạt dưa", new BigDecimal(10000), 0, food));
        productRepo.create(new SanPham("Hạt điều", new BigDecimal(10000), 0, food));
        productRepo.create(new SanPham("Bánh phồng tôm", new BigDecimal(10000), 0, food));
        productRepo.create(new SanPham("Đậu phộng", new BigDecimal(10000), 0, food));

        orderedTableRepo.createOrUpdate(new DatBan(1, productRepo.get(1), tableRepo.get(1)));
//        for (DatBan datBan : orderedTableRepo.getByTableId(1)) {
//            System.out.println(datBan.getSanPham());
//        }
        orderedTableRepo.createOrUpdate(new DatBan(2, productRepo.get(1), tableRepo.get(1)));
        orderedTableRepo.createOrUpdate(new DatBan(1, productRepo.get(3), tableRepo.get(2)));

    }

    private void initProductTable() {
        productTable.setModel(new ProductTableModel());
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            editProductButton.setVisible(true);
            deleteProductButton.setVisible(true);
        });

        disableProductButtons();

    }

    private void initCurrentTables() {

        currentTableNumberTextField.setText(Integer.toString(tableRepo.count()));
        currentTableIdStartTextField.setText(Integer.toString(tableRepo.getFirstTableId()));
        currentTableIdEndTextField.setText(Integer.toString(tableRepo.getLastTableId()));
    }

    private void initEmployeeTab() {
        employeeNameTextField.setText("");
        employeeBirthdayTextField.setText("");
        employeeHiredDateTextField.setText("");
        employeePasswordTextField.setText("");
        employeeUsernameTextField.setText("");
        employeesTable.setModel(new EmployeeTableModel());
        employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void initCategoryListBox() {
        disableCategoryButtons();

        CategoryListModel model = new CategoryListModel();
        categoryList.setModel(model);

        categoryList.addListSelectionListener((e) -> {
            editCategoryButton.setVisible(true);
            deleteCategoryButton.setVisible(true);

//            JOptionPane.showMessageDialog(this, categoryList.getSelectedValue().getLoaiSanPhamId());
//            categoryList.getSelectedValue().getSanPhams().forEach(sp -> System.out.println(sp));
        });

    }

    private void initDiscountList() {
        DiscountListModel model = new DiscountListModel();
        discountList.setModel(model);

    }

    public void disableCategoryButtons() {
        editCategoryButton.setVisible(false);
        deleteCategoryButton.setVisible(false);
    }

    public void disableProductButtons() {
        editProductButton.setVisible(false);
        deleteProductButton.setVisible(false);
    }

    private void InsertData() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createProductPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        createProductNameTextField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        createProductPriceTextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        createProductQuantityTextField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        createProductCategoryComboBox = new javax.swing.JComboBox<>();
        TabbedPane = new javax.swing.JTabbedPane();
        homePanel = new javax.swing.JPanel();
        orderPanel = new javax.swing.JPanel();
        orderTableIdComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        foodComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        foodQuantityTextField = new javax.swing.JTextField();
        drinkQuantityTextField = new javax.swing.JTextField();
        drinkComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        addOrUpdateDrink = new javax.swing.JButton();
        addOrUpdateFood = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDetailTable = new javax.swing.JTable();
        deleteOrderedRowButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        discountComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        checkoutButton = new javax.swing.JButton();
        checkoutTableIdComboBox = new javax.swing.JComboBox<>();
        totalLabel = new javax.swing.JLabel();
        totalFreeLabel = new javax.swing.JLabel();
        billPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        receiptTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        receiptFindButton = new javax.swing.JButton();
        receptIdSearchTextField = new javax.swing.JTextField();
        viewReceiptDetailButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        receiptDetailTable = new javax.swing.JTable();
        statisticPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        revenueByMonth = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        receiptByMonthTotalLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        receiptsByMonthTable = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        totalValueLabel = new javax.swing.JLabel();
        employeePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        employeesTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        employeeBirthdayTextField = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        employeeHiredDateTextField = new javax.swing.JTextField();
        addEmployeeButton = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        employeeUsernameTextField = new javax.swing.JTextField();
        employeePasswordTextField = new javax.swing.JPasswordField();
        jLabel32 = new javax.swing.JLabel();
        deleteEmployeeButton = new javax.swing.JButton();
        editEmployeeButton = new javax.swing.JButton();
        updateEmployeeButton = new javax.swing.JButton();
        foodAndBeveragePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        categoryList = new javax.swing.JList<>();
        editCategoryButton = new javax.swing.JButton();
        addCategoryButton = new javax.swing.JButton();
        deleteCategoryButton = new javax.swing.JButton();
        deleteProductButton = new javax.swing.JButton();
        editProductButton = new javax.swing.JButton();
        addProductButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        settingPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tableStartIdTextField = new javax.swing.JTextField();
        numberOfTableTextField = new javax.swing.JTextField();
        updateTableNumberButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        discountPercentTextField = new javax.swing.JTextField();
        addDiscountButton = new javax.swing.JButton();
        deleteDiscountButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        currentTableIdStartTextField = new javax.swing.JTextField();
        currentTableNumberTextField = new javax.swing.JTextField();
        currentTableIdEndTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        discountList = new javax.swing.JList<>();
        jLabel26 = new javax.swing.JLabel();
        helpPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setText("Thêm mới sản phẩm");

        jLabel22.setText("Tên sản phẩm");

        jLabel23.setText("Đơn giá");

        jLabel24.setText("Số lượng");

        createProductQuantityTextField.setText("0");

        jLabel25.setText("Loại sản phẩm");

        createProductCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout createProductPanelLayout = new javax.swing.GroupLayout(createProductPanel);
        createProductPanel.setLayout(createProductPanelLayout);
        createProductPanelLayout.setHorizontalGroup(
            createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createProductPanelLayout.createSequentialGroup()
                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createProductPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel21))
                    .addGroup(createProductPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(createProductPanelLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(createProductCategoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(createProductPanelLayout.createSequentialGroup()
                                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(createProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createProductPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        createProductPanelLayout.setVerticalGroup(
            createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createProductPanelLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(createProductCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(createProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(createProductPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(createProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel24)
                    .addComponent(createProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabbedPane.setToolTipText("");

        orderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Đặt bàn"));

        jLabel4.setText("Bàn");

        jLabel5.setText("Thức uống");

        jLabel8.setText("Số lượng");

        jLabel6.setText("Đồ ăn");

        foodQuantityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodQuantityTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Số lượng");

        addOrUpdateDrink.setText("Thêm");
        addOrUpdateDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrUpdateDrinkActionPerformed(evt);
            }
        });

        addOrUpdateFood.setText("Thêm");
        addOrUpdateFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrUpdateFoodActionPerformed(evt);
            }
        });

        tableDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableDetailTable);

        deleteOrderedRowButton.setText("Xóa");
        deleteOrderedRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteOrderedRowButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout orderPanelLayout = new javax.swing.GroupLayout(orderPanel);
        orderPanel.setLayout(orderPanelLayout);
        orderPanelLayout.setHorizontalGroup(
            orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(orderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(foodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(orderPanelLayout.createSequentialGroup()
                        .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orderTableIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(drinkComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(orderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(foodQuantityTextField))
                    .addGroup(orderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(drinkQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addOrUpdateDrink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addOrUpdateFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteOrderedRowButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );
        orderPanelLayout.setVerticalGroup(
            orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(orderTableIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteOrderedRowButton))
                .addGap(18, 18, 18)
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(drinkComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(drinkQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOrUpdateDrink))
                .addGap(18, 18, 18)
                .addGroup(orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(foodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(foodQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOrUpdateFood))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh toán"));

        jLabel10.setText("Giảm giá");

        jLabel9.setText("Bàn");

        checkoutButton.setText("Thanh toán");
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });

        totalLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        totalLabel.setText("Thành tiền");

        totalFreeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalFreeLabel.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(40, 40, 40)
                        .addComponent(checkoutTableIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(totalLabel)
                        .addGap(28, 28, 28)
                        .addComponent(totalFreeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(checkoutTableIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutButton)
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(totalLabel)
                    .addComponent(totalFreeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(orderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Trang chính", homePanel);

        jScrollPane2.setViewportView(receiptTable);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm hóa đơn"));

        jLabel2.setText("Mã hóa đơn");

        receiptFindButton.setText("Tìm kiếm");
        receiptFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiptFindButtonActionPerformed(evt);
            }
        });

        viewReceiptDetailButton.setText("Xem chi tiết hóa đơn");
        viewReceiptDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewReceiptDetailButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(receptIdSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(receiptFindButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewReceiptDetailButton)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(receiptFindButton)
                    .addComponent(receptIdSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(viewReceiptDetailButton)))
        );

        jLabel3.setText("Chi tiết hóa đơn");

        jScrollPane8.setViewportView(receiptDetailTable);

        javax.swing.GroupLayout billPanelLayout = new javax.swing.GroupLayout(billPanel);
        billPanel.setLayout(billPanelLayout);
        billPanelLayout.setHorizontalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel3))
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        billPanelLayout.setVerticalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        TabbedPane.addTab("Hóa đơn", billPanel);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Theo thời gian"));

        jLabel11.setText("Danh sách hóa đơn tháng");

        revenueByMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        revenueByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueByMonthActionPerformed(evt);
            }
        });

        jLabel35.setText("Tổng trị giá");

        receiptByMonthTotalLabel.setText("VND");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(revenueByMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(receiptByMonthTotalLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(revenueByMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(receiptByMonthTotalLabel))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        receiptsByMonthTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(receiptsByMonthTable);

        jLabel18.setText("Tổng giá trị");

        javax.swing.GroupLayout statisticPanelLayout = new javax.swing.GroupLayout(statisticPanel);
        statisticPanel.setLayout(statisticPanelLayout);
        statisticPanelLayout.setHorizontalGroup(
            statisticPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statisticPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(statisticPanelLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(371, Short.MAX_VALUE))
        );
        statisticPanelLayout.setVerticalGroup(
            statisticPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(statisticPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(totalValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Thống kê", statisticPanel);

        employeesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(employeesTable);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tạo mới nhân viên"));

        jLabel27.setText("Họ tên");

        jLabel28.setText("Ngày sinh");

        jLabel29.setText("Ngày vào làm");

        addEmployeeButton.setText("Thêm mới");
        addEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeButtonActionPerformed(evt);
            }
        });

        jLabel30.setText("dd/mm/yyyy");

        jLabel31.setText("dd/mm/yyyy");

        jLabel33.setText("Tài khoản");

        jLabel34.setText("Mật khẩu");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(40, 40, 40)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addEmployeeButton)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(employeePasswordTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeUsernameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeNameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeBirthdayTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employeeHiredDateTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(employeeBirthdayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(employeeHiredDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(employeeUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(employeePasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(addEmployeeButton)
                .addContainerGap())
        );

        jLabel32.setText("Danh sách nhân viên");

        deleteEmployeeButton.setText("Xóa");
        deleteEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmployeeButtonActionPerformed(evt);
            }
        });

        editEmployeeButton.setText("Sửa");
        editEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEmployeeButtonActionPerformed(evt);
            }
        });

        updateEmployeeButton.setText("Lưu");
        updateEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmployeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeePanelLayout = new javax.swing.GroupLayout(employeePanel);
        employeePanel.setLayout(employeePanelLayout);
        employeePanelLayout.setHorizontalGroup(
            employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeePanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeePanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel32)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editEmployeeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateEmployeeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteEmployeeButton)
                .addGap(188, 188, 188))
        );
        employeePanelLayout.setVerticalGroup(
            employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeePanelLayout.createSequentialGroup()
                .addGroup(employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeePanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(employeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(editEmployeeButton)
                    .addComponent(updateEmployeeButton)
                    .addComponent(deleteEmployeeButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        TabbedPane.addTab("Nhân viên", employeePanel);

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        productTable.setName("foodCategoryTable"); // NOI18N
        jScrollPane1.setViewportView(productTable);

        jScrollPane6.setViewportView(categoryList);

        editCategoryButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Edit\\Edit_48x48.png")); // NOI18N
        editCategoryButton.setToolTipText("Chỉnh sửa loại sản phẩm");
        editCategoryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        editCategoryButton.setPreferredSize(new java.awt.Dimension(81, 81));
        editCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCategoryButtonActionPerformed(evt);
            }
        });

        addCategoryButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Add\\Add_48x48.png")); // NOI18N
        addCategoryButton.setToolTipText("Thêm loại sản phẩm");
        addCategoryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addCategoryButton.setPreferredSize(new java.awt.Dimension(81, 81));
        addCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryButtonActionPerformed(evt);
            }
        });

        deleteCategoryButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Delete\\Delete_48x48.png")); // NOI18N
        deleteCategoryButton.setToolTipText("Xóa loại sản phẩm");
        deleteCategoryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deleteCategoryButton.setPreferredSize(new java.awt.Dimension(81, 81));
        deleteCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryButtonActionPerformed(evt);
            }
        });

        deleteProductButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Delete\\Delete_48x48.png")); // NOI18N
        deleteProductButton.setToolTipText("Xóa thông tin sản phẩm");
        deleteProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deleteProductButton.setPreferredSize(new java.awt.Dimension(81, 81));
        deleteProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductButtonActionPerformed(evt);
            }
        });

        editProductButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Edit\\Edit_48x48.png")); // NOI18N
        editProductButton.setToolTipText("Chỉnh sửa sản phẩm");
        editProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        editProductButton.setPreferredSize(new java.awt.Dimension(81, 81));
        editProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductButtonActionPerformed(evt);
            }
        });

        addProductButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Downloads\\Compressed\\must_have_icons_icons_pack_120704\\must_have_icon_set\\Add\\Add_48x48.png")); // NOI18N
        addProductButton.setToolTipText("Thêm mới sản phẩm");
        addProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addProductButton.setPreferredSize(new java.awt.Dimension(81, 81));
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });

        jLabel19.setText("Loại sản phẩm");

        jLabel20.setText("Danh sách sản phẩm");

        javax.swing.GroupLayout foodAndBeveragePanelLayout = new javax.swing.GroupLayout(foodAndBeveragePanel);
        foodAndBeveragePanel.setLayout(foodAndBeveragePanelLayout);
        foodAndBeveragePanelLayout.setHorizontalGroup(
            foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27))
        );
        foodAndBeveragePanelLayout.setVerticalGroup(
            foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, foodAndBeveragePanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                                .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(editProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 180, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(foodAndBeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(foodAndBeveragePanelLayout.createSequentialGroup()
                                .addComponent(addCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(editCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteCategoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane6))))
                .addContainerGap())
        );

        TabbedPane.addTab("Thức ăn / Đồ uống", foodAndBeveragePanel);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhật số lượng bàn"));

        jLabel13.setText("Bắt đầu từ");

        jLabel12.setText("Số lượng");

        updateTableNumberButton.setText("Cập nhật");
        updateTableNumberButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableNumberButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(numberOfTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableStartIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateTableNumberButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(numberOfTableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(tableStartIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateTableNumberButton))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Khuyến mãi"));

        jLabel15.setText("Phần trăm");

        addDiscountButton.setText("Thêm");
        addDiscountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDiscountButtonActionPerformed(evt);
            }
        });

        deleteDiscountButton.setText("Xóa");
        deleteDiscountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDiscountButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(discountPercentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addDiscountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteDiscountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(discountPercentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addDiscountButton)
                    .addComponent(deleteDiscountButton))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sô lượng bàn hiện tại"));

        jLabel14.setText("Bắt đầu");

        jLabel16.setText("Số lượng");

        currentTableIdStartTextField.setEditable(false);

        currentTableNumberTextField.setEditable(false);

        currentTableIdEndTextField.setEditable(false);

        jLabel17.setText("Kết thúc");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(currentTableNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currentTableIdStartTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currentTableIdEndTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(currentTableIdEndTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(currentTableIdStartTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(currentTableNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jScrollPane7.setViewportView(discountList);

        jLabel26.setText("Danh sách khuyến mãi");

        javax.swing.GroupLayout settingPanelLayout = new javax.swing.GroupLayout(settingPanel);
        settingPanel.setLayout(settingPanelLayout);
        settingPanelLayout.setHorizontalGroup(
            settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane7)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(508, Short.MAX_VALUE))
        );
        settingPanelLayout.setVerticalGroup(
            settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingPanelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        TabbedPane.addTab("Thiết lập", settingPanel);

        javax.swing.GroupLayout helpPanelLayout = new javax.swing.GroupLayout(helpPanel);
        helpPanel.setLayout(helpPanelLayout);
        helpPanelLayout.setHorizontalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
        );
        helpPanelLayout.setVerticalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        TabbedPane.addTab("Hướng dẫn", helpPanel);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Coffee Shop Manager 1.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabbedPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(311, 311, 311)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOrUpdateDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrUpdateDrinkActionPerformed
        // TODO add your handling code here:

        String drinkQuantityText = drinkQuantityTextField.getText();

        if (drinkQuantityText.equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int drinkQuantity = 0;

        try {
            if (!drinkQuantityText.equals("")) {
                drinkQuantity = Integer.parseInt(drinkQuantityText);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thông tin số lượng đồ uống không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        orderedTableRepo.createOrUpdate(new DatBan(drinkQuantity, (SanPham) drinkComboBox.getSelectedItem(), (Ban) orderTableIdComboBox.getSelectedItem()));
        initOrderingTable();
        initOrderedTable();
        drinkQuantityTextField.setText("");
    }//GEN-LAST:event_addOrUpdateDrinkActionPerformed

    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutButtonActionPerformed
        // TODO add your handling code here:
        Ban table = (Ban) checkoutTableIdComboBox.getSelectedItem();

        if (table == null) {
            JOptionPane.showMessageDialog(this, "Chưa có bàn nào để thanh toán");
        }

        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thanh toán bàn " + table.getSoBan(), "Thanh toán", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            GiamGia discount = (GiamGia) discountComboBox.getSelectedItem();
            List<DatBan> datBans = orderedTableRepo.getByTableId(table.getBanId());
            List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<ChiTietHoaDon>();
            int total = 0;

            for (DatBan datBan : datBans) {
                total += datBan.getSanPham().getDonGia().intValue() * datBan.getSoLuong();
            }

            int receiptId = receiptRepository.create(new HoaDon(new BigDecimal(total), discount.getPhanTram(), table, loggedUser));

            HoaDon hoaDon = receiptRepository.get(receiptId);
            for (DatBan datBan : datBans) {
                ChiTietHoaDon receiptDetail = new ChiTietHoaDon(hoaDon, datBan.getSanPham(), datBan.getSoLuong());
                receiptDetailRepository.create(receiptDetail);
            }

            table.setConTrong(true);
            table.setUpdateDate(new java.util.Date());
            tableRepo.update(table);

            totalLabel.setText("Thành tiền bàn " + table.getSoBan() + ": ");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("Vietnamese", "Vietnam"));
            totalFreeLabel.setText(formatter.format(total - total * discount.getPhanTram() * 0.01) + " VND giảm (" + discount.getPhanTram() + "%)");

            initTableOrderTab();
            initReceiptTab(null);

        }


    }//GEN-LAST:event_checkoutButtonActionPerformed

    private void addCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryButtonActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog(this, "Nhập tên loại sản phẩm");
        if (name != null && !name.isEmpty()) {
            try {
                CategoryListModel model = (CategoryListModel) categoryList.getModel();
                List<String> categories = new ArrayList<>();
                model.getData().forEach(c -> {
                    categories.add(c.getTenLoaiSanPham());

                });
                if (categories.contains(name)) {
                    JOptionPane.showMessageDialog(this, "Trùng tên loại sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                categoryRepo.create(new LoaiSanPham(name));
                JOptionPane.showMessageDialog(this, "Tạo thành công loại sản phẩm mới");
                initCategoryListBox();
                initTableOrderTab();
                disableCategoryButtons();
            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Lỗi khi thêm loại sản phẩm. ");
            }

        }


    }//GEN-LAST:event_addCategoryButtonActionPerformed

    private void editCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCategoryButtonActionPerformed
        editCategory();
        initProductTable();
        initTableOrderTab();
    }//GEN-LAST:event_editCategoryButtonActionPerformed

    private void editCategory() {
        int selectedIndex = categoryList.getSelectedIndex();

        if (selectedIndex == -1) {

        }

        LoaiSanPham loaiSanPham = categoryList.getSelectedValue();

        String name = JOptionPane.showInputDialog(this, "Nhập tên loại sản phẩm mới - " + loaiSanPham.getTenLoaiSanPham());
        if (name != null && !name.isEmpty()) {

            loaiSanPham.setTenLoaiSanPham(name);

            System.out.println(categoryRepo.update(loaiSanPham));
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin loại sản phẩm thành công");
            initCategoryListBox();

            disableCategoryButtons();
        }
    }

    private void deleteCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryButtonActionPerformed
        // TODO add your handling code here:

        int dialogResult = JOptionPane.showConfirmDialog(this, "Xóa loại sản phẩm này sẽ xóa tất cả các sản phẩm cùng loại. Bạn có chắc chắn muốn xóa?", "Cảnh báo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {

            LoaiSanPham loaiSanPham = categoryList.getSelectedValue();
            try {
                categoryRepo.delete(loaiSanPham.getLoaiSanPhamId());
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công");
                initCategoryListBox();
                initProductTable();
                initTableOrderTab();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            disableCategoryButtons();
        }


    }//GEN-LAST:event_deleteCategoryButtonActionPerformed

    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed
        // TODO add your handling code here:

        EditProductPanel p = new EditProductPanel();
        int result = JOptionPane.showConfirmDialog(this, p, "Thêm mới sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = p.getNameTextField().getText();
                BigDecimal price = new BigDecimal(p.getPriceTextField().getText());
                int quantity = Integer.parseInt(p.getQuantityTextField().getText());
                LoaiSanPham category = (LoaiSanPham) p.getCategoryComboBox().getSelectedItem();
                SanPham sp = new SanPham(name, price, quantity, category);

                productRepo.create(sp);
                initProductTable();
                initTableOrderTab();

                Utilities.Log(Level.INFO, sp.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu thiếu hoặc không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_addProductButtonActionPerformed

    private void editProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductButtonActionPerformed

        int row = productTable.getSelectedRow();
        int id = (int) productTable.getValueAt(row, 0);
        SanPham sp = productRepo.get(id);

        EditProductPanel p = new EditProductPanel();

        p.getNameTextField().setText(sp.getTenSanPham());
        p.getPriceTextField().setText(sp.getDonGia().toString());
        p.getQuantityTextField().setText(sp.getSoLuong().toString());

        p.getCategoryComboBox().setSelectedItem(sp.getLoaiSanPham());

        int result = JOptionPane.showConfirmDialog(this, p, "Cập nhật thông tin sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = p.getNameTextField().getText();
                BigDecimal price = new BigDecimal(p.getPriceTextField().getText());
                int quantity = Integer.parseInt(p.getQuantityTextField().getText());
                LoaiSanPham category = (LoaiSanPham) p.getCategoryComboBox().getSelectedItem();
                sp.setTenSanPham(name);
                sp.setDonGia(price);
                sp.setSoLuong(quantity);
                sp.setLoaiSanPham(category);

                productRepo.update(sp);
                initProductTable();
                initTableOrderTab();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu thiếu hoặc không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_editProductButtonActionPerformed

    private void deleteProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductButtonActionPerformed
        int row = productTable.getSelectedRow();
        int id = (int) productTable.getValueAt(row, 0);
        SanPham sp = productRepo.get(id);

        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm: " + sp.getTenSanPham(), "Xóa sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                productRepo.delete(sp.getSanPhamId());
            }
            initProductTable();
            initTableOrderTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công");
        }
    }//GEN-LAST:event_deleteProductButtonActionPerformed

    private void updateTableNumberButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateTableNumberButtonActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Cập nhật lại bàn sẽ xóa tất cả các bàn đang được đặt. Bạn có chắc chắc muốn thực hiện thao tác này?", "Cảnh báo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int number = Integer.parseInt(numberOfTableTextField.getText());
                int start = Integer.parseInt(tableStartIdTextField.getText());
                tableRepo.create(start, number);
                initTableOrderTab();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi nhập thiếu hoặc sai thông tin ");

            }
        }

        initCurrentTables();

    }//GEN-LAST:event_updateTableNumberButtonActionPerformed

    private void addDiscountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDiscountButtonActionPerformed
        // TODO add your handling code here:
        try {
            int percent = Integer.parseInt(discountPercentTextField.getText());

            List<GiamGia> discounts = discountRepo.get();
            for (GiamGia discount : discounts) {
                if (discount.getPhanTram() == percent) {
                    JOptionPane.showMessageDialog(this, "Trùng thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            discountRepo.create(new GiamGia(percent));
            initDiscountList();
            initDiscountComboBox();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thông tin thiếu hoặc sai định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addDiscountButtonActionPerformed

    private void deleteDiscountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDiscountButtonActionPerformed
        // TODO add your handling code here:

        if (discountList.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn đối tượng để xóa");
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn chắc xóa giảm giá này?", "Cảnh báo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {

            GiamGia giamGia = discountList.getSelectedValue();
            try {
                discountRepo.delete(giamGia.getGiamGiaId());
                initDiscountList();
                initDiscountComboBox();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa giảm giá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_deleteDiscountButtonActionPerformed

    private void addEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeButtonActionPerformed
        // TODO add your handling code here:

        String name = employeeNameTextField.getText();
        String birthdayText = employeeBirthdayTextField.getText();
        String hiredDateText = employeeHiredDateTextField.getText();
        String username = employeeUsernameTextField.getText();
        String password = employeePasswordTextField.getText();

        Date birthday = null;
        Date hiredDay = null;

        if (name == null || name.isEmpty() || birthdayText == null || birthdayText.isEmpty() || hiredDateText == null || hiredDateText.isEmpty()
                || username == null || username.isEmpty() || password == null || password.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Thiếu thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;

        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try {
            birthday = df.parse(birthdayText);
            hiredDay = df.parse(hiredDateText);
            System.out.println(birthday);
            System.out.println(hiredDay);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi ngày sinh", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            employeeRepo.create(new NhanVien(name, birthday, hiredDay, username, password));
            initEmployeeTab();
            JOptionPane.showMessageDialog(this, "Thêm mới nhan viên thành công");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo nhân viên mới", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_addEmployeeButtonActionPerformed

    private void editEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEmployeeButtonActionPerformed
        // TODO add your handling code here:
        int row = employeesTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên muốn sửa");
            return;
        }
        int id = (int) employeesTable.getValueAt(row, 0);

        NhanVien nhanVien = employeeRepo.get(id);

        if (nhanVien != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            employeeNameTextField.setText(nhanVien.getHoTen());
            employeeBirthdayTextField.setText(df.format(nhanVien.getNgaySinh()));
            employeeHiredDateTextField.setText(df.format(nhanVien.getNgayVaoLam()));
            employeeUsernameTextField.setText(nhanVien.getUsername());
            employeePasswordTextField.setText(nhanVien.getPassword());
            editingEmployeeId = id;
        }

    }//GEN-LAST:event_editEmployeeButtonActionPerformed

    private void updateEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmployeeButtonActionPerformed

        if (editingEmployeeId == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cập nhật");
            return;
        }
        String name = employeeNameTextField.getText();
        String birthdayText = employeeBirthdayTextField.getText();
        String hiredDateText = employeeHiredDateTextField.getText();
        String username = employeeUsernameTextField.getText();
        String password = employeePasswordTextField.getText();

        Date birthday = null;
        Date hiredDay = null;

        if (name == null || name.isEmpty() || birthdayText == null || birthdayText.isEmpty() || hiredDateText == null || hiredDateText.isEmpty()
                || username == null || username.isEmpty() || password == null || password.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Thiếu thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;

        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try {
            birthday = df.parse(birthdayText);
            hiredDay = df.parse(hiredDateText);
            System.out.println(birthday);
            System.out.println(hiredDay);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi ngày sinh", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NhanVien nhanVien = employeeRepo.get(editingEmployeeId);
        if (nhanVien != null) {
            nhanVien.setHoTen(name);
            nhanVien.setNgaySinh(hiredDay);
            nhanVien.setNgayVaoLam(hiredDay);
            nhanVien.setUsername(username);
            nhanVien.setPassword(password);

            try {
                employeeRepo.update(nhanVien);
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công");
                editingEmployeeId = 0;

                initEmployeeTab();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_updateEmployeeButtonActionPerformed

    private void deleteEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmployeeButtonActionPerformed
        // TODO add your handling code here:
        int row = employeesTable.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên muốn xóa");
            return;
        }
        int id = (int) employeesTable.getValueAt(row, 0);

        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên: " + employeeRepo.get(id).getHoTen(), "Xóa nhân viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                employeeRepo.delete(id);
                JOptionPane.showMessageDialog(this, "Xóa thành công nhân viên");
                initEmployeeTab();
            }
            initProductTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_deleteEmployeeButtonActionPerformed

    private void foodQuantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodQuantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodQuantityTextFieldActionPerformed

    private void addOrUpdateFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrUpdateFoodActionPerformed
        // TODO add your handling code here:
        String foodQuantityText = foodQuantityTextField.getText();

        if (foodQuantityText.equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int foodQuantity = 0;

        try {
            if (!foodQuantityText.equals("")) {
                foodQuantity = Integer.parseInt(foodQuantityText);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thông tin số lượng đồ không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        foodQuantityTextField.setText("");

        orderedTableRepo.createOrUpdate(new DatBan(foodQuantity, (SanPham) foodComboBox.getSelectedItem(), (Ban) orderTableIdComboBox.getSelectedItem()));
        initOrderingTable();
        initOrderedTable();

    }//GEN-LAST:event_addOrUpdateFoodActionPerformed

    private void deleteOrderedRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteOrderedRowButtonActionPerformed
        // TODO add your handling code here:

        int selectedRow = tableDetailTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng để xóa");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa", "Xóa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            SanPham sp = (SanPham) tableDetailTable.getValueAt(selectedRow, 0);

            orderedTableRepo.createOrUpdate(new DatBan(0, sp, (Ban) orderTableIdComboBox.getSelectedItem()));

            initTableOrderTab();
        }
    }//GEN-LAST:event_deleteOrderedRowButtonActionPerformed

    private void receiptFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiptFindButtonActionPerformed
        // TODO add your handling code here:

        int id = 0;
        try {
            id = Integer.parseInt(receptIdSearchTextField.getText());
        } catch (Exception e) {
            initReceiptTab(null);
        }

        List<HoaDon> receipts = new ArrayList<HoaDon>();
        HoaDon hoaDon = receiptRepository.get(id);
        if (hoaDon != null) {
            receipts.add(hoaDon);
            initReceiptTab(receipts);

        } else {
            initReceiptTab(null);
        }


    }//GEN-LAST:event_receiptFindButtonActionPerformed

    private void viewReceiptDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewReceiptDetailButtonActionPerformed
        // TODO add your handling code here:

        int row = receiptTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết");
            return;
        }

        int id = (int) receiptTable.getValueAt(row, 0);

        List<ChiTietHoaDon> chiTietHoaDons = receiptDetailRepository.getByReceiptId(id);
        receiptDetailTable.setModel(new ReceiptDetailTableModel(chiTietHoaDons));

    }//GEN-LAST:event_viewReceiptDetailButtonActionPerformed

    private void revenueByMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revenueByMonthActionPerformed
        // TODO add your handling code here:

        int month = Integer.parseInt(revenueByMonth.getSelectedItem().toString());

        List<HoaDon> receipts = receiptRepository.get();

        List<HoaDon> filteredReceipts = new ArrayList<HoaDon>();

        for (HoaDon receipt : receipts) {
            if (receipt.getNgayHoaDon().getMonth() + 1 == month && receipt.getNgayHoaDon().getYear() == new java.util.Date().getYear()) {
                filteredReceipts.add(receipt);
            }
        }

        double total = 0;
        for (HoaDon hoaDon : filteredReceipts) {
            total += hoaDon.getTongTriGia().doubleValue();
        }

        receiptByMonthTotalLabel.setText(Double.toString(total) + " VND");
        receiptsByMonthTable.setModel(new ReceiptTableModel(filteredReceipts));
    }//GEN-LAST:event_revenueByMonthActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JButton addCategoryButton;
    private javax.swing.JButton addDiscountButton;
    private javax.swing.JButton addEmployeeButton;
    private javax.swing.JButton addOrUpdateDrink;
    private javax.swing.JButton addOrUpdateFood;
    private javax.swing.JButton addProductButton;
    private javax.swing.JPanel billPanel;
    private javax.swing.JList<LoaiSanPham> categoryList;
    private javax.swing.JButton checkoutButton;
    private javax.swing.JComboBox<Ban> checkoutTableIdComboBox;
    private javax.swing.JComboBox<String> createProductCategoryComboBox;
    private javax.swing.JTextField createProductNameTextField;
    private javax.swing.JPanel createProductPanel;
    private javax.swing.JTextField createProductPriceTextField;
    private javax.swing.JTextField createProductQuantityTextField;
    private javax.swing.JTextField currentTableIdEndTextField;
    private javax.swing.JTextField currentTableIdStartTextField;
    private javax.swing.JTextField currentTableNumberTextField;
    private javax.swing.JButton deleteCategoryButton;
    private javax.swing.JButton deleteDiscountButton;
    private javax.swing.JButton deleteEmployeeButton;
    private javax.swing.JButton deleteOrderedRowButton;
    private javax.swing.JButton deleteProductButton;
    private javax.swing.JComboBox<GiamGia> discountComboBox;
    private javax.swing.JList<GiamGia> discountList;
    private javax.swing.JTextField discountPercentTextField;
    private javax.swing.JComboBox<SanPham> drinkComboBox;
    private javax.swing.JTextField drinkQuantityTextField;
    private javax.swing.JButton editCategoryButton;
    private javax.swing.JButton editEmployeeButton;
    private javax.swing.JButton editProductButton;
    private javax.swing.JTextField employeeBirthdayTextField;
    private javax.swing.JTextField employeeHiredDateTextField;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel employeePanel;
    private javax.swing.JPasswordField employeePasswordTextField;
    private javax.swing.JTextField employeeUsernameTextField;
    private javax.swing.JTable employeesTable;
    private javax.swing.JPanel foodAndBeveragePanel;
    private javax.swing.JComboBox<SanPham> foodComboBox;
    private javax.swing.JTextField foodQuantityTextField;
    private javax.swing.JPanel helpPanel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField numberOfTableTextField;
    private javax.swing.JPanel orderPanel;
    private javax.swing.JComboBox<Ban> orderTableIdComboBox;
    private javax.swing.JTable productTable;
    private javax.swing.JLabel receiptByMonthTotalLabel;
    private javax.swing.JTable receiptDetailTable;
    private javax.swing.JButton receiptFindButton;
    private javax.swing.JTable receiptTable;
    private javax.swing.JTable receiptsByMonthTable;
    private javax.swing.JTextField receptIdSearchTextField;
    private javax.swing.JComboBox<String> revenueByMonth;
    private javax.swing.JPanel settingPanel;
    private javax.swing.JPanel statisticPanel;
    private javax.swing.JTable tableDetailTable;
    private javax.swing.JTextField tableStartIdTextField;
    private javax.swing.JLabel totalFreeLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel totalValueLabel;
    private javax.swing.JButton updateEmployeeButton;
    private javax.swing.JButton updateTableNumberButton;
    private javax.swing.JButton viewReceiptDetailButton;
    // End of variables declaration//GEN-END:variables

    private void initTableOrderTab() {
        initOrderComboBox();

        initOrderedTable();

        initFoodComboBox();

        initDrinkComboBox();

        initOrderingTable();

        initDiscountComboBox();

    }

    public void initDrinkComboBox() {
        List<SanPham> products = productRepo.get();
        List<SanPham> drinkList = products.stream()
                .filter(p -> p.getLoaiSanPham().getTenLoaiSanPham().equals("Thức uống"))
                .collect(Collectors.toList());

        SanPham[] drink = new SanPham[drinkList.size()];
        drink = drinkList.toArray(drink);
        DrinkComboBoxModel drinkComboBoxModel = new DrinkComboBoxModel(drink);
        drinkComboBox.setModel(drinkComboBoxModel);
    }

    public void initFoodComboBox() {
        List<SanPham> products = productRepo.get();

        List<SanPham> foodList = products.stream()
                .filter(p -> p.getLoaiSanPham().getTenLoaiSanPham().equals("Đồ ăn"))
                .collect(Collectors.toList());

        SanPham[] food = new SanPham[foodList.size()];
        food = foodList.toArray(food);
        FoodComboBoxModel foodComboBoxModel = new FoodComboBoxModel(food);
        foodComboBox.setModel(foodComboBoxModel);
    }

    public void initOrderComboBox() {
        // List of all tables
        List<Ban> tablesList = tableRepo.get();
        Ban[] tables = new Ban[tablesList.size()];

        tables = tablesList.toArray(tables);
        TablesComboBoxModel tableComboBoxModel = new TablesComboBoxModel(tables);

        orderTableIdComboBox.setModel(tableComboBoxModel);
        orderTableIdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initOrderingTable();
            }
        });
    }

    public void initOrderedTable() {
        // List of ordered tables
        List<Ban> tablesList = tableRepo.get();
        List<Ban> emptyTablesList = tablesList.stream()
                .filter(t -> !t.getConTrong())
                .collect(Collectors.toList());

        Ban[] emptyTables = new Ban[emptyTablesList.size()];
        emptyTables = emptyTablesList.toArray(emptyTables);
        OrderedTablesComboBoxModel orderedTablesComboBoxModel = new OrderedTablesComboBoxModel(emptyTables);
        checkoutTableIdComboBox.setModel(orderedTablesComboBoxModel);
    }

    private void initOrderingTable() {

        Ban b = (Ban) orderTableIdComboBox.getSelectedItem();
        OrderedTableTableModel model = new OrderedTableTableModel(b.getBanId());
        tableDetailTable.setModel(model);
        tableDetailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void initDiscountComboBox() {

        List<GiamGia> discountList = discountRepo.get();
        GiamGia[] discounts = new GiamGia[discountList.size()];
        discounts = discountList.toArray(discounts);
        DiscountComboBoxModel discountComboBoxModel = new DiscountComboBoxModel(discounts);
        discountComboBox.setModel(discountComboBoxModel);

    }

    private void initReceiptTab(List<HoaDon> receipts) {
        receiptTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receiptTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if (receipts == null) {
            receiptTable.setModel(new ReceiptTableModel());
        } else {
            receiptTable.setModel(new ReceiptTableModel(receipts));

        }
    }

    private void initReceiptsByMonthTable() {
//        receiptsByMonthTable.setModel(new ReceiptTableModel());
    }

}
