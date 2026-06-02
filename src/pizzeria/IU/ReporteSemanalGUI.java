package pizzeria.IU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pizzeria.controller.GestorFinanzas;
import pizzeria.model.MovimientoCaja;
import pizzeria.model.TipoMovimiento;

public class ReporteSemanalGUI extends JPanel {
    
    private GestorFinanzas gestorFinanzas;
    private JTextArea txtReporte;
    //private JComboBox<String> cmbSemana;
    //private JTextField txtAnio;
    //private JButton btnGenerar, btnSemanaActual, btnSemanaAnterior, btnSemanaSiguiente, btnCerrar;
    //private JLabel lblTotalIngresos, lblTotalEgresos, lblBalance, lblPromedioDiario;
    
    private int añoActual;
    private int semanaActual;
    
    private DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private DateTimeFormatter formatterFechaCorta = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private WeekFields weekFields = WeekFields.of(Locale.getDefault());
    
    public ReporteSemanalGUI() {
        gestorFinanzas = new GestorFinanzas();
        gestorFinanzas.cargarArchivos();
        
        initUI();
        
        // Establecer semana y año actual por defecto
        LocalDate hoy = LocalDate.now();
        añoActual = hoy.getYear();
        semanaActual = hoy.get(weekFields.weekOfWeekBasedYear());
        
        // Llenar combo de semanas (1 a 53)
        for (int i = 1; i <= 53; i++) {
            cmbSemana.addItem(String.valueOf(i));
        }
        
        cmbSemana.setSelectedIndex(semanaActual - 1);
        txtAnio.setText(String.valueOf(añoActual));
        generarReporteSemanal();
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
        
        // Titulo
        JLabel lblTitulo = new JLabel("REPORTE SEMANAL");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(168, 27, 29));
        
        // Panel de filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setOpaque(false);
        
        JLabel lblSemana = new JLabel("Semana N°:");
        lblSemana.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        cmbSemana = new JComboBox<>();
        cmbSemana.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbSemana.setPreferredSize(new Dimension(70, 25));
        
        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        txtAnio = new JTextField(6);
        txtAnio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGenerar.setBackground(new Color(52, 152, 219));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnGenerar.setFocusPainted(false);
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.addActionListener(e -> generarDesdeInput());
        
        btnSemanaActual = new JButton("Semana Actual");
        btnSemanaActual.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSemanaActual.setBackground(new Color(46, 204, 113));
        btnSemanaActual.setForeground(Color.WHITE);
        btnSemanaActual.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnSemanaActual.setFocusPainted(false);
        btnSemanaActual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSemanaActual.addActionListener(e -> cargarSemanaActual());
        
        btnSemanaAnterior = new JButton("◄ Semana Anterior");
        btnSemanaAnterior.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSemanaAnterior.setBackground(new Color(46, 204, 113));
        btnSemanaAnterior.setForeground(Color.WHITE);
        btnSemanaAnterior.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnSemanaAnterior.setFocusPainted(false);
        btnSemanaAnterior.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSemanaAnterior.addActionListener(e -> semanaAnterior());
        
        btnSemanaSiguiente = new JButton("Semana Siguiente ►");
        btnSemanaSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSemanaSiguiente.setBackground(new Color(46, 204, 113));
        btnSemanaSiguiente.setForeground(Color.WHITE);
        btnSemanaSiguiente.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnSemanaSiguiente.setFocusPainted(false);
        btnSemanaSiguiente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSemanaSiguiente.addActionListener(e -> semanaSiguiente());
        
        panelFiltro.add(lblSemana);
        panelFiltro.add(cmbSemana);
        panelFiltro.add(lblAnio);
        panelFiltro.add(txtAnio);
        panelFiltro.add(btnGenerar);
        panelFiltro.add(btnSemanaActual);
        panelFiltro.add(btnSemanaAnterior);
        panelFiltro.add(btnSemanaSiguiente);
        
        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelFiltro, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Panel del reporte
        JPanel panelReporte = new JPanel(new BorderLayout());
        panelReporte.setOpaque(false);
        
        txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtReporte.setBackground(new Color(250, 250, 250));
        txtReporte.setForeground(Color.BLACK);
        txtReporte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(txtReporte);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        panelReporte.add(scrollPane, BorderLayout.CENTER);
        
