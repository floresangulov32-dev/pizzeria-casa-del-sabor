/*package pizzeria.main;

import pizzeria.IU.PantallaInicial;

public class Main{
    public static void main(String[] args){
        // Configurar Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            new PantallaInicial().setVisible(true);
        });
    }
}*/

package pizzeria.main;

import pizzeria.IU.PantallaInicial;

public class Main{
    public static void main(String[] args){
        // Configurar Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            new PantallaInicial().setVisible(true);
        });
    }
}