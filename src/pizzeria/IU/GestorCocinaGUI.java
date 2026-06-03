package pizzeria.IU;

import pizzeria.controller.GestorCocina;
import pizzeria.model.PedidoCocina;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import javax.swing.ImageIcon;
import java.awt.Image;

public class GestorCocinaGUI extends javax.swing.JFrame {

    // ── Colores del equipo ─────────────────────────────────
    private static final Color VINO        = new Color(168, 27, 29);
    private static final Color NEGRO       = new Color(0, 0, 0);
    private static final Color BLANCO      = new Color(255, 255, 255);
    private static final Color GRIS_CLARO  = new Color(217, 217, 217);
    private static final Color GRIS_CARBON = new Color(74, 74, 74);
    private static final Color VERDE       = new Color(46, 125, 50);

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(GestorCocinaGUI.class.getName());

    // ── Datos ──────────────────────────────────────────────
    private GestorCocina gestorCocina;
    private String rolUsuario   = "Cocina";
    private String nombreUsuario = "";
    private javax.swing.JButton btnActivo = null;

    // ── Estructura plantilla (igual que Erick) ─────────────
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Encabezado;
    private javax.swing.JPanel BarraNav;
    private javax.swing.JPanel Interfaz;
    private javax.swing.JPanel PiePag;
    private javax.swing.JLabel Rol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblLogo;

    // ── Botones barra nav ──────────────────────────────────
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnCerrar;

    // ── Tabla de pedidos ───────────────────────────────────
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    // ── Panel resumen ──────────────────────────────────────
    private JLabel lblTotal, lblPendientes, lblEnPrep, lblListos;
    private JLabel lblDetNombre, lblDetTipo, lblDetTotal;

    // ── Botones acción ─────────────────────────────────────
    private JButton btnTomar, btnMarcarListo, btnMarcarEntregado, btnActualizar;

    // ── Constructores ──────────────────────────────────────
    public GestorCocinaGUI() {
        this.gestorCocina = null;
        initComponents();
        actualizarTabla();
    }

    public GestorCocinaGUI(GestorCocina gestor, String rol, String nombre) {
        this.gestorCocina  = gestor;
        this.rolUsuario    = rol;
        this.nombreUsuario = nombre;
        initComponents();
        Rol.setText(rol + ": " + nombre);
        actualizarTabla();
    }

    public GestorCocinaGUI(GestorCocina gestor) {
        this.gestorCocina = gestor;
        initComponents();
        actualizarTabla();
    }

    // ══════════════════════════════════════════════════════
    //  INIT — estructura igual a PlantillaGerente
    // ══════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("Gestor de Cocina - La Casa del Sabor");
        setSize(1280, 720);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // FONDO
        Fondo = new javax.swing.JPanel();
        Fondo.setBackground(BLANCO);
        Fondo.setPreferredSize(new java.awt.Dimension(1280, 720));

        // ENCABEZADO (igual a plantilla Erick)
        Encabezado = new javax.swing.JPanel();
        Encabezado.setBackground(VINO);
        Encabezado.setBorder(javax.swing.BorderFactory.createLineBorder(GRIS_CLARO));
        Encabezado.setPreferredSize(new java.awt.Dimension(1280, 100));

        lblLogo = new javax.swing.JLabel("Logo");
        lblLogo.setPreferredSize(new java.awt.Dimension(94, 81));
        cargarImagen(lblLogo, "/pizzeria/IU/imagenes/logoCasaDelSabor.jpeg");

