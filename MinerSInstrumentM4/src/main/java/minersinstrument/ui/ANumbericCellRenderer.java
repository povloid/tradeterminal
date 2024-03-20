/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author kopychenko
 */
public class ANumbericCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private Icon icon = null;
    private Icon icon2 = null;
    private String suffix = "";
    private String prefix = "";
    private Color colorPlus = Color.BLACK;
    private Color colorMinus = Color.BLACK;
    private Color unselectedForeground;
    private Color unselectedBackground;
    private static Border noFocusBorder2 = new EmptyBorder(0, 1, 0, 1);
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(0, 1, 0, 1);
    private DecimalFormat formater = new DecimalFormat("##0.00#");

    public DecimalFormat getFormater() {
        return formater;
    }

    public void setFormater(DecimalFormat formater) {
        this.formater = formater;
    }

    public ANumbericCellRenderer() {
    }

    public ANumbericCellRenderer(String prefix) {
        this.prefix = prefix;
    }

    public ANumbericCellRenderer(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ANumbericCellRenderer(Icon icon) {
        this.icon = icon;
    }

    public ANumbericCellRenderer(Icon icon, Icon icon2) {
        this.icon = icon;
        this.icon2 = icon2;
    }

    public ANumbericCellRenderer(Icon icon, String prefix) {
        this.icon = icon;
        this.prefix = prefix;
    }

    public ANumbericCellRenderer(Icon icon, Icon icon2, String prefix) {
        this.icon = icon;
        this.icon2 = icon2;
        this.prefix = prefix;
    }

    public ANumbericCellRenderer(Icon icon, String prefix, String suffix) {
        this.icon = icon;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ANumbericCellRenderer(Icon icon, Icon icon2, String prefix, String suffix) {
        this.icon = icon;
        this.icon2 = icon2;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {


        if (value == null) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);

            return cell;
        }

        double f = 0;
        if (value.getClass() == java.math.BigDecimal.class) {
            f = ((java.math.BigDecimal) value).doubleValue();
        } else if (value.getClass() == Long.class) {
            f = ((Long) value).doubleValue();
        } else if (value.getClass() == Double.class) {
            f = (Double) value;
        }


        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBackground(Color.WHITE);

        JLabel l = new JLabel(prefix + formater.format(f) + suffix);

        p.add(l, BorderLayout.EAST);

        different(f, p, l, table, value,
                isSelected, hasFocus, row, column);


        if (isSelected) {
            p.setForeground(table.getSelectionForeground());
            p.setBackground(table.getSelectionBackground());
            if (colorPlus == colorMinus) {
                l.setForeground(table.getSelectionForeground());
                l.setBackground(table.getSelectionBackground());
            } else {
                //l.setForeground(new Color(l.getForeground().getRGB() | l.getBackground().getRGB()));
                l.setForeground(new Color(~table.getSelectionBackground().getRGB()));
                l.setBackground(table.getSelectionBackground());
            }


        } else {
            p.setForeground(unselectedForeground != null
                    ? unselectedForeground
                    : table.getForeground());
            p.setBackground(unselectedBackground != null
                    ? unselectedBackground
                    : table.getBackground());
        }


        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            p.setBorder(border);

            if (isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    p.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    p.setBackground(col);
                }
            }
        } else {
            p.setBorder(getNoFocusBorder());
        }


//        if (hasFocus) {
//            p.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
//            p.setBackground(Color.LIGHT_GRAY);
//        } else if (isSelected) {
//            p.setBackground(Color.LIGHT_GRAY);
//        } else {
//            p.setBorder(null);
//        }

        return p;

    }

    protected void different(double f, JPanel panel, JLabel label,
            JTable table, Object o,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (f > 0) {
            label.setForeground(colorPlus);
            panel.add(new JLabel(icon), BorderLayout.WEST);
        } else {
            label.setForeground(colorMinus);
            panel.add(new JLabel(icon2), BorderLayout.WEST);
        }
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder2;
        }
    }

    /**
     * Overrides <code>JComponent.setForeground</code> to assign
     * the unselected-foreground color to the specified color.
     * 
     * @param c set the foreground color to this value
     */
    @Override
    public void setForeground(Color c) {
        super.setForeground(c);
        unselectedForeground = c;
    }

    /**
     * Overrides <code>JComponent.setBackground</code> to assign
     * the unselected-background color to the specified color.
     *
     * @param c set the background color to this value
     */
    @Override
    public void setBackground(Color c) {
        super.setBackground(c);
        unselectedBackground = c;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Color getColorMinus() {
        return colorMinus;
    }

    public void setColorMinus(Color colorMinus) {
        this.colorMinus = colorMinus;
    }

    public Color getColorPlus() {
        return colorPlus;
    }

    public void setColorPlus(Color colorPlus) {
        this.colorPlus = colorPlus;
    }

    public void setColorPlusAndMinus(Color colorPlus, Color colorMinus) {
        this.colorPlus = colorPlus;
        this.colorMinus = colorMinus;
    }
}





