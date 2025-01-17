/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.malban.config.theme;

import de.malban.config.Configuration;
import de.malban.config.Logable;
import de.malban.gui.panels.LogPanel;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Malban
 */
public class ProxyPanel extends javax.swing.JPanel {

    public static final Proxy.Type[] PROXY_TYPES = Proxy.Type.values();
    private int mProxyType = 0;

    /**
     * Creates new form ProxyPanel
     */
    public ProxyPanel() {
        initComponents();
        jPanel2.removeAll();

        String[] text = { "No Proxy", "Http Proxy","Socks Proxy"};
        for(int i = 0; i < PROXY_TYPES.length; i++)
        {
            JRadioButton rb = new JRadioButton(text[i]);
            final int t=i;
            rb.addChangeListener(new ChangeListener()
            {
                @Override public void stateChanged(ChangeEvent e)
                {
                    if(((AbstractButton) e.getSource()).isSelected())
                    {
                        mProxyType = t;
                        jTextFieldProxyURL.setEnabled(mProxyType != 0);
                        jTextFieldProxyPort.setEnabled(mProxyType != 0);
                    }
                }
            });
            buttonGroup1.add(rb);
            jPanel2.add(rb);
            if(i == 0) rb.setSelected(true);
        }
        
    }

    public Proxy getProxy()
    {
        Proxy p;
        if(mProxyType == 0)
            p = Proxy.NO_PROXY;
        else
        {
            try
            {
                p = new Proxy(  PROXY_TYPES[mProxyType],
                                
                                new InetSocketAddress(jTextFieldProxyURL.getText(),
                                de.malban.util.UtilityString.IntX(jTextFieldProxyPort.getText(), 21)));
            }
            catch(Exception e)
            {
                Configuration.getConfiguration().getDebugEntity().addLog(e,LogPanel.ERROR);
                return Proxy.NO_PROXY;
            }
        }
        return p;
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldProxyURL = new javax.swing.JTextField();
        jTextFieldProxyPort = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jRadioButtonPType = new javax.swing.JRadioButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Proxy Settings"));

        jTextFieldProxyURL.setText("Proxy Adress");
        jTextFieldProxyURL.setToolTipText("URL");

        jTextFieldProxyPort.setText("Port Number");
        jTextFieldProxyPort.setToolTipText("Port");

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jRadioButtonPType.setText("jRadioButton1");
        jPanel2.add(jRadioButtonPType);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextFieldProxyURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 229, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextFieldProxyPort, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldProxyURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonPType;
    private javax.swing.JTextField jTextFieldProxyPort;
    private javax.swing.JTextField jTextFieldProxyURL;
    // End of variables declaration//GEN-END:variables
}
