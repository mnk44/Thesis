package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import services.AreaService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

import classes.Area;
import classes.User;
import extras.TablesInfo;

public class CenterView {

	private JFrame principal_view;

	User user_conected;
	public static boolean faild_change = false;

	@SuppressWarnings("unchecked")
	ArrayList<Area> areasList = (ArrayList<Area>) AreaService.getAreas();

	private JMenuBar menu;
	private JMenu user_options;
	private JMenu admin_options;
	private JMenu boss_options;
	private JMenu oper_options;
	private JMenu reports_options;
	private JMenuItem change_pass;
	private JMenuItem personal_info;
	private JMenuItem close_sesion;
	private JPanel personal_infor_panel;
	private JMenuItem area_management_button;
	private JPanel area_manage;

	private static DefaultTableModel date;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CenterView window = new CenterView(null);
					window.principal_view.setLocationRelativeTo(null);
					window.principal_view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CenterView(User user) throws SQLException {
		initialize(user);
	}

	private void initialize(User user) throws SQLException {
		user_conected = user;
		principal_view = new JFrame();
		principal_view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, "�Desea salir del sistema?", "Salir", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(option == 0){
					System.exit(0);
				}
			}
		});
		principal_view.getContentPane().setBackground(Color.WHITE);
		principal_view.getContentPane().setLayout(null);		
		principal_view.setTitle("Sistema de entrenamiento SECPROIT");
		principal_view.setIconImage(Toolkit.getDefaultToolkit().getImage(CenterView.class.getResource("/images/mini-logo.png")));
		principal_view.setBackground(Color.WHITE);
		principal_view.setForeground(Color.BLACK);
		principal_view.setBounds(100, 100, 1064, 639);
		principal_view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		menu = new JMenuBar();
		menu.setBackground(new Color(243,193,67));
		principal_view.setJMenuBar(menu);

		user_options = new JMenu("Usuario");
		user_options.setBackground(new Color(243,193,67));
		user_options.setFont(new Font("Segoe UI", Font.BOLD, 20));
		user_options.setForeground(Color.WHITE);
		menu.add(user_options);

		change_pass = new JMenuItem("Cambiar contrase\u00F1a");
		change_pass.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		change_pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!faild_change){
					ChangePassView cp = new ChangePassView(user_conected);
					cp.setLocationRelativeTo(principal_view);
					cp.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "Demasiados intentos fallidos, contacte con un administrador", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		change_pass.setIcon(new ImageIcon(CenterView.class.getResource("/images/change-pass.png")));
		change_pass.setFont(new Font("Segoe UI", Font.BOLD, 19));
		change_pass.setBackground(new Color(243,193,67));
		change_pass.setForeground(Color.WHITE);
		user_options.add(change_pass);

		{
			personal_infor_panel = new JPanel();
			personal_infor_panel.setVisible(false);
			personal_infor_panel.setBackground(Color.WHITE);
			personal_infor_panel.setBounds(249, 16, 579, 536);
			principal_view.getContentPane().add(personal_infor_panel);
			personal_infor_panel.setLayout(null);
		}

		{
			area_manage = new JPanel();
			area_manage.setVisible(false);
			area_manage.setBackground(Color.WHITE);
			area_manage.setBounds(29, 16, 1014, 536);
			principal_view.getContentPane().add(area_manage);
			area_manage.setLayout(null);
		}

		personal_info = new JMenuItem("Informaci\u00F3n personal");
		personal_info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		personal_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				personal_infor_panel.setVisible(true);
				try {
					ChargeComponents.personalInfo(personal_infor_panel, user_conected);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		personal_info.setFont(new Font("Segoe UI", Font.BOLD, 19));
		personal_info.setIcon(new ImageIcon(CenterView.class.getResource("/images/personal-info.png")));
		personal_info.setBackground(new Color(243,193,67));
		personal_info.setForeground(Color.WHITE);
		user_options.add(personal_info);

		close_sesion = new JMenuItem("Cerrar sesi\u00F3n");
		close_sesion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		close_sesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginView log = new LoginView();
				log.setLocationRelativeTo(principal_view);
				log.setVisible(true);
				CenterView.this.principal_view.setVisible(false);
			}
		});
		close_sesion.setIcon(new ImageIcon(CenterView.class.getResource("/images/exit.png")));
		close_sesion.setFont(new Font("Segoe UI", Font.BOLD, 19));
		close_sesion.setBackground(new Color(243,193,67));
		close_sesion.setForeground(Color.WHITE);
		user_options.add(close_sesion);

		admin_options = new JMenu("Administrador");
		admin_options.setBackground(new Color(243,193,67));
		admin_options.setFont(new Font("Segoe UI", Font.BOLD, 20));
		admin_options.setForeground(Color.WHITE);
		menu.add(admin_options);

		area_management_button = new JMenuItem("Gesti\u00F3n de \u00E1reas");
		area_management_button.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		area_management_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				area_manage.setVisible(true);
				try {
					ChargeComponents.areaManagement(area_manage, areasList, user_conected);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		area_management_button.setIcon(new ImageIcon(CenterView.class.getResource("/images/areas.png")));
		area_management_button.setFont(new Font("Segoe UI", Font.BOLD, 19));
		area_management_button.setBackground(new Color(243,193,67));
		area_management_button.setForeground(Color.WHITE);
		admin_options.add(area_management_button);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JMenuItem mntmGestinDeUsuarios = new JMenuItem("Gesti\u00F3n de usuarios");
		mntmGestinDeUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		mntmGestinDeUsuarios.setIcon(new ImageIcon(CenterView.class.getResource("/imgs/icons8_User_Groups_16.png")));
		mntmGestinDeUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 19));
		mntmGestinDeUsuarios.setBackground(new Color(244, 164, 96));
		mntmGestinDeUsuarios.setForeground(Color.WHITE);
		admin_options.add(mntmGestinDeUsuarios);

		boss_options = new JMenu("Jefe de \u00E1rea");
		boss_options.setBackground(new Color(244, 164, 96));
		boss_options.setFont(new Font("Segoe UI", Font.BOLD, 20));
		boss_options.setForeground(Color.WHITE);
		menu.add(boss_options);

		JMenuItem mntmGestinDeProcesos = new JMenuItem("Gesti\u00F3n de procesos");
		mntmGestinDeProcesos.setIcon(new ImageIcon(CenterView.class.getResource("/imgs/icons8_Compose_16.png")));
		mntmGestinDeProcesos.setFont(new Font("Segoe UI", Font.BOLD, 19));
		mntmGestinDeProcesos.setBackground(new Color(244, 164, 96));
		mntmGestinDeProcesos .setForeground(Color.WHITE);
		boss_options.add(mntmGestinDeProcesos);

		oper_options = new JMenu("Operario");
		oper_options.setBackground(new Color(244, 164, 96));
		oper_options.setFont(new Font("Segoe UI", Font.BOLD, 20));
		oper_options.setForeground(Color.WHITE);
		menu.add(oper_options);

		JMenuItem mntmRealizarEntrenamiento = new JMenuItem("Realizar entrenamiento");
		mntmRealizarEntrenamiento.setIcon(new ImageIcon(CenterView.class.getResource("/imgs/icons8_Pass_Fail_16.png")));
		mntmRealizarEntrenamiento.setFont(new Font("Segoe UI", Font.BOLD, 19));
		mntmRealizarEntrenamiento.setBackground(new Color(244, 164, 96));
		mntmRealizarEntrenamiento.setForeground(Color.WHITE);
		oper_options.add(mntmRealizarEntrenamiento);

		reports_options = new JMenu("Reportes");
		reports_options.setForeground(Color.WHITE);
		reports_options.setFont(new Font("Segoe UI", Font.BOLD, 20));
		reports_options.setBackground(new Color(244, 164, 96));
		menu.add(reports_options);

		JMenuItem mntmControlDeAcciones = new JMenuItem("Control de acciones");
		mntmControlDeAcciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TracesSystemView tracs = null;
				try {
					tracs = new TracesSystemView();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tracs.setLocationRelativeTo(principal_view);
				tracs.setVisible(true);
			}
		});
		mntmControlDeAcciones.setIcon(new ImageIcon(CenterView.class.getResource("/imgs/list.png")));
		mntmControlDeAcciones.setForeground(Color.WHITE);
		mntmControlDeAcciones.setFont(new Font("Segoe UI", Font.BOLD, 19));
		mntmControlDeAcciones.setBackground(new Color(244, 164, 96));
		reports_options.add(mntmControlDeAcciones);
	}

	public JFrame getFrame() {
		return principal_view;
	}

	public static void failValue(){
		faild_change = true;
	}

	public static void areaTable(ArrayList<Area> areas, JTable table, JButton update_area, JButton delete_area, JTextField find_field) throws SQLException{
		TablesInfo.getAreas(date, table, areas);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.getColumnModel().getColumn(1).setCellRenderer(tcr);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		update_area.setEnabled(false);
		delete_area.setEnabled(false);
		if(find_field.getText().equals("Buscar")){
			find_field.setCaretPosition(0);
		}
	}
}