        lblTitulo = new javax.swing.JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 35));
        lblTitulo.setForeground(BLANCO);

        jLabel4 = new javax.swing.JLabel("PIZZERIA");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel4.setForeground(GRIS_CLARO);

        Rol = new javax.swing.JLabel("Rol: Cocina");
        Rol.setFont(new java.awt.Font("Segoe UI", 0, 16));
        Rol.setForeground(BLANCO);

        // Layout encabezado igual a plantilla
        javax.swing.GroupLayout encLayout = new javax.swing.GroupLayout(Encabezado);
        Encabezado.setLayout(encLayout);
        encLayout.setHorizontalGroup(
            encLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(encLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33)
                .addGroup(encLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 609, Short.MAX_VALUE)
                .addComponent(Rol, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        encLayout.setVerticalGroup(
            encLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(encLayout.createSequentialGroup()
                .addGap(21)
                .addComponent(Rol, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(encLayout.createSequentialGroup()
                .addGroup(encLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(encLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, encLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // BARRA NAV (igual estructura, solo 2 botones para cocina)
        BarraNav = new javax.swing.JPanel();
        BarraNav.setBackground(NEGRO);
        BarraNav.setBorder(javax.swing.BorderFactory.createLineBorder(GRIS_CLARO));
        BarraNav.setPreferredSize(new java.awt.Dimension(280, 560));

        btnPedidos = crearBotonNav("Pedidos de Cocina");
        btnCerrar  = crearBotonNav("Cerrar Sesión");

        btnPedidos.addActionListener(e -> activarBoton(btnPedidos));
        btnCerrar.addActionListener(e -> accionCerrarSesion());

        javax.swing.GroupLayout navLayout = new javax.swing.GroupLayout(BarraNav);
        BarraNav.setLayout(navLayout);
        navLayout.setHorizontalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPedidos, javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        navLayout.setVerticalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPedidos,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(400, Short.MAX_VALUE))
        );

        configurarHover();
        activarBoton(btnPedidos);

        // INTERFAZ — panel blanco central (aquí va el contenido de cocina)
        Interfaz = new javax.swing.JPanel(new BorderLayout(0, 0));
        Interfaz.setBackground(BLANCO);
        Interfaz.setBorder(javax.swing.BorderFactory.createLineBorder(GRIS_CLARO));
        construirContenidoCocina();

        // PIE DE PÁGINA (igual a plantilla Erick)
        PiePag = new javax.swing.JPanel();
        PiePag.setBackground(NEGRO);
        PiePag.setBorder(javax.swing.BorderFactory.createLineBorder(GRIS_CLARO));
        PiePag.setPreferredSize(new java.awt.Dimension(1280, 47));

        jLabel2 = new javax.swing.JLabel("  Powered by StarTech");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16));
        jLabel2.setForeground(BLANCO);

        javax.swing.GroupLayout pieLayout = new javax.swing.GroupLayout(PiePag);
        PiePag.setLayout(pieLayout);
        pieLayout.setHorizontalGroup(
            pieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pieLayout.createSequentialGroup()
                .addGap(12)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pieLayout.setVerticalGroup(
            pieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pieLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        // FONDO LAYOUT (igual a plantilla Erick)
        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Encabezado, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(fondoLayout.createSequentialGroup()
                .addComponent(BarraNav, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Interfaz, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(PiePag, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addComponent(Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BarraNav, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Interfaz, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PiePag, javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    // ══════════════════════════════════════════════════════
    //  CONTENIDO COCINA — va dentro del panel Interfaz
    // ══════════════════════════════════════════════════════
    private void construirContenidoCocina() {

        // ── Tabla ──────────────────────────────────────────
        String[] columnas = {"#", "Cliente", "Items", "Estado", "Tipo", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPedidos.setRowHeight(30);
        tablaPedidos.setSelectionBackground(VINO);
        tablaPedidos.setSelectionForeground(BLANCO);
        tablaPedidos.setGridColor(GRIS_CLARO);
        tablaPedidos.setBackground(BLANCO);
        tablaPedidos.setForeground(NEGRO);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tablaPedidos.getTableHeader();
        header.setBackground(GRIS_CARBON);
        header.setForeground(BLANCO);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);

        // anchos de columna
        int[] anchos = {40, 120, 250, 100, 80, 80};
        for (int i = 0; i < anchos.length; i++)
            tablaPedidos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        // listener para mostrar detalle al seleccionar
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> mostrarDetalle());

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        // ── Panel derecho resumen ──────────────────────────
        JPanel pnlResumen = new JPanel();
        pnlResumen.setLayout(new BoxLayout(pnlResumen, BoxLayout.Y_AXIS));
        pnlResumen.setBackground(new Color(245, 245, 245));
        pnlResumen.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        pnlResumen.setPreferredSize(new Dimension(190, 0));

        JLabel lblTitRes = new JLabel("RESUMEN");
        lblTitRes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitRes.setForeground(GRIS_CARBON);
        lblTitRes.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitRes.setBorder(BorderFactory.createEmptyBorder(10, 10, 6, 10));

        lblTotal      = labelResumen("Total: 0",       NEGRO);
        lblPendientes = labelResumen("Pendientes: 0",  VINO);
        lblEnPrep     = labelResumen("En prep.: 0",    new Color(133, 100, 4));
        lblListos     = labelResumen("Listos: 0",      VERDE);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(GRIS_CLARO);

        JLabel lblDetTit = new JLabel("Pedido seleccionado:");
        lblDetTit.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDetTit.setForeground(VINO);
        lblDetTit.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDetTit.setBorder(BorderFactory.createEmptyBorder(8, 10, 4, 10));

        lblDetNombre = labelDetalle("Cliente: —");
        lblDetTipo   = labelDetalle("Tipo: —");
        lblDetTotal  = labelDetalle("Total: —");

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnActualizar.setBackground(GRIS_CARBON);
        btnActualizar.setForeground(BLANCO);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnActualizar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnActualizar.addActionListener(e -> actualizarTabla());

        pnlResumen.add(lblTitRes);
        pnlResumen.add(lblTotal);
        pnlResumen.add(lblPendientes);
        pnlResumen.add(lblEnPrep);
        pnlResumen.add(lblListos);
        pnlResumen.add(Box.createVerticalStrut(8));
        pnlResumen.add(sep);
        pnlResumen.add(lblDetTit);
        pnlResumen.add(lblDetNombre);
        pnlResumen.add(lblDetTipo);
        pnlResumen.add(lblDetTotal);
        pnlResumen.add(Box.createVerticalGlue());
        pnlResumen.add(btnActualizar);

        // ── Panel central (tabla + resumen) ────────────────
        JPanel pnlCentro = new JPanel(new BorderLayout(5, 0));
        pnlCentro.setBackground(BLANCO);
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        pnlCentro.add(scroll,     BorderLayout.CENTER);
        pnlCentro.add(pnlResumen, BorderLayout.EAST);

        // ── Barra de botones inferior ──────────────────────
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlBotones.setBackground(NEGRO);
        pnlBotones.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        btnTomar          = crearBotonAccion("Tomar pedido",       VINO);
        btnMarcarListo    = crearBotonAccion("Marcar listo",       GRIS_CARBON);
        btnMarcarEntregado= crearBotonAccion("Marcar entregado",   VERDE);

        btnTomar.addActionListener(e -> accionTomarPedido());
        btnMarcarListo.addActionListener(e -> accionMarcarListo());
        btnMarcarEntregado.addActionListener(e -> accionMarcarEntregado());

        pnlBotones.add(btnTomar);
        pnlBotones.add(btnMarcarListo);
        pnlBotones.add(btnMarcarEntregado);

        // ── Ensamblar en Interfaz ──────────────────────────
        Interfaz.add(pnlCentro,   BorderLayout.CENTER);
        Interfaz.add(pnlBotones,  BorderLayout.SOUTH);
    }

    // ══════════════════════════════════════════════════════
    //  ACCIONES
    // ══════════════════════════════════════════════════════
    private void accionTomarPedido() {
        if (gestorCocina == null) { sinConexion(); return; }
        PedidoCocina p = gestorCocina.tomarSiguientePedido();
        if (p == null) {
            JOptionPane.showMessageDialog(this,
                "No hay pedidos pendientes.", "Aviso",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Pedido #" + p.getIdPedidoCocina() +
                " tomado.\nCliente: " + p.getNombreCliente(),
                "Pedido tomado", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTabla();
    }

    private void accionMarcarListo() {
        if (gestorCocina == null) { sinConexion(); return; }
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) { sinSeleccion(); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String estado = (String) modeloTabla.getValueAt(fila, 3);
        if (!estado.equals("EN PREP.")) {
            JOptionPane.showMessageDialog(this,
                "Solo puedes marcar como listo un pedido EN PREPARACION.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this,
            "Marcar pedido #" + id + " como LISTO?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            gestorCocina.marcarComoListo(id);
            actualizarTabla();
        }
    }

    private void accionMarcarEntregado() {
        if (gestorCocina == null) { sinConexion(); return; }
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) { sinSeleccion(); return; }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String estado = (String) modeloTabla.getValueAt(fila, 3);
        if (!estado.equals("LISTO")) {
            JOptionPane.showMessageDialog(this,
                "Solo puedes entregar un pedido que este LISTO.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this,
            "Marcar pedido #" + id + " como ENTREGADO?\nSe descontara stock de insumos.",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            gestorCocina.marcarComoEntregado(id);
            actualizarTabla();
        }
    }

    private void accionCerrarSesion() {
        int r = JOptionPane.showConfirmDialog(this,
            "Desea cerrar sesion?", "Cerrar Sesion",
            JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            dispose();
            new LoginGUI().setVisible(true);
        }
    }

    // ══════════════════════════════════════════════════════
    //  ACTUALIZAR TABLA Y RESUMEN
    // ══════════════════════════════════════════════════════
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        if (gestorCocina == null) return;

        for (PedidoCocina p : gestorCocina.getColaPendientes())
            modeloTabla.addRow(new Object[]{
                p.getIdPedidoCocina(), p.getNombreCliente(),
                resumenItems(p), "PENDIENTE",
                p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())
            });

        for (PedidoCocina p : gestorCocina.getEnPreparacion())
            modeloTabla.addRow(new Object[]{
                p.getIdPedidoCocina(), p.getNombreCliente(),
                resumenItems(p), "EN PREP.",
                p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())
            });

        for (PedidoCocina p : gestorCocina.getListos())
            modeloTabla.addRow(new Object[]{
                p.getIdPedidoCocina(), p.getNombreCliente(),
                resumenItems(p), "LISTO",
                p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())
            });

        int pend = gestorCocina.getColaPendientes().size();
        int prep = gestorCocina.getEnPreparacion().size();
        int list = gestorCocina.getListos().size();
        lblTotal.setText     ("Total: "        + (pend + prep + list));
        lblPendientes.setText("Pendientes: "   + pend);
        lblEnPrep.setText    ("En prep.: "     + prep);
        lblListos.setText    ("Listos: "       + list);

        // colores por estado en la tabla
        tablaPedidos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String est = (String) t.getValueAt(row, 3);
                if (sel) {
                    setBackground(VINO); setForeground(BLANCO);
                } else {
                    switch (est) {
                        case "PENDIENTE":
                            setBackground(new Color(255,243,243));
                            setForeground(NEGRO); break;
                        case "EN PREP.":
                            setBackground(new Color(255,251,230));
                            setForeground(NEGRO); break;
                        case "LISTO":
                            setBackground(new Color(230,255,234));
                            setForeground(NEGRO); break;
                        default:
                            setBackground(BLANCO);
                            setForeground(NEGRO);
                    }
                }
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return this;
            }
        });
    }

    private void mostrarDetalle() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) return;
        lblDetNombre.setText("Cliente: " + modeloTabla.getValueAt(fila, 1));
        lblDetTipo.setText  ("Tipo: "    + modeloTabla.getValueAt(fila, 4));
        lblDetTotal.setText ("Total: "   + modeloTabla.getValueAt(fila, 5));
    }

    private String resumenItems(PedidoCocina p) {
        if (p.getItems() == null || p.getItems().isEmpty()) return "(sin items)";
        StringBuilder sb = new StringBuilder();
        for (pizzeria.model.DetalleVenta d : p.getItems()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(d.getCantidad()).append("x ").append(d.getProducto().getNombre());
        }
        return sb.toString();
    }

    // ══════════════════════════════════════════════════════
    //  HELPERS VISUALES
    // ══════════════════════════════════════════════════════
    private JButton crearBotonNav(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setBackground(NEGRO);
        b.setForeground(BLANCO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        return b;
    }

    private JButton crearBotonAccion(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setBackground(fondo);
        b.setForeground(BLANCO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setPreferredSize(new Dimension(180, 40));
        return b;
    }

    private JLabel labelResumen(String texto, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(color);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        return l;
    }

    private JLabel labelDetalle(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(GRIS_CARBON);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        return l;
    }

    private void configurarHover() {
        javax.swing.JButton[] botones = {btnPedidos, btnCerrar};
        for (javax.swing.JButton b : botones) {
            b.setContentAreaFilled(true);
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setBackground(NEGRO);
            b.setForeground(BLANCO);
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b != btnActivo) b.setBackground(VINO);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (b != btnActivo) b.setBackground(NEGRO);
                }
            });
        }
    }

    private void activarBoton(javax.swing.JButton boton) {
        if (btnActivo != null) {
            btnActivo.setBackground(NEGRO);
            btnActivo.setForeground(BLANCO);
        }
        boton.setBackground(VINO);
        boton.setForeground(BLANCO);
        btnActivo = boton;
    }

    private void cargarImagen(JLabel label, String ruta) {
        try {
            label.setText("");
            ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
            Image img = icono.getImage().getScaledInstance(
                label.getPreferredSize().width,
                label.getPreferredSize().height,
                Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            label.setText("Logo");
        }
    }

    private void sinConexion() {
        JOptionPane.showMessageDialog(this,
            "Sin conexion al gestor.", "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }

    private void sinSeleccion() {
        JOptionPane.showMessageDialog(this,
            "Selecciona un pedido de la tabla primero.", "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }

    // ══════════════════════════════════════════════════════
    //  MAIN — para probar sola
    // ══════════════════════════════════════════════════════
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new GestorCocinaGUI().setVisible(true));
    }
}