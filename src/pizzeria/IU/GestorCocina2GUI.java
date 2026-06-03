package pizzeria.IU;

import pizzeria.controller.GestorCocina;
import pizzeria.model.PedidoCocina;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class GestorCocina2GUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestorCocina2GUI.class.getName());
    private static final Color VINO        = new Color(168, 27, 29);
    private static final Color NEGRO       = new Color(0, 0, 0);
    private static final Color GRIS_CARBON = new Color(74, 74, 74);
    private static final Color BLANCO      = new Color(255, 255, 255);
    private static final Color GRIS_CLARO  = new Color(217, 217, 217);

    private GestorCocina gestorCocina;
    private String nombreUsuario;
    private String rolUsuario;
    private JButton btnActivo = null;

    private DefaultTableModel modeloTabla;
    private JTable tablaPedidos;
    private JLabel lblPendientes;
    private JLabel lblEnPrep;
    private JLabel lblListos;

    // Paneles principales igual que tu compañero
    private JPanel Fondo;
    private JPanel Encabezado;
    private JPanel BarraNav;
    private JPanel PiePag;
    private JPanel Interfaz;
    private JLabel Rol;
    private JButton btnInicio;
    private JButton btnCerrar;

    public GestorCocina2GUI() {
        initComponentes();
    }

    public GestorCocina2GUI(GestorCocina gestorCocina, String rol, String nombre) {
        this.gestorCocina = gestorCocina;
        this.rolUsuario = rol;
        this.nombreUsuario = nombre;
        initComponentes();
        Rol.setText(rol + ": " + nombre);
        actualizarTabla("TODOS");
    }

    private void initComponentes() {
        setTitle("Gestor de Cocina - La Casa del Sabor");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ── FONDO ──────────────────────────────────────
        Fondo = new JPanel(new BorderLayout());
        Fondo.setBackground(BLANCO);
        Fondo.setPreferredSize(new Dimension(1280, 720));

        // ── ENCABEZADO ─────────────────────────────────
        Encabezado = new JPanel(new BorderLayout());
        Encabezado.setBackground(VINO);
        Encabezado.setPreferredSize(new Dimension(1280, 100));
        Encabezado.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        JPanel encIzq = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        encIzq.setBackground(VINO);
        JLabel lblLogo = new JLabel("LOGO");
        lblLogo.setPreferredSize(new Dimension(75, 75));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setForeground(BLANCO);
        lblLogo.setBorder(BorderFactory.createLineBorder(BLANCO));
        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setBackground(VINO);
        JLabel lblTitulo = new JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(BLANCO);
        JLabel lblSub = new JLabel("PIZZERÍA");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setForeground(GRIS_CLARO);
        textos.add(lblTitulo);
        textos.add(lblSub);
        encIzq.add(lblLogo);
        encIzq.add(textos);

        JPanel encDer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 35));
        encDer.setBackground(VINO);
        Rol = new JLabel("Rol: Cocina");
        Rol.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        Rol.setForeground(BLANCO);
        JButton btnCerrarEnc = new JButton("Cerrar Sesión");
        btnCerrarEnc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCerrarEnc.setBackground(NEGRO);
        btnCerrarEnc.setForeground(BLANCO);
        btnCerrarEnc.setBorder(BorderFactory.createLineBorder(BLANCO));
        btnCerrarEnc.setFocusPainted(false);
        btnCerrarEnc.addActionListener(e -> cerrarSesion());
        encDer.add(Rol);
        encDer.add(btnCerrarEnc);

        Encabezado.add(encIzq, BorderLayout.WEST);
        Encabezado.add(encDer, BorderLayout.EAST);

        // ── BARRA NAV ──────────────────────────────────
        BarraNav = new JPanel();
        BarraNav.setBackground(NEGRO);
        BarraNav.setPreferredSize(new Dimension(280, 560));
        BarraNav.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        BarraNav.setLayout(new BoxLayout(BarraNav, BoxLayout.Y_AXIS));

        JLabel modulo = new JLabel("  MÓDULO COCINA");
        modulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        modulo.setForeground(GRIS_CLARO);
        modulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        modulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnInicio = crearBotonNav("Pedidos", true);
        btnInicio.addActionListener(e -> {
            activarBoton(btnInicio);
            actualizarTabla("TODOS");
        });

        btnCerrar = crearBotonNav("Cerrar Sesión", false);
        btnCerrar.addActionListener(e -> cerrarSesion());

        BarraNav.add(modulo);
        BarraNav.add(btnInicio);
        BarraNav.add(Box.createVerticalGlue());
        BarraNav.add(btnCerrar);
        BarraNav.add(Box.createVerticalStrut(20));

        // ── INTERFAZ (área central en BLANCO) ──────────
        Interfaz = new JPanel(new BorderLayout(0, 0));
        Interfaz.setBackground(BLANCO);
        Interfaz.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        // Filtros
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        filtros.setBackground(GRIS_CARBON);
        JLabel lblFiltrar = new JLabel("Filtrar:");
        lblFiltrar.setForeground(BLANCO);
        lblFiltrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filtros.add(lblFiltrar);
        String[] fNombres = {"Todos", "Pendientes", "En Prep.", "Listos"};
        String[] fValores = {"TODOS", "PENDIENTE", "EN_PREPARACION", "LISTO"};
        for (int i = 0; i < fNombres.length; i++) {
            final String val = fValores[i];
            JButton fb = new JButton(fNombres[i]);
            fb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            fb.setBackground(i == 0 ? VINO : GRIS_CARBON);
            fb.setForeground(BLANCO);
            fb.setFocusPainted(false);
            fb.setBorderPainted(false);
            fb.setPreferredSize(new Dimension(100, 30));
            fb.addActionListener(e -> actualizarTabla(val));
            filtros.add(fb);
        }

        // Tabla + Resumen
        JPanel central = new JPanel(new BorderLayout(8, 0));
        central.setBackground(BLANCO);
        central.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"#", "Cliente", "Items", "Estado", "Tipo", "Total"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPedidos.setRowHeight(35);
        tablaPedidos.setSelectionBackground(new Color(255, 220, 220));
        tablaPedidos.setGridColor(GRIS_CLARO);
        tablaPedidos.getTableHeader().setBackground(GRIS_CARBON);
        tablaPedidos.getTableHeader().setForeground(BLANCO);
        tablaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPedidos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaPedidos.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaPedidos.getColumnModel().getColumn(2).setPreferredWidth(220);
        tablaPedidos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaPedidos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaPedidos.getColumnModel().getColumn(5).setPreferredWidth(80);
        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        // Panel resumen
        JPanel resumen = new JPanel();
        resumen.setLayout(new BoxLayout(resumen, BoxLayout.Y_AXIS));
        resumen.setBackground(new Color(245, 245, 245));
        resumen.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        resumen.setPreferredSize(new Dimension(190, 0));
        JLabel resT = new JLabel("RESUMEN");
        resT.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resT.setForeground(GRIS_CARBON);
        resT.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        lblPendientes = new JLabel("Pendientes: 0");
        lblPendientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPendientes.setForeground(VINO);
        lblPendientes.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        lblEnPrep = new JLabel("En prep.: 0");
        lblEnPrep.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEnPrep.setForeground(new Color(133, 100, 4));
        lblEnPrep.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        lblListos = new JLabel("Listos: 0");
        lblListos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblListos.setForeground(new Color(46, 125, 50));
        lblListos.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        JButton btnAct = new JButton("Actualizar");
        btnAct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAct.setBackground(GRIS_CARBON);
        btnAct.setForeground(BLANCO);
        btnAct.setFocusPainted(false);
        btnAct.setBorderPainted(false);
        btnAct.setMaximumSize(new Dimension(160, 35));
        btnAct.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAct.addActionListener(e -> actualizarTabla("TODOS"));
        resumen.add(resT);
        resumen.add(new JSeparator());
        resumen.add(lblPendientes);
        resumen.add(lblEnPrep);
        resumen.add(lblListos);
        resumen.add(Box.createVerticalStrut(10));
        resumen.add(btnAct);

        central.add(scroll, BorderLayout.CENTER);
        central.add(resumen, BorderLayout.EAST);

        // Botones acción
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        botones.setBackground(NEGRO);
        botones.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        JButton btnTomar = crearBotonAccion("▶ Tomar pedido", VINO);
        btnTomar.addActionListener(e -> tomarPedido());
        JButton btnListo = crearBotonAccion("✔ Marcar listo", GRIS_CARBON);
        btnListo.addActionListener(e -> marcarListo());
        JButton btnEntregado = crearBotonAccion("✅ Marcar entregado", new Color(46, 125, 50));
        btnEntregado.addActionListener(e -> marcarEntregado());
        JButton btnActualizar = crearBotonAccion("🔄 Actualizar", GRIS_CARBON);
        btnActualizar.addActionListener(e -> actualizarTabla("TODOS"));
        JButton btnCerrarBot = crearBotonAccion("Cerrar Sesión", NEGRO);
        btnCerrarBot.setBorder(BorderFactory.createLineBorder(new Color(255, 100, 100)));
        btnCerrarBot.setForeground(new Color(255, 100, 100));
        btnCerrarBot.addActionListener(e -> cerrarSesion());
        botones.add(btnTomar);
        botones.add(btnListo);
        botones.add(btnEntregado);
        botones.add(btnActualizar);
        botones.add(btnCerrarBot);

        Interfaz.add(filtros, BorderLayout.NORTH);
        Interfaz.add(central, BorderLayout.CENTER);
        Interfaz.add(botones, BorderLayout.SOUTH);

        // ── PIE DE PÁGINA ──────────────────────────────
        PiePag = new JPanel(new BorderLayout());
        PiePag.setBackground(NEGRO);
        PiePag.setPreferredSize(new Dimension(1280, 47));
        PiePag.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        JLabel lblPower = new JLabel("  Powered by StarTech");
        lblPower.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPower.setForeground(BLANCO);
        PiePag.add(lblPower, BorderLayout.WEST);

        // ── ARMADO FINAL ───────────────────────────────
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(BLANCO);
        centro.add(BarraNav, BorderLayout.WEST);
        centro.add(Interfaz, BorderLayout.CENTER);

        Fondo.add(Encabezado, BorderLayout.NORTH);
        Fondo.add(centro, BorderLayout.CENTER);
        Fondo.add(PiePag, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(Fondo);
        pack();
    }

    // ── LÓGICA ─────────────────────────────────────────
    private void tomarPedido() {
        if (gestorCocina == null) { avisoSinGestor(); return; }
        PedidoCocina pedido = gestorCocina.tomarSiguientePedido();
        if (pedido == null) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getIdPedidoCocina() + " tomado.\nCliente: " + pedido.getNombreCliente(),
                "Pedido tomado", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTabla("TODOS");
    }

    private void marcarListo() {
        if (gestorCocina == null) { avisoSinGestor(); return; }
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Marcar pedido #" + id + " como LISTO?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestorCocina.marcarComoListo(id);
            JOptionPane.showMessageDialog(this, "Pedido #" + id + " marcado como LISTO.", "Listo", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTabla("TODOS");
    }

    private void marcarEntregado() {
        if (gestorCocina == null) { avisoSinGestor(); return; }
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Marcar pedido #" + id + " como ENTREGADO?\nSe descontará el stock.", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            gestorCocina.marcarComoEntregado(id);
            JOptionPane.showMessageDialog(this, "Pedido #" + id + " marcado como ENTREGADO.", "Entregado", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTabla("TODOS");
    }

    private void cerrarSesion() {
        int r = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginGUI().setVisible(true);
        }
    }

    public void actualizarTabla(String filtro) {
        modeloTabla.setRowCount(0);
        int pendientes = 0, enPrep = 0, listos = 0;
        if (gestorCocina == null) return;
        for (PedidoCocina p : gestorCocina.getColaPendientes()) {
            pendientes++;
            if (filtro.equals("TODOS") || filtro.equals("PENDIENTE"))
                modeloTabla.addRow(new Object[]{p.getIdPedidoCocina(), p.getNombreCliente(), formatearItems(p), "PENDIENTE", p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())});
        }
        for (PedidoCocina p : gestorCocina.getEnPreparacion()) {
            enPrep++;
            if (filtro.equals("TODOS") || filtro.equals("EN_PREPARACION"))
                modeloTabla.addRow(new Object[]{p.getIdPedidoCocina(), p.getNombreCliente(), formatearItems(p), "EN PREP.", p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())});
        }
        for (PedidoCocina p : gestorCocina.getListos()) {
            listos++;
            if (filtro.equals("TODOS") || filtro.equals("LISTO"))
                modeloTabla.addRow(new Object[]{p.getIdPedidoCocina(), p.getNombreCliente(), formatearItems(p), "LISTO", p.getTipoOrigen(), String.format("Bs.%.2f", p.calcularTotal())});
        }
        if (lblPendientes != null) lblPendientes.setText("Pendientes: " + pendientes);
        if (lblEnPrep != null) lblEnPrep.setText("En prep.: " + enPrep);
        if (lblListos != null) lblListos.setText("Listos: " + listos);
    }

    private String formatearItems(PedidoCocina p) {
        if (p.getItems() == null || p.getItems().isEmpty()) return "Sin items";
        StringBuilder sb = new StringBuilder();
        p.getItems().forEach(d -> sb.append(d.getCantidad()).append("x ").append(d.getProducto().getNombre()).append(", "));
        String r = sb.toString();
        return r.length() > 2 ? r.substring(0, r.length() - 2) : r;
    }

    private void avisoSinGestor() {
        JOptionPane.showMessageDialog(this, "Sin conexión al gestor.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void activarBoton(JButton boton) {
        if (btnActivo != null) {
            btnActivo.setBackground(NEGRO);
            btnActivo.setForeground(BLANCO);
        }
        boton.setBackground(VINO);
        boton.setForeground(BLANCO);
        btnActivo = boton;
    }

    private JButton crearBotonNav(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(activo ? VINO : NEGRO);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(250, 60));
        btn.setPreferredSize(new Dimension(250, 60));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(color);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 40));
        return btn;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestorCocina2GUI().setVisible(true));
    }
}