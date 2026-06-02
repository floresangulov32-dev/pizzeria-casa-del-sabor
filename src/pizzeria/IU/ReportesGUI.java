package pizzeria.IU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pizzeria.controller.GestorFinanzas;
import pizzeria.controller.GestorUsuarios;

public class ReportesGUI extends JPanel {
    
    private GestorFinanzas gestorFinanzas;
    private GestorUsuarios gestorUsuarios;
    
    //private JButton btnVentasDiarias, btnVentasPeriodo, btnTopProductos;
    //private JButton btnReservasDiario, btnReservasPeriodo;
    //private JButton btnReporteSemanal, btnReporteMensual, btnReportePeriodo;
    //private JButton btnReporteFinanzas, btnHistorialMovimientos;
    //private JButton btnStockActual, btnProductosBajoStock, btnMovimientosInventario;
    //private JButton btnListaUsuarios, btnUsuariosPorRol;
    //private JButton btnCerrar;
    
    public ReportesGUI() {
        gestorFinanzas = new GestorFinanzas();
        gestorFinanzas.cargarArchivos();
        
        gestorUsuarios = new GestorUsuarios();
        gestorUsuarios.cargarDesdeArchivo();
        
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel lblTitulo = new JLabel("REPORTES DEL SISTEMA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(168, 27, 29));
        
        panel.add(lblTitulo, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(crearPanelVentas(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(crearPanelReservas(), gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(crearPanelGenerales(), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(crearPanelFinanzas(), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(crearPanelInventario(), gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(crearPanelUsuarios(), gbc);
        
        return panel;
    }
    
    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "VENTAS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(52, 152, 219)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnVentasDiarias = crearBotonReporte("Ventas Diarias", new Color(52, 152, 219));
        btnVentasDiarias.addActionListener(e -> reporteVentasDiarias());
        
        btnVentasPeriodo = crearBotonReporte("Ventas por Período", new Color(52, 152, 219));
        btnVentasPeriodo.addActionListener(e -> reporteVentasPeriodo());
        
        btnTopProductos = crearBotonReporte("Top Productos", new Color(52, 152, 219));
        btnTopProductos.addActionListener(e -> reporteTopProductos());
        
        panel.add(btnVentasDiarias);
        panel.add(btnVentasPeriodo);
        panel.add(btnTopProductos);
        
        return panel;
    }
    
    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "RESERVAS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(241, 196, 15)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnReservasDiario = crearBotonReporte("Reservas Diario", new Color(241, 196, 15));
        btnReservasDiario.addActionListener(e -> reporteReservasDiario());
        
        btnReservasPeriodo = crearBotonReporte("Reservas por Período", new Color(241, 196, 15));
        btnReservasPeriodo.addActionListener(e -> reporteReservasPeriodo());
        
        panel.add(btnReservasDiario);
        panel.add(btnReservasPeriodo);
        
        return panel;
    }
    
    private JPanel crearPanelGenerales() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "GENERALES",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(46, 204, 113)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnReporteSemanal = crearBotonReporte("Reporte Semanal", new Color(46, 204, 113));
        btnReporteSemanal.addActionListener(e -> reporteGeneralSemanal());
        
        btnReporteMensual = crearBotonReporte("Reporte Mensual", new Color(46, 204, 113));
        btnReporteMensual.addActionListener(e -> reporteGeneralMensual());
        
        btnReportePeriodo = crearBotonReporte("Reporte por Período", new Color(46, 204, 113));
        btnReportePeriodo.addActionListener(e -> reporteGeneralPeriodo());
        
        panel.add(btnReporteSemanal);
        panel.add(btnReporteMensual);
        panel.add(btnReportePeriodo);
        
        return panel;
    }
    
    private JPanel crearPanelFinanzas() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "FINANZAS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(155, 89, 182)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnReporteFinanzas = crearBotonReporte("Reporte Financiero", new Color(155, 89, 182));
        btnReporteFinanzas.addActionListener(e -> reporteFinanciero());
        
        btnHistorialMovimientos = crearBotonReporte("Historial Movimientos", new Color(155, 89, 182));
        btnHistorialMovimientos.addActionListener(e -> historialMovimientos());
        
        panel.add(btnReporteFinanzas);
        panel.add(btnHistorialMovimientos);
        
        return panel;
    }
    
    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "INVENTARIO",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(230, 126, 34)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnStockActual = crearBotonReporte("Stock Actual", new Color(230, 126, 34));
        btnStockActual.addActionListener(e -> reporteStockActual());
        
        btnProductosBajoStock = crearBotonReporte("Stock Bajo", new Color(230, 126, 34));
        btnProductosBajoStock.addActionListener(e -> reporteProductosBajoStock());
        
        btnMovimientosInventario = crearBotonReporte("Movimientos", new Color(230, 126, 34));
        btnMovimientosInventario.addActionListener(e -> reporteMovimientosInventario());
        
        panel.add(btnStockActual);
        panel.add(btnProductosBajoStock);
        panel.add(btnMovimientosInventario);
        
        return panel;
    }
    
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "USUARIOS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11),
            new Color(52, 73, 94)
        ));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        btnListaUsuarios = crearBotonReporte("Lista de Usuarios", new Color(52, 73, 94));
        btnListaUsuarios.addActionListener(e -> reporteListaUsuarios());
        
        btnUsuariosPorRol = crearBotonReporte("Usuarios por Rol", new Color(52, 73, 94));
        btnUsuariosPorRol.addActionListener(e -> reporteUsuariosPorRol());
        
        panel.add(btnListaUsuarios);
        panel.add(btnUsuariosPorRol);
        
        return panel;
    }
    
