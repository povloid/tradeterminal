/*
 * incomProductsDPanel.java
 *
 * Created on 7 Март 2008 г., 7:18
 */
package tradeterminal.operations.products.incommingandoutcomming.p0;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import minersinstrument.db.ADBProc;
import minersinstrument.db.ADBProcParametr;
import minersinstrument.db.PADBUtils;
import minersinstrument.db.PAJDBCComboBoxModel;
import minersinstrument.ui.ADialog;
import minersinstrument.ui.ADialogFocusLisner;
import minersinstrument.ui.AErrorDialog;
import minersinstrument.ui.AUniversalAddDialog;
import minersinstrument.ui.AUniversalDialog;
import minersinstrument.ui.AUniversalEditDialog;
import minersinstrument.ui.IADialogPanel;
import org.jdesktop.application.Action;
import org.postgresql.ds.PGPoolingDataSource;
import tradeterminal.Setup;
import tradeterminal.conf.AppAccessSettings;
import tradeterminal.conf.User;
import tradeterminal.products.FindProdustsDialogPanel;
import tradeterminal.products.ProductsAddEditDialogPanel;
import tradeterminal.products_groups.ProgectsGroupsMainPanel;

/**
 *
 * @author  kopychenko
 */
public final class IncomAndOutcomProductsDPanel extends javax.swing.JPanel implements IADialogPanel {

    class Rb_measuresComboBoxModel extends PAJDBCComboBoxModel {

        JTextField updatedTextField;

        public void setUpdatedJTextField(JTextField textField) {
            this.updatedTextField = textField;
        }

        public Rb_measuresComboBoxModel(PGPoolingDataSource source) {
            super(source);
        }

        @Override
        protected PAJDBCCResult vExecuteQuery(Connection conn) throws SQLException {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call rb_measures_select() }");
            proc.registerOutParameter(1, Types.OTHER);

            proc.execute();

            return new PAJDBCCResult((ResultSet) proc.getObject(1), conn, proc);
        }

