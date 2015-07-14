package formais2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Tela extends javax.swing.JFrame {

    private GLC glc;
    private AnalizadorSintático analizadorSintático;
    private JFileChooser fc = new JFileChooser();

    public Tela() {
        super("Olintools");
        initComponents();
        setLocationRelativeTo(null);

        menuCarregar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String texto = carregarTexto();
                    glc = new GLC(texto);
                    analizadorSintático = null;
                    textGramatica.setText(texto);
                    tableFirstFollow.setModel(new javax.swing.table.DefaultTableModel(
                            new Object[][]{},
                            new String[]{
                                "", "First", "Follow", "First ∩ Follow"
                            }
                    ));
                } catch (IllegalArgumentException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Arquivo inválido.");
                }
            }
        });

        menuSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String gramatica = textGramatica.getText();
                if (gramatica.length() != 0) {
                    salvarTexto(gramatica);
                } else {
                    JOptionPane.showMessageDialog(null, "Insira uma gramática antes de salvar.");
                }
            }
        });

    }

    public String carregarTexto() throws IOException {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            byte[] encoded = Files.readAllBytes(file.toPath());
            return new String(encoded, "UTF-8");
        }
        return "";
    }

    public void salvarTexto(String texto) {
        byte dataToWrite[] = texto.getBytes();
        FileOutputStream out;
        try {
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                out = new FileOutputStream(file);
                out.write(dataToWrite);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textGramatica = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFirstFollow = new javax.swing.JTable();
        btnGerarParser = new javax.swing.JButton();
        btnGramatica = new javax.swing.JButton();
        textReconhecer = new javax.swing.JTextField();
        btnReconhecer = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuSalvar = new javax.swing.JMenuItem();
        menuCarregar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textGramatica.setColumns(20);
        textGramatica.setRows(5);
        jScrollPane1.setViewportView(textGramatica);

        tableFirstFollow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "First", "Follow", "First ∩ Follow"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableFirstFollow);

        btnGerarParser.setText("Parser");
        btnGerarParser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarParserActionPerformed(evt);
            }
        });

        btnGramatica.setText("First/Follow");
        btnGramatica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGramaticaActionPerformed(evt);
            }
        });

        btnReconhecer.setText("Reconhecer");
        btnReconhecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReconhecerActionPerformed(evt);
            }
        });

        menuArquivo.setText("Arquivo");

        menuSalvar.setText("Salvar");
        menuArquivo.add(menuSalvar);

        menuCarregar.setText("Carregar");
        menuArquivo.add(menuCarregar);

        jMenuBar1.add(menuArquivo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGramatica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGerarParser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textReconhecer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReconhecer)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGerarParser)
                    .addComponent(btnGramatica)
                    .addComponent(textReconhecer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReconhecer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGramaticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGramaticaActionPerformed
        try {
            glc = new GLC(textGramatica.getText());

            Object[][] rows = new Object[glc.getSimbolosNaoTerminais().size()][4];

            int i = 0;
            for (String simbolo : glc.getSimbolosNaoTerminais()) {
                Set<String> firstList = glc.obterConjuntosFirst().get(simbolo);
                Set<String> followList = glc.obterConjuntosFollow().get(simbolo);

                String first = firstList.toString();
                first = first.substring(1, first.length() - 1);
                String follow = followList.toString();
                follow = follow.substring(1, follow.length() - 1);
                rows[i] = (new Object[]{simbolo, first, follow, firstList.contains("&") ? (glc.interseccaoEntreFirstEFollowVazia(simbolo) ? "Sim" : "Não") : ""});
                i++;
            }

            tableFirstFollow.setModel(new javax.swing.table.DefaultTableModel(
                    rows,
                    new String[]{
                        "", "First", "Follow", "First ∩ Follow"
                    }
            ) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            });

            String recursaoEsquerda;

            try {
                glc.possuiRecursaoAEsquerda();
                recursaoEsquerda = "Não";
            } catch (Exception ex) {
                recursaoEsquerda = ex.getMessage();
            }

            String mensagem = "A gramática:"
                    + "\nPossui recursão a esquerda? " + recursaoEsquerda
                    + "\nEstá fatorada? " + (glc.isFatorada() ? "Sim" : "Não");

            JOptionPane.showMessageDialog(null, mensagem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Insira uma gramática válida.");
        }
    }//GEN-LAST:event_btnGramaticaActionPerformed

    private void btnGerarParserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarParserActionPerformed
        if (glc.verificarSeLL1()) {
            analizadorSintático = new AnalizadorSintático(textGramatica.getText());
            JOptionPane.showMessageDialog(null, "Parser gerado. Basta reconhecer sentenças.");
        } else {
            JOptionPane.showMessageDialog(null, "Gramática não é LL(1)");
        }
    }//GEN-LAST:event_btnGerarParserActionPerformed

    private void btnReconhecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReconhecerActionPerformed
        if (glc.verificarSeLL1()) {
            analizadorSintático.reconhecer(textReconhecer.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Gramática não é LL(1)");
        }
    }//GEN-LAST:event_btnReconhecerActionPerformed

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
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGerarParser;
    private javax.swing.JButton btnGramatica;
    private javax.swing.JButton btnReconhecer;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuItem menuCarregar;
    private javax.swing.JMenuItem menuSalvar;
    private javax.swing.JTable tableFirstFollow;
    private javax.swing.JTextArea textGramatica;
    private javax.swing.JTextField textReconhecer;
    // End of variables declaration//GEN-END:variables
}
