/*
 * ProductDPanel.java
 *
 * Created on 22 Май 2008 г., 0:19
 */
package tt.reports.gran.dynamic_bar3d_plot;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import minersinstrument.ui.IADialogPanel;

/**
 *
 * @author  pkopychenko
 */
public class TTRAPSLDynamicBar3DPlotDPanel extends javax.swing.JPanel implements IADialogPanel {

    /**
     * Получить шаг
     * @return
     */
    public String getStep() {
        if (yearRadioButton.isSelected()) {
            return "year";
        } else if (monthRadioButton.isSelected()) {
            return "month";
        } else if (dayRadioButton.isSelected()) {
            return "day";
        } else if (hourRadioButton.isSelected()) {
            return "hour";
        } else {
            return "day";
        }
    }

    /** Creates new form ProductDPanel */
    public TTRAPSLDynamicBar3DPlotDPanel() {
        initComponents();


        Calendar cal = new GregorianCalendar();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);

        beginDatePicker.setDate(cal.getTime());
        endDatePicker.setDate(new Date());
    }

    public Date getBeginDate() {
        return beginDatePicker.getDate();
    }

    public void setBeginDate(Date d) {
        this.beginDatePicker.setDate(d);
    }

    public Date getEndDate() {
        return endDatePicker.getDate();
    }

    public void setEndDate(Date d) {
        this.endDatePicker.setDate(d);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stepButtonGroup = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        beginDatePicker = new org.jdesktop.swingx.JXDatePicker();
        endDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jPanel1 = new javax.swing.JPanel();
        yearRadioButton = new javax.swing.JRadioButton();
        monthRadioButton = new javax.swing.JRadioButton();
        dayRadioButton = new javax.swing.JRadioButton();
        hourRadioButton = new javax.swing.JRadioButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(TTRAPSLDynamicBar3DPlotDPanel.class);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        beginDatePicker.setName("beginDatePicker"); // NOI18N

        endDatePicker.setName("endDatePicker"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        stepButtonGroup.add(yearRadioButton);
        yearRadioButton.setText(resourceMap.getString("yearRadioButton.text")); // NOI18N
        yearRadioButton.setName("yearRadioButton"); // NOI18N

        stepButtonGroup.add(monthRadioButton);
        monthRadioButton.setText(resourceMap.getString("monthRadioButton.text")); // NOI18N
        monthRadioButton.setName("monthRadioButton"); // NOI18N

        stepButtonGroup.add(dayRadioButton);
        dayRadioButton.setSelected(true);
        dayRadioButton.setText(resourceMap.getString("dayRadioButton.text")); // NOI18N
        dayRadioButton.setName("dayRadioButton"); // NOI18N

        stepButtonGroup.add(hourRadioButton);
        hourRadioButton.setText(resourceMap.getString("hourRadioButton.text")); // NOI18N
        hourRadioButton.setName("hourRadioButton"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yearRadioButton)
                    .addComponent(monthRadioButton)
                    .addComponent(dayRadioButton)
                    .addComponent(hourRadioButton))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yearRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dayRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hourRadioButton)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(beginDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(endDatePicker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(beginDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public boolean checkPanel() {
        return true;
    }

    @Override
    public void openPanel() {
        beginDatePicker.requestFocus();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker beginDatePicker;
    private javax.swing.JRadioButton dayRadioButton;
    private org.jdesktop.swingx.JXDatePicker endDatePicker;
    private javax.swing.JRadioButton hourRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton monthRadioButton;
    private javax.swing.ButtonGroup stepButtonGroup;
    private javax.swing.JRadioButton yearRadioButton;
    // End of variables declaration//GEN-END:variables
}
