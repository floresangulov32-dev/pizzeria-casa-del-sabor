package pizzeria.IU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InterfazCliente extends JFrame {
    
    private String nombreUsuario;
    private String rolUsuario;
    private JButton btnActivo = null;
    private Image logoImagen;
    private Image fondoImagen;
    
    // Componentes
    private JPanel Encabezado;
    private JPanel BarraNav;
    private JPanel Interfaz;
    private JPanel PiePag;
    private JButton btnVerMenu;
    private JButton btnVerCombos;
    private JButton btnCerrarSesion;  // Nuevo botón
    
    public InterfazCliente(String rol, String nombre) {
        this.rolUsuario = rol;
        this.nombreUsuario = nombre;
        
        setTitle("La Casa del Sabor - Cliente");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        cargarImagenes();
        initUI();
    }
    
    private void cargarImagenes() {
        try {
            String rutaLogoPng = "resources/imagenes/LogoPizzeria.png";
            String rutaLogoJpg = "resources/imagenes/LogoPizzeria.jpg";
            File archivoLogoPng = new File(rutaLogoPng);
            File archivoLogoJpg = new File(rutaLogoJpg);

            if (archivoLogoPng.exists()) {
                ImageIcon logoIcon = new ImageIcon(archivoLogoPng.getAbsolutePath());
                logoImagen = logoIcon.getImage();
                System.out.println("Logo cargado desde: " + archivoLogoPng.getAbsolutePath());
            } else if (archivoLogoJpg.exists()) {
                ImageIcon logoIcon = new ImageIcon(archivoLogoJpg.getAbsolutePath());
                logoImagen = logoIcon.getImage();
                System.out.println("Logo cargado desde: " + archivoLogoJpg.getAbsolutePath());
            } else {
                logoImagen = null;
            }
        } catch (Exception e) {
            System.out.println("Error cargando logo: " + e.getMessage());
            logoImagen = null;
        }
        
        try {
            String rutaFondo = "resources/imagenes/FondoBlanco.png";
            File archivoFondo = new File(rutaFondo);
            if (archivoFondo.exists()) {
                ImageIcon fondoIcon = new ImageIcon(archivoFondo.getAbsolutePath());
                fondoImagen = fondoIcon.getImage();
                System.out.println("Fondo cargado desde: " + archivoFondo.getAbsolutePath());
            } else {
                fondoImagen = null;
            }
        } catch (Exception e) {
            System.out.println("Error cargando fondo: " + e.getMessage());
            fondoImagen = null;
        }
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        
        crearEncabezado();
        crearBarraNav();
        crearPanelCentral();
        crearPiePagina();
        
        add(Encabezado, BorderLayout.NORTH);
        add(BarraNav, BorderLayout.WEST);
        add(Interfaz, BorderLayout.CENTER);
        add(PiePag, BorderLayout.SOUTH);
        
        configurarHover();
        activarBoton(btnVerMenu);
    }
    
    private void crearEncabezado() {
        Encabezado = new JPanel(new BorderLayout());
        Encabezado.setBackground(Color.WHITE);
        Encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(217, 217, 217)));
        Encabezado.setPreferredSize(new Dimension(1280, 100));
        
        // Panel izquierdo - Logo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.X_AXIS));
        panelIzquierdo.setBorder(new EmptyBorder(10, 20, 10, 10));
        
        if (logoImagen != null) {
            Image logoEscalado = logoImagen.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(logoEscalado));
            panelIzquierdo.add(lblLogo);
            panelIzquierdo.add(Box.createRigidArea(new Dimension(15, 0)));
        }
        
        // Panel central - Títulos
        JPanel panelTitulos = new JPanel();
        panelTitulos.setOpaque(false);
        panelTitulos.setLayout(new BoxLayout(panelTitulos, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("LA CASA DEL SABOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(168, 27, 29));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("PIZZERÍA");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(74, 74, 74));
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelTitulos.add(lblTitulo);
        panelTitulos.add(Box.createRigidArea(new Dimension(0, 5)));
        panelTitulos.add(lblSubtitulo);
        
        // Panel derecho - Rol de usuario
        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(new EmptyBorder(10, 10, 10, 20));
        panelDerecho.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblRol = new JLabel("ROL: " + (rolUsuario != null ? rolUsuario.toUpperCase() : "CLIENTE"));
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRol.setForeground(new Color(100, 100, 100));
        lblRol.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblNombre = new JLabel(nombreUsuario != null ? nombreUsuario : "Cliente");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNombre.setForeground(new Color(168, 27, 29));
        lblNombre.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(lblRol);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDerecho.add(lblNombre);
        panelDerecho.add(Box.createVerticalGlue());
        
        Encabezado.add(panelIzquierdo, BorderLayout.WEST);
        Encabezado.add(panelTitulos, BorderLayout.CENTER);
        Encabezado.add(panelDerecho, BorderLayout.EAST);
    }
    
    private void crearBarraNav() {
        BarraNav = new JPanel();
        BarraNav.setBackground(Color.WHITE);
        BarraNav.setPreferredSize(new Dimension(280, 570));
        BarraNav.setLayout(new BoxLayout(BarraNav, BoxLayout.Y_AXIS));

        // Botón Ver Menú
        btnVerMenu = new JButton("Ver Menú");
        btnVerMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnVerMenu.setForeground(new Color(60, 60, 60));
        btnVerMenu.setBackground(Color.WHITE);
        btnVerMenu.setHorizontalAlignment(SwingConstants.LEFT);
        btnVerMenu.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 15));
        btnVerMenu.setFocusPainted(false);
        btnVerMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerMenu.setMaximumSize(new Dimension(280, 50));
        btnVerMenu.setMinimumSize(new Dimension(280, 50));
        btnVerMenu.setPreferredSize(new Dimension(280, 50));
        btnVerMenu.addActionListener(e -> mostrarMenuCompleto());
        BarraNav.add(btnVerMenu);

        // Botón Ver Combos
        btnVerCombos = new JButton("Ver Combos");
        btnVerCombos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnVerCombos.setForeground(new Color(60, 60, 60));
        btnVerCombos.setBackground(Color.WHITE);
        btnVerCombos.setHorizontalAlignment(SwingConstants.LEFT);
        btnVerCombos.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 15));
        btnVerCombos.setFocusPainted(false);
        btnVerCombos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerCombos.setMaximumSize(new Dimension(280, 50));
        btnVerCombos.setMinimumSize(new Dimension(280, 50));
        btnVerCombos.setPreferredSize(new Dimension(280, 50));
        btnVerCombos.addActionListener(e -> mostrarCombos());
        BarraNav.add(btnVerCombos);

        // Espacio flexible (empuja los botones hacia arriba)
        BarraNav.add(Box.createVerticalGlue());

        // Botón Cerrar Sesión (se mantiene abajo)
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnCerrarSesion.setForeground(new Color(168, 27, 29));
        btnCerrarSesion.setBackground(Color.WHITE);
        btnCerrarSesion.setHorizontalAlignment(SwingConstants.LEFT);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 15));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setMaximumSize(new Dimension(280, 50));
        btnCerrarSesion.setMinimumSize(new Dimension(280, 50));
        btnCerrarSesion.setPreferredSize(new Dimension(280, 50));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        BarraNav.add(btnCerrarSesion);
    }
    
    private void crearPanelCentral() {
        Interfaz = new JPanel(new BorderLayout());
        Interfaz.setBackground(Color.WHITE);
        
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondoImagen != null) {
                    g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());
        
        Interfaz.add(panelFondo, BorderLayout.CENTER);
    }
    
    private void crearPiePagina() {
        PiePag = new JPanel(new BorderLayout());
        PiePag.setBackground(Color.WHITE);
        PiePag.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(217, 217, 217)));
        PiePag.setPreferredSize(new Dimension(1280, 50));
        
        JLabel lblEquipo = new JLabel("Equipo StarTech - Desarrollo de Software");
        lblEquipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEquipo.setForeground(new Color(100, 100, 100));
        lblEquipo.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        JLabel lblVersion = new JLabel("Versión: 1.0.0");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblVersion.setForeground(new Color(100, 100, 100));
        lblVersion.setBorder(new EmptyBorder(0, 0, 0, 20));
        lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
        
        PiePag.add(lblEquipo, BorderLayout.WEST);
        PiePag.add(lblVersion, BorderLayout.EAST);
    }
    
    private void configurarHover() {
        JButton[] botones = {btnVerMenu, btnVerCombos, btnCerrarSesion};
        
        for (JButton b : botones) {
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (b != btnActivo && b != btnCerrarSesion) {
                        b.setBackground(new Color(245, 245, 245));
                        b.setForeground(new Color(168, 27, 29));
                    } else if (b == btnCerrarSesion) {
                        b.setBackground(new Color(245, 245, 245));
                        b.setForeground(new Color(200, 0, 0));
                    }
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (b != btnActivo && b != btnCerrarSesion) {
                        b.setBackground(Color.WHITE);
                        b.setForeground(new Color(60, 60, 60));
                    } else if (b == btnCerrarSesion) {
                        b.setBackground(Color.WHITE);
                        b.setForeground(new Color(168, 27, 29));
                    }
                }
            });
        }
    }
    
    private void activarBoton(JButton boton) {
        if (btnActivo != null) {
            btnActivo.setBackground(Color.WHITE);
            btnActivo.setForeground(new Color(60, 60, 60));
            btnActivo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        }
        
        boton.setBackground(new Color(168, 27, 29));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnActivo = boton;
    }
    
    private void cargarPanel(JPanel panel) {
        JPanel panelFondo = (JPanel) Interfaz.getComponent(0);
        panelFondo.removeAll();
        panelFondo.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panelFondo.add(panel, BorderLayout.CENTER);
        panelFondo.revalidate();
        panelFondo.repaint();
    }
    
    private void mostrarMenuCompleto() {
        FrameVerMenu ventana = new FrameVerMenu(rolUsuario, nombreUsuario);
        ventana.setVisible(true);
    }
    
    private void mostrarCombos() {
        activarBoton(btnVerCombos);
        JLabel lblMensaje = new JLabel("Combos - Próximamente", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMensaje.setForeground(new Color(168, 27, 29));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(lblMensaje, BorderLayout.CENTER);
        cargarPanel(panel);
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea cerrar sesión?",
            "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual
            new PantallaInicial().setVisible(true); // Abre la pantalla inicial
        }
    }
    
    public static void main(String args[]) {
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
        
        java.awt.EventQueue.invokeLater(() -> new InterfazCliente("CLIENTE", "Cliente Demo").setVisible(true));
    }
}