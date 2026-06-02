package pizzeria.IU;

import pizzeria.controller.GestorCocina;
import pizzeria.controller.GestorReserva;
import pizzeria.controller.GestorVenta;
import pizzeria.model.PedidoCocina;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GestorCocinaGUI extends javax.swing.JFrame {

    // ── Colores del equipo ──
    private static final Color ROJO_VINO   = new Color(168, 27, 29);
    private static final Color GRIS_CARBON = new Color(74, 74, 74);
    private static final Color BLANCO      = new Color(255, 255, 255);
    private static final Color NEGRO       = new Color(0, 0, 0);
    private static final Color GRIS_CLARO  = new Color(217, 217, 217);

    // ── Fuentes ──
    private static final Font FONT_TITULO    = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_TEXTO     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOTON     = new Font("Segoe UI", Font.BOLD, 13);

    // ── Controladores ──
    private GestorCocina  gestorCocina;
    private GestorReserva gestorReserva;
    private GestorVenta   gestorVenta;

    // ── Tablas ──
    private JTable tablaPendientes, tablaPreparacion, tablaListos;
    private DefaultTableModel modeloPendientes, modeloPreparacion, modeloListos;

    // ── Sidebar ──
    private JPanel btnActivo = null;

    // ── Labels contadores ──
    private JLabel lblCntPendientes, lblCntPreparacion, lblCntListos;

    // ── Panel contenido ──
    private JPanel panelContenido;
    private CardLayout cardLayout;

    private String nombreUsuario = "Cocinero";
    private String rolUsuario    = "COCINA";

    public GestorCocinaGUI() {
        initComponentsCustom();
    }

    public GestorCocinaGUI(GestorCocina gestorCocina, GestorReserva gestorReserva, GestorVenta gestorVenta, String nombre, String rol) {
        this.gestorCocina  = gestorCocina;
        this.gestorReserva = gestorReserva;
        this.gestorVenta   = gestorVenta;
        this.nombreUsuario = nombre;
        this.rolUsuario    = rol;
        initComponentsCustom();
        if (gestorCocina != null) actualizarTablas();
    }

    private void initComponentsCustom() {
        setTitle("Pizzería Casa del Sabor — Gestión de Cocina");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BLANCO);

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearBarraNav(),   BorderLayout.WEST);
        add(crearCentro(),     BorderLayout.CENTER);
        add(crearPiePag(),     BorderLayout.SOUTH);
    }

    // ══════════════════════════════════════════
    //  ENCABEZADO
    // ══════════════════════════════════════════
    private JPanel crearEncabezado() {
        JPanel enc = new JPanel(new BorderLayout());
        enc.setBackground(ROJO_VINO);
        enc.setPreferredSize(new Dimension(1280, 80));
        enc.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo + título
        JPanel izq = new JPanel(new GridLayout(2, 1));
        izq.setOpaque(false);
        JLabel lblTitulo = new JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(BLANCO);
        JLabel lblSub = new JLabel("PIZZERIA");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(255, 200, 200));
        izq.add(lblTitulo);
        izq.add(lblSub);

        // Logo placeholder
        JLabel lblLogo = new JLabel("LOGO");
        lblLogo.setFont(FONT_TEXTO);
        lblLogo.setForeground(GRIS_CLARO);
        lblLogo.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(60, 50));

        // Rol usuario
        JLabel lblRol = new JLabel(rolUsuario);
        lblRol.setFont(FONT_SUBTITULO);
        lblRol.setForeground(BLANCO);

        enc.add(lblLogo, BorderLayout.WEST);
        enc.add(izq,     BorderLayout.CENTER);
        enc.add(lblRol,  BorderLayout.EAST);
        return enc;
    }

    // ══════════════════════════════════════════
    //  BARRA LATERAL
    // ══════════════════════════════════════════
    private JPanel crearBarraNav() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(GRIS_CARBON);
        nav.setPreferredSize(new Dimension(200, 640));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        nav.add(crearBotonNav("Inicio",        "menu"));
        nav.add(crearBotonNav("Pedidos",       "pedidos"));
        nav.add(Box.createVerticalGlue());

        JPanel btnCerrar = crearBotonNav("Cerrar Sesión", "cerrar");
        btnCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        nav.add(btnCerrar);
        return nav;
    }

    private JPanel crearBotonNav(String texto, String card) {
        JPanel btn = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        btn.setBackground(GRIS_CARBON);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_BOTON);
        lbl.setForeground(BLANCO);
        btn.add(lbl);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(ROJO_VINO); }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(btnActivo == btn ? ROJO_VINO : GRIS_CARBON);
            }
            public void mouseClicked(MouseEvent e) {
                if (btnActivo != null) btnActivo.setBackground(GRIS_CARBON);
                btnActivo = btn;
                btn.setBackground(ROJO_VINO);
                if (cardLayout != null) cardLayout.show(panelContenido, card);
            }
        });
        return btn;
    }

    // ══════════════════════════════════════════
    //  CENTRO
    // ══════════════════════════════════════════
    private JPanel crearCentro() {
        cardLayout     = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(BLANCO);
        panelContenido.add(crearPanelMenu(),    "menu");
        panelContenido.add(crearPanelPedidos(), "pedidos");
        cardLayout.show(panelContenido, "pedidos");
        return panelContenido;
    }

    // ── Panel menú del día ──
    private JPanel crearPanelMenu() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(BLANCO);
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Nuestras Pizzas del Día", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(ROJO_VINO);

        JLabel sub = new JLabel("¡Frescas, artesanales y llenas de sabor!", SwingConstants.CENTER);
        sub.setFont(FONT_SUBTITULO);
        sub.setForeground(GRIS_CARBON);

        JPanel top = new JPanel(new GridLayout(2, 1, 0, 5));
        top.setOpaque(false);
        top.add(titulo);
        top.add(sub);
        p.add(top, BorderLayout.NORTH);

        String[][] pizzas = {
            {"Margherita",     "Tomate, mozzarella y albahaca",        "Bs. 45"},
            {"Pepperoni",      "Salsa tomate, mozzarella y pepperoni", "Bs. 55"},
            {"Cuatro Quesos",  "Mozzarella, gouda, parmesano y brie",  "Bs. 60"},
            {"Hawaiana",       "Jamón, piña y mozzarella",             "Bs. 50"},
            {"Vegetariana",    "Pimientos, champiñones y aceitunas",   "Bs. 48"},
            {"Casa del Sabor", "Receta especial de la casa",           "Bs. 70"},
        };

        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15));
        grid.setOpaque(false);
        for (String[] pizza : pizzas) {
            JPanel card = new JPanel(new BorderLayout(0, 8));
            card.setBackground(BLANCO);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO_VINO, 2),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));
            JLabel n = new JLabel("🍕 " + pizza[0], SwingConstants.CENTER);
            n.setFont(new Font("Segoe UI", Font.BOLD, 15));
            n.setForeground(ROJO_VINO);
            JLabel d = new JLabel("<html><center>" + pizza[1] + "</center></html>", SwingConstants.CENTER);
            d.setFont(FONT_TEXTO);
            d.setForeground(GRIS_CARBON);
            JLabel pr = new JLabel(pizza[2], SwingConstants.CENTER);
            pr.setFont(new Font("Segoe UI", Font.BOLD, 16));
            pr.setForeground(NEGRO);
            card.add(n,  BorderLayout.NORTH);
            card.add(d,  BorderLayout.CENTER);
            card.add(pr, BorderLayout.SOUTH);
            grid.add(card);
        }
        p.add(grid, BorderLayout.CENTER);
        return p;
    }

    // ── Panel pedidos ──
    private JPanel crearPanelPedidos() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(BLANCO);
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel titulo = new JLabel("Estado de Pedidos en Cocina");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ROJO_VINO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        p.add(titulo, BorderLayout.NORTH);

        String[] cols = {"ID", "Origen", "Items", "Estado"};
        modeloPendientes  = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r,int c){return false;} };
        modeloPreparacion = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r,int c){return false;} };
        modeloListos      = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r,int c){return false;} };

        tablaPendientes  = crearTabla(modeloPendientes);
        tablaPreparacion = crearTabla(modeloPreparacion);
        tablaListos      = crearTabla(modeloListos);

        lblCntPendientes  = new JLabel("0");
        lblCntPreparacion = new JLabel("0");
        lblCntListos      = new JLabel("0");

        JPanel columnas = new JPanel(new GridLayout(1, 3, 12, 0));
        columnas.setOpaque(false);
        columnas.add(crearColumna("⏳ Pendientes",     new Color(180,120,0), tablaPendientes,  lblCntPendientes));
        columnas.add(crearColumna("🔥 En Preparación", ROJO_VINO,            tablaPreparacion, lblCntPreparacion));
        columnas.add(crearColumna("✅ Listos",          new Color(40,110,40), tablaListos,      lblCntListos));
        p.add(columnas, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        botones.setOpaque(false);

        JButton btnTomar     = crearBoton("▶ Tomar Pedido",  ROJO_VINO,            BLANCO);
        JButton btnListo     = crearBoton("✔ Marcar Listo",  new Color(40,110,40), BLANCO);
        JButton btnEntregado = crearBoton("🚀 Entregado",     GRIS_CARBON,          BLANCO);
        JButton btnActualizar= crearBoton("⟳ Actualizar",    NEGRO,                BLANCO);

        btnTomar.addActionListener(e      -> tomarSiguientePedido());
        btnListo.addActionListener(e      -> marcarComoListo());
        btnEntregado.addActionListener(e  -> marcarComoEntregado());
        btnActualizar.addActionListener(e -> actualizarTablas());

        for (JButton b : new JButton[]{btnTomar, btnListo, btnEntregado, btnActualizar}) {
            b.setPreferredSize(new Dimension(180, 40));
            botones.add(b);
        }
        p.add(botones, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearColumna(String titulo, Color colorHeader, JTable tabla, JLabel lblCnt) {
        JPanel col = new JPanel(new BorderLayout());
        col.setBackground(BLANCO);
        col.setBorder(BorderFactory.createLineBorder(GRIS_CLARO));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(colorHeader);
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(FONT_SUBTITULO);
        lbl.setForeground(BLANCO);
        lblCnt.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCnt.setForeground(BLANCO);
        header.add(lbl,    BorderLayout.WEST);
        header.add(lblCnt, BorderLayout.EAST);

        // Buscador
        JTextField buscador = new JTextField("🔍 Buscar...");
        buscador.setFont(FONT_TEXTO);
        buscador.setForeground(Color.GRAY);
        buscador.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GRIS_CLARO),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        buscador.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (buscador.getText().startsWith("🔍")) { buscador.setText(""); buscador.setForeground(NEGRO); }
            }
            public void focusLost(FocusEvent e) {
                if (buscador.getText().isEmpty()) { buscador.setText("🔍 Buscar..."); buscador.setForeground(Color.GRAY); }
            }
        });

        JPanel buscarPanel = new JPanel(new BorderLayout());
        buscarPanel.setBackground(BLANCO);
        buscarPanel.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 6));
        buscarPanel.add(buscador);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(header,      BorderLayout.NORTH);
        top.add(buscarPanel, BorderLayout.SOUTH);

        col.add(top,                    BorderLayout.NORTH);
        col.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return col;
    }

    // ══════════════════════════════════════════
    //  PIE DE PÁGINA
    // ══════════════════════════════════════════
    private JPanel crearPiePag() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(NEGRO);
        pie.setPreferredSize(new Dimension(1280, 35));
        pie.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JLabel izq = new JLabel("© 2025 Pizzería Casa del Sabor");
        izq.setFont(FONT_TEXTO);
        izq.setForeground(new Color(150,150,150));
        JLabel der = new JLabel("Módulo: Gestión de Cocina");
        der.setFont(FONT_TEXTO);
        der.setForeground(new Color(150,150,150));
        pie.add(izq, BorderLayout.WEST);
        pie.add(der, BorderLayout.EAST);
        return pie;
    }

    // ══════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════
    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(FONT_TEXTO);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(GRIS_CLARO);
        tabla.setGridColor(GRIS_CLARO);
        tabla.setSelectionBackground(new Color(255, 210, 210));
        tabla.setFillsViewportHeight(true);
        tabla.setBackground(BLANCO);
        return tabla;
    }

    private JButton crearBoton(String texto, Color fondo, Color letra) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_BOTON);
        btn.setBackground(fondo);
        btn.setForeground(letra);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(fondo.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(fondo); }
        });
        return btn;
    }

    // ══════════════════════════════════════════
    //  LÓGICA
    // ══════════════════════════════════════════
    private void actualizarTablas() {
        if (gestorCocina == null) return;
        modeloPendientes.setRowCount(0);
        modeloPreparacion.setRowCount(0);
        modeloListos.setRowCount(0);
        for (PedidoCocina p : gestorCocina.getColaPendientes())  modeloPendientes.addRow(fila(p));
        for (PedidoCocina p : gestorCocina.getEnPreparacion())   modeloPreparacion.addRow(fila(p));
        for (PedidoCocina p : gestorCocina.getListos())          modeloListos.addRow(fila(p));
        lblCntPendientes.setText(String.valueOf(gestorCocina.getColaPendientes().size()));
        lblCntPreparacion.setText(String.valueOf(gestorCocina.getEnPreparacion().size()));
        lblCntListos.setText(String.valueOf(gestorCocina.getListos().size()));
    }

    private Object[] fila(PedidoCocina p) {
        return new Object[]{ p.getIdPedidoCocina(), p.getTipoOrigen()+" #"+p.getIdOrigen(), p.getItems().size()+" item(s)", p.getEstado() };
    }

    private void tomarSiguientePedido() {
        if (gestorCocina == null) return;
        PedidoCocina pedido = gestorCocina.tomarSiguientePedido();
        if (pedido != null) {
            JOptionPane.showMessageDialog(this, "Pedido #" + pedido.getIdPedidoCocina() + " tomado.", "Pedido tomado", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablas();
        } else {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes.", "Sin pedidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void marcarComoListo() {
        if (gestorCocina == null) return;
        int fila = tablaPreparacion.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Selecciona un pedido EN PREPARACIÓN.", "Selección requerida", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) modeloPreparacion.getValueAt(fila, 0);
        if (gestorCocina.marcarComoListo(id)) { actualizarTablas(); }
    }

    private void marcarComoEntregado() {
        if (gestorCocina == null) return;
        int fila = tablaListos.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Selecciona un pedido LISTO.", "Selección requerida", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) modeloListos.getValueAt(fila, 0);
        if (gestorCocina.marcarComoEntregado(id)) { actualizarTablas(); }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestorCocinaGUI().setVisible(true));
    }
}