        // Borde con título de la semana (se actualizará dinámicamente)
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Semana",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(80, 80, 80)
        );
        panelReporte.setBorder(border);
        
        // Panel de resumen
        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelResumen.setOpaque(false);
        panelResumen.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        lblTotalIngresos = new JLabel("Total Ingresos: Bs. 0.00");
        lblTotalEgresos = new JLabel("Total Egresos: Bs. 0.00");
        lblBalance = new JLabel("Balance: Bs. 0.00");
        lblPromedioDiario = new JLabel("Promedio Diario: Bs. 0.00");
        
        lblTotalIngresos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotalEgresos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBalance.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPromedioDiario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        lblTotalIngresos.setForeground(new Color(46, 204, 113));
        lblTotalEgresos.setForeground(new Color(231, 76, 60));
        lblBalance.setForeground(new Color(52, 152, 219));
        lblPromedioDiario.setForeground(new Color(155, 89, 182));
        
        panelResumen.add(lblTotalIngresos);
        panelResumen.add(lblTotalEgresos);
        panelResumen.add(lblBalance);
        panelResumen.add(lblPromedioDiario);
        
        panel.add(panelReporte, BorderLayout.CENTER);
        panel.add(panelResumen, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(168, 27, 29));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> cerrar());
        
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private LocalDate getFechaInicioSemana(int semana, int año) {
        // Obtener el primer día de la semana (lunes)
        LocalDate primerDia = LocalDate.of(año, 1, 1);
        int primerDiaSemana = primerDia.get(weekFields.weekOfWeekBasedYear());
        
        if (primerDiaSemana == 1) {
            primerDia = primerDia.with(weekFields.dayOfWeek(), 1);
        } else {
            primerDia = primerDia.plusDays(8 - primerDia.getDayOfWeek().getValue());
        }
        
        return primerDia.plusWeeks(semana - 1);
    }
    
    private void cargarSemanaActual() {
        LocalDate hoy = LocalDate.now();
        añoActual = hoy.getYear();
        semanaActual = hoy.get(weekFields.weekOfWeekBasedYear());
        
        cmbSemana.setSelectedIndex(semanaActual - 1);
        txtAnio.setText(String.valueOf(añoActual));
        generarReporteSemanal();
    }
    
    private void semanaAnterior() {
        semanaActual--;
        if (semanaActual < 1) {
            semanaActual = 52;
            añoActual--;
        }
        cmbSemana.setSelectedIndex(semanaActual - 1);
        txtAnio.setText(String.valueOf(añoActual));
        generarReporteSemanal();
    }
    
    private void semanaSiguiente() {
        semanaActual++;
        if (semanaActual > 52) {
            semanaActual = 1;
            añoActual++;
        }
        cmbSemana.setSelectedIndex(semanaActual - 1);
        txtAnio.setText(String.valueOf(añoActual));
        generarReporteSemanal();
    }
    
    private void generarDesdeInput() {
        try {
            int semana = cmbSemana.getSelectedIndex() + 1;
            int año = Integer.parseInt(txtAnio.getText().trim());
            
            if (año < 2000 || año > 2100) {
                JOptionPane.showMessageDialog(this,
                    "Año inválido. Use un año entre 2000 y 2100.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (semana < 1 || semana > 53) {
                JOptionPane.showMessageDialog(this,
                    "Semana inválida. Use un número entre 1 y 53.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            semanaActual = semana;
            añoActual = año;
            generarReporteSemanal();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Año inválido. Ingrese un número de 4 dígitos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarReporteSemanal() {
        // Obtener fecha de inicio y fin de la semana
        LocalDate inicioSemana = getFechaInicioSemana(semanaActual, añoActual);
        LocalDate finSemana = inicioSemana.plusDays(6);
        
        // Actualizar el borde del panel central
        JPanel panelCentral = (JPanel) getComponent(1);
        JPanel panelReporte = (JPanel) panelCentral.getComponent(0);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            String.format("Semana N° %d - %d - Del %s al %s", 
                semanaActual, añoActual,
                inicioSemana.format(formatterFechaCorta), 
                finSemana.format(formatterFechaCorta)),
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(80, 80, 80)
        );
        panelReporte.setBorder(border);
        
        LocalDateTime desde = inicioSemana.atStartOfDay();
        LocalDateTime hasta = finSemana.plusDays(1).atStartOfDay().minusNanos(1);
        
        double totalIngresos = gestorFinanzas.sumaIngresos(desde, hasta);
        double totalEgresos = gestorFinanzas.sumaEgresos(desde, hasta);
        double balance = totalIngresos - totalEgresos;
        double promedioDiario = balance / 7;
        
        // Actualizar resumen
        lblTotalIngresos.setText(String.format("Total Ingresos: Bs. %.2f", totalIngresos));
        lblTotalEgresos.setText(String.format("Total Egresos: Bs. %.2f", totalEgresos));
        
        if (balance >= 0) {
            lblBalance.setText(String.format("Balance: Bs. %.2f", balance));
            lblBalance.setForeground(new Color(46, 204, 113));
        } else {
            lblBalance.setText(String.format("Balance: Bs. %.2f (NEGATIVO)", balance));
            lblBalance.setForeground(new Color(231, 76, 60));
        }
        
        lblPromedioDiario.setText(String.format("Promedio Diario: Bs. %.2f", promedioDiario));
        
        // Obtener movimientos del período
        List<MovimientoCaja> movimientos = gestorFinanzas.getMovimientosPeriodo(desde, hasta);
        
        // Construir el reporte
        StringBuilder reporte = new StringBuilder();
        reporte.append("=".repeat(80)).append("\n");
        reporte.append(String.format("REPORTE SEMANAL - Semana N° %d - %d\n", semanaActual, añoActual));
        reporte.append(String.format("Período: del %s al %s\n", 
            inicioSemana.format(formatterFechaCorta), finSemana.format(formatterFechaCorta)));
        reporte.append("=".repeat(80)).append("\n\n");
        
        reporte.append("RESUMEN DE LA SEMANA\n");
        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("Ingresos totales:     Bs. %12.2f\n", totalIngresos));
        reporte.append(String.format("Egresos totales:      Bs. %12.2f\n", totalEgresos));
        reporte.append(String.format("Balance de la semana: Bs. %12.2f\n", balance));
        reporte.append(String.format("Promedio diario:      Bs. %12.2f\n", promedioDiario));
        reporte.append("\n");
        
        reporte.append("DETALLE POR DÍA\n");
        reporte.append("-".repeat(80)).append("\n");
        
        LocalDate fechaActual = inicioSemana;
        int diaNumero = 1;
        while (!fechaActual.isAfter(finSemana)) {
            final LocalDate dia = fechaActual;
            double ingresosDia = movimientos.stream()
                .filter(m -> m.getTipo() == TipoMovimiento.INGRESO)
                .filter(m -> m.getFecha().toLocalDate().equals(dia))
                .mapToDouble(MovimientoCaja::getMonto)
                .sum();
            
            double egresosDia = movimientos.stream()
                .filter(m -> m.getTipo() == TipoMovimiento.EGRESO)
                .filter(m -> m.getFecha().toLocalDate().equals(dia))
                .mapToDouble(MovimientoCaja::getMonto)
                .sum();
            
            double balanceDia = ingresosDia - egresosDia;
            
            String balanceStr = balanceDia >= 0 ? 
                String.format("Bs. %10.2f", balanceDia) : 
                String.format("Bs. %10.2f (NEGATIVO)", balanceDia);
            
            String diaNombre = getNombreDiaSemana(fechaActual);
            reporte.append(String.format("%s (%d): %s\n", diaNombre, diaNumero, fechaActual.format(formatterFechaCorta)));
            reporte.append(String.format("  Ingresos: Bs. %10.2f | Egresos: Bs. %10.2f | Balance: %s\n", 
                ingresosDia, egresosDia, balanceStr));
            reporte.append("\n");
            
            fechaActual = fechaActual.plusDays(1);
            diaNumero++;
        }
        
        reporte.append("DETALLE DE MOVIMIENTOS\n");
        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("%-20s %-12s %-10s %-35s\n", "FECHA", "TIPO", "MONTO", "DESCRIPCION"));
        reporte.append("-".repeat(80)).append("\n");
        
        if (movimientos.isEmpty()) {
            reporte.append("No hay movimientos registrados en esta semana.\n");
        } else {
            movimientos.sort((a, b) -> a.getFecha().compareTo(b.getFecha()));
            
            for (MovimientoCaja m : movimientos) {
                String tipo = m.getTipo() == TipoMovimiento.INGRESO ? "INGRESO" : "EGRESO";
                String montoStr = String.format("%.2f", m.getMonto());
                String fechaStr = m.getFecha().format(formatterFecha);
                reporte.append(String.format("%-20s %-12s Bs.%8s %-35s\n", 
                    fechaStr, tipo, montoStr, m.getDescripcion()));
            }
        }
        
        reporte.append("\n").append("=".repeat(80)).append("\n");
        reporte.append("Fin del reporte\n");
        
        txtReporte.setText(reporte.toString());
        txtReporte.setCaretPosition(0);
    }
    
    private String getNombreDiaSemana(LocalDate fecha) {
        switch (fecha.getDayOfWeek().getValue()) {
            case 1: return "LUNES";
            case 2: return "MARTES";
            case 3: return "MIÉRCOLES";
            case 4: return "JUEVES";
            case 5: return "VIERNES";
            case 6: return "SÁBADO";
            case 7: return "DOMINGO";
            default: return "";
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
        PanelFiltro = new javax.swing.JPanel();
        lblSemana = new javax.swing.JLabel();
        cmbSemana = new javax.swing.JComboBox<>();
        lblAnio = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();
        btnSemanaActual = new javax.swing.JButton();
        btnSemanaAnterior = new javax.swing.JButton();
        btnSemanaSiguiente = new javax.swing.JButton();
        PanelCentral = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        textArea1 = new java.awt.TextArea();
        PanelResumen = new javax.swing.JPanel();
        lblTotalIngresos = new javax.swing.JLabel();
        lblTotalEgresos = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        lblPromedioDiario = new javax.swing.JLabel();
        PanelInferior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();

        lblTitulo.setText("jLabel1");

        lblSemana.setText("jLabel1");

        cmbSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblAnio.setText("jLabel1");

        txtAnio.setText("jTextField1");

        btnGenerar.setText("jButton1");

        btnSemanaActual.setText("jButton1");

        btnSemanaAnterior.setText("jButton1");

        btnSemanaSiguiente.setText("jButton1");

        javax.swing.GroupLayout PanelFiltroLayout = new javax.swing.GroupLayout(PanelFiltro);
        PanelFiltro.setLayout(PanelFiltroLayout);
        PanelFiltroLayout.setHorizontalGroup(
            PanelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFiltroLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(lblSemana)
                .addGap(29, 29, 29)
                .addComponent(cmbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAnio)
                .addGap(27, 27, 27)
                .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnGenerar)
                .addGap(32, 32, 32)
                .addComponent(btnSemanaActual)
                .addGap(18, 18, 18)
                .addComponent(btnSemanaAnterior)
                .addGap(29, 29, 29)
                .addComponent(btnSemanaSiguiente)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        PanelFiltroLayout.setVerticalGroup(
            PanelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFiltroLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(PanelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAnio)
                        .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGenerar)
                        .addComponent(btnSemanaActual)
                        .addComponent(btnSemanaAnterior)
                        .addComponent(btnSemanaSiguiente))
                    .addComponent(lblSemana))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelSuperiorLayout = new javax.swing.GroupLayout(PanelSuperior);
        PanelSuperior.setLayout(PanelSuperiorLayout);
        PanelSuperiorLayout.setHorizontalGroup(
            PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(PanelFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelSuperiorLayout.setVerticalGroup(
            PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSuperiorLayout.createSequentialGroup()
                .addGroup(PanelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(PanelFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        scrollPane1.add(textArea1);

        lblTotalIngresos.setText("jLabel1");

        lblTotalEgresos.setText("jLabel1");

        lblBalance.setText("jLabel1");

        lblPromedioDiario.setText("jLabel1");

        javax.swing.GroupLayout PanelResumenLayout = new javax.swing.GroupLayout(PanelResumen);
        PanelResumen.setLayout(PanelResumenLayout);
        PanelResumenLayout.setHorizontalGroup(
            PanelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelResumenLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalIngresos)
                .addGap(83, 83, 83)
                .addComponent(lblTotalEgresos)
                .addGap(143, 143, 143)
                .addComponent(lblBalance)
                .addGap(229, 229, 229)
                .addComponent(lblPromedioDiario)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        PanelResumenLayout.setVerticalGroup(
            PanelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelResumenLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(PanelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalIngresos)
                    .addComponent(lblTotalEgresos)
                    .addComponent(lblBalance)
                    .addComponent(lblPromedioDiario))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelCentralLayout = new javax.swing.GroupLayout(PanelCentral);
        PanelCentral.setLayout(PanelCentralLayout);
        PanelCentralLayout.setHorizontalGroup(
            PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelCentralLayout.setVerticalGroup(
            PanelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        btnCerrar.setText("jButton1");

        javax.swing.GroupLayout PanelInferiorLayout = new javax.swing.GroupLayout(PanelInferior);
        PanelInferior.setLayout(PanelInferiorLayout);
        PanelInferiorLayout.setHorizontalGroup(
            PanelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInferiorLayout.createSequentialGroup()
                .addGap(411, 411, 411)
                .addComponent(btnCerrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelInferiorLayout.setVerticalGroup(
            PanelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelInferiorLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnCerrar)
                .addContainerGap(26, Short.MAX_VALUE))
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
                    .addComponent(PanelInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCentral;
    private javax.swing.JPanel PanelFiltro;
    private javax.swing.JPanel PanelInferior;
    private javax.swing.JPanel PanelResumen;
    private javax.swing.JPanel PanelSuperior;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnSemanaActual;
    private javax.swing.JButton btnSemanaAnterior;
    private javax.swing.JButton btnSemanaSiguiente;
    private javax.swing.JComboBox<String> cmbSemana;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblPromedioDiario;
    private javax.swing.JLabel lblSemana;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalEgresos;
    private javax.swing.JLabel lblTotalIngresos;
    private java.awt.ScrollPane scrollPane1;
    private java.awt.TextArea textArea1;
    private javax.swing.JTextField txtAnio;
    // End of variables declaration//GEN-END:variables
}