        @Override
        public void vAfterSetSelectedRowId(Row row) {
            if (updatedTextField != null && row != null) {
                updatedTextField.setText(row.getName());
            } else {
                updatedTextField.setText("");
            }
        }
    }
    private Rb_measuresComboBoxModel rb_measuresComboBoxModel = new Rb_measuresComboBoxModel(Setup.getSource());

    class products_groupsComboBoxModel extends PAJDBCComboBoxModel {

        JTextField updatedTextField;

        public void setUpdatedJTextField(JTextField textField) {
            this.updatedTextField = textField;
        }

        public products_groupsComboBoxModel(PGPoolingDataSource source) {
            super(source);
        }

        @Override
        protected PAJDBCCResult vExecuteQuery(Connection conn) throws SQLException {
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ ? = call products_groups_select_cb1() }");
            proc.registerOutParameter(1, Types.OTHER);

            proc.execute();

            return new PAJDBCCResult((ResultSet) proc.getObject(1), conn, proc);
        }

        @Override
        public void vAfterSetSelectedRowId(Row row) {
            if (updatedTextField != null && row != null) {
                updatedTextField.setText(row.getName());
                updatedTextField.selectAll();
            } else {
                updatedTextField.setText("");
            }
        }
    }
    private products_groupsComboBoxModel products_groupsComboBoxModel = new products_groupsComboBoxModel(Setup.getSource());
    private int id = -1;
    private Boolean flagMinus = false;
    private ADialogFocusLisner adf = new ADialogFocusLisner();

    /** Creates new form incomProductsDPanel */
    public IncomAndOutcomProductsDPanel(Boolean flagMinus) {
        initComponents();

        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(32, 32));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(32, 32));

        //if(flagMinus){
        addNewProductButton.setVisible(!flagMinus);
        //}


        this.flagMinus = flagMinus;

        if (flagMinus) {
            jLabel7.setText("Ост. стоим.");
        }

        descriptionTextArea.setBackground(nameTextField.getBackground());

        //measures_idComboBox.setModel(rb_measuresComboBoxModel);
        //product_groupsComboBox.setModel(products_groupsComboBoxModel);
        products_groupsComboBoxModel.setUpdatedJTextField(groupNameTextField);
        rb_measuresComboBoxModel.setUpdatedJTextField(measuresTextField);


        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().addFocusListener(adf);


        actual_priceSpinner.setModel(new SpinnerNumberModel(
                Double.valueOf(0.0f),
                Double.valueOf(0.0f),
                Double.valueOf(1000000000),
                Double.valueOf(0.01f)));

        ((JSpinner.DefaultEditor) actual_priceSpinner.getEditor()).getTextField().addFocusListener(adf);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        nameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        groupNameTextField = new javax.swing.JTextField();
        measuresTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        scodTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        operationDescriptionTextArea = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        quantitySpinner = new javax.swing.JSpinner();
        actual_priceSpinner = new javax.swing.JSpinner();
        addNewProductButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        find2Button = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(IncomAndOutcomProductsDPanel.class);
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(IncomAndOutcomProductsDPanel.class, this);
        jButton1.setAction(actionMap.get("findProductForScod")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setFont(resourceMap.getFont("descriptionTextArea.font")); // NOI18N
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setFocusable(false);
        descriptionTextArea.setName("descriptionTextArea"); // NOI18N
        jScrollPane1.setViewportView(descriptionTextArea);

        nameTextField.setEditable(false);
        nameTextField.setFocusable(false);
        nameTextField.setName("nameTextField"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        groupNameTextField.setEditable(false);
        groupNameTextField.setText(resourceMap.getString("groupNameTextField.text")); // NOI18N
        groupNameTextField.setFocusable(false);
        groupNameTextField.setName("groupNameTextField"); // NOI18N

        measuresTextField.setEditable(false);
        measuresTextField.setText(resourceMap.getString("measuresTextField.text")); // NOI18N
        measuresTextField.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        measuresTextField.setFocusable(false);
        measuresTextField.setName("measuresTextField"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        scodTextField.setText(resourceMap.getString("scodTextField.text")); // NOI18N
        scodTextField.setName("scodTextField"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        operationDescriptionTextArea.setColumns(20);
        operationDescriptionTextArea.setFont(resourceMap.getFont("descriptionTextArea.font")); // NOI18N
        operationDescriptionTextArea.setLineWrap(true);
        operationDescriptionTextArea.setRows(5);
        operationDescriptionTextArea.setWrapStyleWord(true);
        operationDescriptionTextArea.setName("operationDescriptionTextArea"); // NOI18N
        jScrollPane2.setViewportView(operationDescriptionTextArea);

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        quantitySpinner.setFont(resourceMap.getFont("quantitySpinner.font")); // NOI18N
        quantitySpinner.setName("quantitySpinner"); // NOI18N

        actual_priceSpinner.setFont(resourceMap.getFont("actual_priceSpinner.font")); // NOI18N
        actual_priceSpinner.setName("actual_priceSpinner"); // NOI18N

        addNewProductButton.setAction(actionMap.get("addNewProduct")); // NOI18N
        addNewProductButton.setName("addNewProductButton"); // NOI18N

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        find2Button.setAction(actionMap.get("find2")); // NOI18N
        find2Button.setText(resourceMap.getString("find2Button.text")); // NOI18N
        find2Button.setName("find2Button"); // NOI18N

        jButton3.setAction(actionMap.get("editProduct")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(find2Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addNewProductButton))
                    .addComponent(jScrollPane1)
                    .addComponent(nameTextField)
                    .addComponent(groupNameTextField)
                    .addComponent(scodTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(measuresTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(actual_priceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(scodTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(addNewProductButton)
                        .addComponent(find2Button)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(groupNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(measuresTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(actual_priceSpinner)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void findProductForScod() {
        Connection conn = null;

        try {
            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{ call find_product_for_scod_with_quantity(?,?,?,?) }");
            proc.registerOutParameter(2, Types.OTHER);
            proc.registerOutParameter(3, Types.NUMERIC);
            proc.registerOutParameter(4, Types.NUMERIC);

            proc.setObject(1, scodTextField.getText(), Types.VARCHAR);

            proc.execute();

            ResultSet resultSet = (ResultSet) proc.getObject(2);

            if (resultSet.next()) {
                id = resultSet.getInt(1);

                products_groupsComboBoxModel.setSelectedRowId(resultSet.getInt(2));
                nameTextField.setText(resultSet.getString(3));
                descriptionTextArea.setText(resultSet.getString(4));


                rb_measuresComboBoxModel.setSelectedRowId(resultSet.getInt(7));
                jLabel11.setText(resultSet.getString("list_price"));


                // Теперь установка параметров спинера
                double quantity;

                if (!flagMinus) {
                    quantity = 1000000000f;
                    actual_priceSpinner.setValue(resultSet.getFloat("list_price"));
                } else {
                    quantity = ((Number) proc.getObject(3)).doubleValue();
                }

                System.out.println(((Number) proc.getObject(4)).doubleValue());

                quantitySpinner.setModel(new SpinnerNumberModel(
                        Double.valueOf(0.0f),
                        Double.valueOf(0.0f),
                        Double.valueOf(quantity),
                        Double.valueOf(((Number) proc.getObject(4)).doubleValue())));

                //quantitySpinner.setValue(resultSet.getObject(6));



            } else {
                clearAllElements();
            }

            resultSet.close();
            proc.close();

        } catch (SQLException ex) {
            Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

            try {
                conn.rollback();
                // log error
            } catch (SQLException ex1) {
                Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex1);

                AErrorDialog der2 = new AErrorDialog("Ошибка SQL уровня...", ex1.getMessage());
                der2.setVisible(true);
                der2.dispose();

            }
        } catch (Exception ex) {
            Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
            }
        }

        quantitySpinner.requestFocus();
    }

    private void clearAllElements() {
        id = -1;

        products_groupsComboBoxModel.setSelectedRowId(-1);
        nameTextField.setText("");
        descriptionTextArea.setText("");
        //quantityFormattedTextField.setValue(0);
        quantitySpinner.setValue(0f);

        //actual_priceFormattedTextField.setValue(0);
        actual_priceSpinner.setValue(0f);

        rb_measuresComboBoxModel.setSelectedRowId(-1);
    }

    // Проверка и добавление в базу данных.
    @Override
    public boolean checkPanel() {


        // Проверка ввода штрихкода
        if (scodTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Введите номер штрихкода.",
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE);

            scodTextField.requestFocus();
            return false;
        }


        // Если курсор находится в поле ввода штрихкода
        if (scodTextField.isFocusOwner()) {
            findProductForScod();

            quantitySpinner.setValue(0f);
            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().addFocusListener(adf);
            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().requestFocus();

            return false;
        }

        // Проверка ввода количества товара
        try {
            quantitySpinner.commitEdit();
        } catch (ParseException ex) {
            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().addFocusListener(adf);
            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().requestFocus();
        }

        if (((Number) quantitySpinner.getValue()).doubleValue() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Введите количество товара.",
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE);

            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().addFocusListener(adf);
            ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().requestFocus();

            return false;
        }


        // Проверка ввода суммы закупки
        try {
            actual_priceSpinner.commitEdit();
        } catch (ParseException ex) {
            ((JSpinner.DefaultEditor) actual_priceSpinner.getEditor()).getTextField().requestFocus();
        }

        if (((Number) actual_priceSpinner.getValue()).doubleValue() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Введите сумму.",
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE);

            ((JSpinner.DefaultEditor) actual_priceSpinner.getEditor()).getTextField().requestFocus();
            return false;
        }


        // Если все введено верно то выполняем операцию на сервере
        Connection conn = null;


        try {
            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);

            CallableStatement proc = conn.prepareCall("{call receipts_of_the_product(?,?,?,?,?,?)}");
            proc.setBoolean(1, flagMinus);
            proc.setInt(2, id);

            //proc.setObject(3, actual_priceFormattedTextField.getValue());
            proc.setObject(3, actual_priceSpinner.getValue(), Types.NUMERIC);
            proc.setObject(4, quantitySpinner.getValue(), Types.NUMERIC);

            proc.setString(5, operationDescriptionTextArea.getText());
            proc.setInt(6, User.id);
            proc.execute(); // 454273

            proc.close();

            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

            try {
                conn.rollback();
                // log error
            } catch (SQLException ex1) {
                Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex1);

                AErrorDialog der2 = new AErrorDialog("Ошибка...", ex1.getMessage());
                der2.setVisible(true);
                der2.dispose();

            }
        } catch (Exception ex) {
            Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex);

            AErrorDialog der = new AErrorDialog("Ошибка...", ex.getMessage());
            der.setVisible(true);
            der.dispose();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(IncomAndOutcomProductsDPanel.class.getName()).log(Level.SEVERE, null, ex2);

                    AErrorDialog der = new AErrorDialog("Ошибка SQL уровня...", ex2.getMessage());
                    der.setVisible(true);
                    der.dispose();
                }
            }
            return true;
        }
    }

    @Override
    public void openPanel() {
        scodTextField.requestFocus();
    }

    @Action
    public void selectProductGroup() {
        ProgectsGroupsMainPanel p =
                new ProgectsGroupsMainPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/addbk_32.png")));
        d.setTitle("Выбор группы");
        d.setTitleText("Выбор группы");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            products_groupsComboBoxModel.setSelectedRowId(p.getSelectedGroupId());
        } else {
        }
    }

    @Action
    public void addNewProduct() {

        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        AddNewProductDPanel p = new AddNewProductDPanel(true);
        AUniversalAddDialog d = new AUniversalAddDialog(p, null, true);
        d.setTitle("Новый товар");
        d.setTitleText("Новый товар");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            clearAllElements();
            scodTextField.setText(p.getProductSCod().trim());
            findProductForScod();
        }
    }

    @Action
    public void find2() {

        FindProdustsDialogPanel p = new FindProdustsDialogPanel();
        AUniversalDialog d = new AUniversalDialog(p, null, true);
        d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/pokodu.png")));
        d.setTitle("Поиск");
        d.setTitleText("Поиск товара по содержимому штрихкода");

        d.setVisible(true);
        d.dispose();

        if (d.getReturnStatus() == ADialog.RET_OK) {
            scodTextField.setText(p.getSelectedScode());
            findProductForScod();
        }

    }

    @Action
    public void editProduct() {     
        // Проверка прав доступа
        if (!AppAccessSettings.isCurrentUserHasAccessWithMessage(
                AppAccessSettings.RB_EDIT)) {
            return;
        }

        Connection conn = null;

        try {

            conn = Setup.getSource().getConnection();
            conn.setAutoCommit(false);
            
            CallableStatement proc = conn.prepareCall("{ ? = call find_product_for_scod_2(?) }");
            proc.registerOutParameter(1, Types.OTHER);
            proc.setString(2, scodTextField.getText().trim());

            proc.execute();
            
            ResultSet rs = (ResultSet) proc.getObject(1);
            rs.next();    
            
            System.out.println("111");
            
            ProductsAddEditDialogPanel p = new ProductsAddEditDialogPanel();
            AUniversalEditDialog d = new AUniversalEditDialog(p, null, true);

            p.set_Name(rs.getString("product_name"));
            p.setDescription(rs.getString("description"));
            p.setScod(rs.getString("scod"));
            p.setQuantity(rs.getDouble("quantity"));
            p.setMeasures_id(rs.getInt("measures_id"));


            p.setList_price(((BigDecimal) rs.getBigDecimal("list_price")));
            p.setSpec_price(((BigDecimal) rs.getBigDecimal("spec_price")));
            p.setPercent_discount(((BigDecimal) rs.getBigDecimal("percent_discount")));

            p.commitAllFormatedTextFields();
            
            rs.close();
            proc.close();
            


            d.setTitleIcon(new javax.swing.ImageIcon(getClass().getResource("/tradeterminal/icons/TT_icons/32X32/tovarred.png")));
            d.setVisible(true);
            d.dispose();

            if (d.getReturnStatus() == ADialog.RET_OK) {              
                proc = conn.prepareCall("{call products_update(?,?,?,?,?,?,?,?,?)}");

                proc.setInt(1, id);
                
                {
                    ADBProc p4 = new ADBProc("find_product_group_id_for_product_scod");
                    p4.addInParametr(new ADBProcParametr(Types.VARCHAR, scodTextField.getText()));                
                    proc.setInt(2, PADBUtils.getIntScalar(conn, p4, false));
                }

                proc.setString(3, p.get_Name());
                proc.setString(4, p.getDescription());
                proc.setObject(5, p.getScod(), Types.VARCHAR);
                proc.setInt(6, p.getMeasures_id());
                proc.setObject(7, new BigDecimal((Double) p.getList_price()), Types.NUMERIC);
                proc.setObject(8, new BigDecimal((Double) p.getSpec_price()), Types.NUMERIC);
                proc.setObject(9, new BigDecimal(((Double) p.getPercent_discount()) * 0.01f), Types.NUMERIC);

                proc.execute();
                proc.close();
                
                conn.commit();

                findProductForScod();
            }

            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(conn != null)
                try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner actual_priceSpinner;
    private javax.swing.JButton addNewProductButton;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton find2Button;
    private javax.swing.JTextField groupNameTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField measuresTextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextArea operationDescriptionTextArea;
    private javax.swing.JSpinner quantitySpinner;
    private javax.swing.JTextField scodTextField;
    // End of variables declaration//GEN-END:variables
}
