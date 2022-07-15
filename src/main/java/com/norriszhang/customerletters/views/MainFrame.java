package com.norriszhang.customerletters.views;

import com.norriszhang.customerletters.models.DataForm;
import com.norriszhang.customerletters.services.Docx2PdfService;
import com.norriszhang.customerletters.services.TemplateRenderService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.io.IOException;

public class MainFrame extends JFrame {
    private TemplateRenderService service = new TemplateRenderService("/Users/norris/Documents/temp/pdfout");
    private final Docx2PdfService converter = new Docx2PdfService();
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel mainPanel = new MainPanel();
        add(mainPanel);
        JButton btnGo = new JButton("GO");
        btnGo.addActionListener(e -> {
            try {
                DataForm form = DataForm.builder()
                    .name(mainPanel.getCustomerName())
                    .address(mainPanel.getCustomerAddress())
                    .build();

                String filePath = service.render("/SettlementInstruction.docx", form);
                String pdfPath = converter.docx2Pdf(filePath);
                JOptionPane.showMessageDialog(this, pdfPath, "Title", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnGo, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