    private JButton crearBotonReporte(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(130, 28));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(boton.getBackground().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnCerrar.setBackground(new Color(168, 27, 29));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> cerrar());
        
        panel.add(btnCerrar);
        
        return panel;
    }
    
    // MÉTODOS DE REPORTES 
    
    private void reporteVentasDiarias() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Ventas Diarias en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteVentasPeriodo() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Ventas por Período en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteTopProductos() {
        JOptionPane.showMessageDialog(this,
            "Top Productos más vendidos en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteReservasDiario() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Reservas Diario en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteReservasPeriodo() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Reservas por Período en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteGeneralSemanal() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteSemanalParaReportesGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }    
    
    private void reporteGeneralMensual() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteMensualParaReportesGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void reporteGeneralPeriodo() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteRangosParaReportesGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void reporteFinanciero() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteFinancieroGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void historialMovimientos() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteHistorialMovimientosGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void reporteStockActual() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Stock Actual en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteProductosBajoStock() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Productos con Stock Bajo en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteMovimientosInventario() {
        JOptionPane.showMessageDialog(this,
            "Reporte de Movimientos de Inventario en desarrollo.\nPróximamente disponible.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reporteListaUsuarios() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteListaUsuariosGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void reporteUsuariosPorRol() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new ReporteUsuariosPorRolGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
    
    private void cerrar() {
        JPanel parent = (JPanel) getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new GestionFinanzasGUI(), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        PanelDescripcion = new javax.swing.JPanel();
        lblDescripcion = new javax.swing.JLabel();
        PanelCentral = new javax.swing.JPanel();
        PanelVentas = new javax.swing.JPanel();
        btnVentasDiarias = new javax.swing.JButton();
        btnVentasPeriodo = new javax.swing.JButton();
        btnTopProductos = new javax.swing.JButton();
        PanelReservas = new javax.swing.JPanel();
        btnReservasDiario = new javax.swing.JButton();
        btnReservasPeriodo = new javax.swing.JButton();
        PanelGenerales = new javax.swing.JPanel();
        btnReporteSemanal = new javax.swing.JButton();
        btnReporteMensual = new javax.swing.JButton();
        btnReportePeriodo = new javax.swing.JButton();
        PanelFinanzas = new javax.swing.JPanel();
        btnReporteFinanzas = new javax.swing.JButton();
        btnHistorialMovimientos = new javax.swing.JButton();
        PanelInventario = new javax.swing.JPanel();
        btnStockActual = new javax.swing.JButton();
        btnProductosBajoStock = new javax.swing.JButton();
        btnMovimientosInventario = new javax.swing.JButton();
        PanelUsuarios = new javax.swing.JPanel();
        btnListaUsuarios = new javax.swing.JButton();
        btnUsuariosPorRol = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();

        lblTitulo.setText("jLabel1");

        lblDescripcion.setText("jLabel1");

        javax.swing.GroupLayout PanelDescripcionLayout = new javax.swing.GroupLayout(PanelDescripcion);
        PanelDescripcion.setLayout(PanelDescripcionLayout);
        PanelDescripcionLayout.setHorizontalGroup(
            PanelDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDescripcionLayout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addComponent(lblDescripcion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDescripcionLayout.setVerticalGroup(
            PanelDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDescripcionLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(lblDescripcion)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout PanelSuperiorLayout = new javax.swing.GroupLayout(PanelSuperior);
        PanelSuperior.setLayout(PanelSuperiorLayout);
        PanelSuperiorLayout.setHorizontalGroup(
            PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSuperiorLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelSuperiorLayout.setVerticalGroup(
            PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSuperiorLayout.createSequentialGroup()
                .addGroup(PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSuperiorLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblTitulo))
                    .addGroup(PanelSuperiorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PanelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnVentasDiarias.setText("jButton1");

        btnVentasPeriodo.setText("jButton1");

        btnTopProductos.setText("jButton1");

        javax.swing.GroupLayout PanelVentasLayout = new javax.swing.GroupLayout(PanelVentas);
        PanelVentas.setLayout(PanelVentasLayout);
        PanelVentasLayout.setHorizontalGroup(
            PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVentasLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTopProductos)
                    .addComponent(btnVentasPeriodo)
                    .addComponent(btnVentasDiarias))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        PanelVentasLayout.setVerticalGroup(
            PanelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVentasLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnVentasDiarias)
                .addGap(41, 41, 41)
                .addComponent(btnVentasPeriodo)
                .addGap(32, 32, 32)
                .addComponent(btnTopProductos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReservasDiario.setText("jButton1");

        btnReservasPeriodo.setText("jButton1");

        javax.swing.GroupLayout PanelReservasLayout = new javax.swing.GroupLayout(PanelReservas);
        PanelReservas.setLayout(PanelReservasLayout);
        PanelReservasLayout.setHorizontalGroup(
            PanelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelReservasLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(PanelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnReservasPeriodo)
                    .addComponent(btnReservasDiario))
                .addGap(27, 27, 27))
        );
        PanelReservasLayout.setVerticalGroup(
            PanelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReservasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReservasDiario)
                .addGap(57, 57, 57)
                .addComponent(btnReservasPeriodo)
                .addGap(44, 44, 44))
        );

        btnReporteSemanal.setText("jButton1");

        btnReporteMensual.setText("jButton1");

        btnReportePeriodo.setText("jButton1");

        javax.swing.GroupLayout PanelGeneralesLayout = new javax.swing.GroupLayout(PanelGenerales);
        PanelGenerales.setLayout(PanelGeneralesLayout);
        PanelGeneralesLayout.setHorizontalGroup(
            PanelGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGeneralesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PanelGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnReportePeriodo)
                    .addComponent(btnReporteMensual)
                    .addComponent(btnReporteSemanal))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        PanelGeneralesLayout.setVerticalGroup(
            PanelGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGeneralesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnReporteSemanal)
                .addGap(36, 36, 36)
                .addComponent(btnReporteMensual)
                .addGap(36, 36, 36)
                .addComponent(btnReportePeriodo)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        btnReporteFinanzas.setText("jButton1");

        btnHistorialMovimientos.setText("jButton1");

        javax.swing.GroupLayout PanelFinanzasLayout = new javax.swing.GroupLayout(PanelFinanzas);
        PanelFinanzas.setLayout(PanelFinanzasLayout);
        PanelFinanzasLayout.setHorizontalGroup(
            PanelFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFinanzasLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(PanelFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHistorialMovimientos)
                    .addComponent(btnReporteFinanzas))
                .addGap(18, 18, 18))
        );
        PanelFinanzasLayout.setVerticalGroup(
            PanelFinanzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFinanzasLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnReporteFinanzas)
                .addGap(60, 60, 60)
                .addComponent(btnHistorialMovimientos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnStockActual.setText("jButton1");

        btnProductosBajoStock.setText("jButton1");

        btnMovimientosInventario.setText("jButton1");

        javax.swing.GroupLayout PanelInventarioLayout = new javax.swing.GroupLayout(PanelInventario);
        PanelInventario.setLayout(PanelInventarioLayout);
        PanelInventarioLayout.setHorizontalGroup(
            PanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStockActual)
                    .addComponent(btnProductosBajoStock)
                    .addComponent(btnMovimientosInventario))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PanelInventarioLayout.setVerticalGroup(
            PanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInventarioLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnStockActual)
                .addGap(26, 26, 26)
                .addComponent(btnProductosBajoStock)
                .addGap(42, 42, 42)
                .addComponent(btnMovimientosInventario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnListaUsuarios.setText("jButton1");

        btnUsuariosPorRol.setText("jButton1");

        javax.swing.GroupLayout PanelUsuariosLayout = new javax.swing.GroupLayout(PanelUsuarios);
        PanelUsuarios.setLayout(PanelUsuariosLayout);
        PanelUsuariosLayout.setHorizontalGroup(
            PanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(PanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuariosPorRol)
                    .addComponent(btnListaUsuarios))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        PanelUsuariosLayout.setVerticalGroup(
            PanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnListaUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUsuariosPorRol)
                .addGap(62, 62, 62))
        );

        javax.swing.GroupLayout PanelCentralLayout = new javax.swing.GroupLayout(PanelCentral);
        PanelCentral.setLayout(PanelCentralLayout);
        PanelCentralLayout.setHorizontalGroup(
            PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCentralLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(PanelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(PanelReservas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(PanelGenerales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelFinanzas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        PanelCentralLayout.setVerticalGroup(
            PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCentralLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelGenerales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelFinanzas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        btnCerrar.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar)
                .addGap(92, 92, 92))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnCerrar)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCentral;
    private javax.swing.JPanel PanelDescripcion;
    private javax.swing.JPanel PanelFinanzas;
    private javax.swing.JPanel PanelGenerales;
    private javax.swing.JPanel PanelInventario;
    private javax.swing.JPanel PanelReservas;
    private javax.swing.JPanel PanelSuperior;
    private javax.swing.JPanel PanelUsuarios;
    private javax.swing.JPanel PanelVentas;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnHistorialMovimientos;
    private javax.swing.JButton btnListaUsuarios;
    private javax.swing.JButton btnMovimientosInventario;
    private javax.swing.JButton btnProductosBajoStock;
    private javax.swing.JButton btnReporteFinanzas;
    private javax.swing.JButton btnReporteMensual;
    private javax.swing.JButton btnReportePeriodo;
    private javax.swing.JButton btnReporteSemanal;
    private javax.swing.JButton btnReservasDiario;
    private javax.swing.JButton btnReservasPeriodo;
    private javax.swing.JButton btnStockActual;
    private javax.swing.JButton btnTopProductos;
    private javax.swing.JButton btnUsuariosPorRol;
    private javax.swing.JButton btnVentasDiarias;
    private javax.swing.JButton btnVentasPeriodo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
