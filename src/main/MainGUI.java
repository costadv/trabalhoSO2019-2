package main;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PseudoColumnUsage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import structure.CpuRobin;
import structure.CpuRobin;
import structure.Execute;
import structure.ProcessScreen;
import structure.QueueRobin;
import java.awt.Font;
import javax.swing.JProgressBar;

public class MainGUI {

	private JFrame frame;
	/**
	 * Launch the application.
	 */

	static QueueRobin sjf = new QueueRobin();
	public static int globalPid = 0;
	public static int globalBid = 0;
	static CpuRobin cpu;
	public static boolean executando = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(1920, 1020));
		frame.setPreferredSize(new Dimension(1920, 1020));
		frame.setSize(new Dimension(1920, 1020));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
						JPanel panelRobin = new JPanel();
						panelRobin.setVisible(false);
						
		panelRobin.setBounds(0, 0, 1919, 1017);
		panelRobin.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelRobin.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panelRobin);
		panelRobin.setLayout(null);
		
				JLabel lblCpuCores = new JLabel("CPU Cores");
				lblCpuCores.setHorizontalTextPosition(SwingConstants.CENTER);
				lblCpuCores.setHorizontalAlignment(SwingConstants.CENTER);
				lblCpuCores.setForeground(Color.WHITE);
				lblCpuCores.setBounds(64, 52, 63, 14);
				panelRobin.add(lblCpuCores);
				
						JLabel lblQueue = new JLabel("Queue");
						lblQueue.setHorizontalTextPosition(SwingConstants.CENTER);
						lblQueue.setHorizontalAlignment(SwingConstants.CENTER);
						lblQueue.setForeground(Color.WHITE);
						lblQueue.setBounds(64, 160, 47, 14);
						panelRobin.add(lblQueue);
						
								JButton btnNewProcess = new JButton("Novo Processo");
								btnNewProcess.setBackground(Color.CYAN);
								btnNewProcess.setHorizontalTextPosition(SwingConstants.LEADING);
								btnNewProcess.setBounds(315, 14, 164, 23);
								panelRobin.add(btnNewProcess);
								
										JButton btnStart = new JButton("Iniciar");
										btnStart.setBackground(Color.GREEN);
										btnStart.setForeground(Color.BLACK);
										btnStart.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												
												if(!executando) {
													executando = true;
													Execute ex = new Execute(cpu);
													ex.start();	
												}else {
													JOptionPane.showMessageDialog(null, "Escalonador ja esta em execucao!", "Round Robin", JOptionPane.PLAIN_MESSAGE);
												}
												
											}
										});
										btnStart.setHorizontalTextPosition(SwingConstants.LEADING);
										btnStart.setBounds(525, 16, 116, 23);
										panelRobin.add(btnStart);
										
												JLabel lblValorQuantum = new JLabel("Valor Quantum");
												lblValorQuantum.setHorizontalAlignment(SwingConstants.CENTER);
												lblValorQuantum.setForeground(Color.WHITE);
												lblValorQuantum.setBounds(143, 11, 116, 28);
												panelRobin.add(lblValorQuantum);
												
														JLabel lblQuantum = new JLabel("Quantum:");
														lblQuantum.setHorizontalAlignment(SwingConstants.CENTER);
														lblQuantum.setForeground(Color.WHITE);
														lblQuantum.setBounds(64, 11, 116, 28);
														panelRobin.add(lblQuantum);
														
														JLabel lblMemory = new JLabel("Memory");
														lblMemory.setHorizontalTextPosition(SwingConstants.CENTER);
														lblMemory.setHorizontalAlignment(SwingConstants.CENTER);
														lblMemory.setForeground(Color.WHITE);
														lblMemory.setBounds(64, 700, 47, 14);
														panelRobin.add(lblMemory);
														
														JProgressBar memoryProgressBar = new JProgressBar();
														memoryProgressBar.setToolTipText("");
														memoryProgressBar.setForeground(Color.GREEN);
														memoryProgressBar.setMinimum(0);
														memoryProgressBar.setBounds(121, 700, 146, 23);
														panelRobin.add(memoryProgressBar);
		
				JPanel panelMenu = new JPanel();
				panelMenu.setBackground(Color.DARK_GRAY);
				panelMenu.setBounds(0, 0, 1919, 1017);
				frame.getContentPane().add(panelMenu);
				panelMenu.setLayout(null);
				
						JTextPane textPaneTotalCores = new JTextPane();
						textPaneTotalCores.setBounds(891, 345, 122, 20);
						panelMenu.add(textPaneTotalCores);
						
								JTextPane textPanePreLoaded = new JTextPane();
								textPanePreLoaded.setBounds(891, 289, 122, 20);
								panelMenu.add(textPanePreLoaded);

								
								JLabel lblTamanhoTotalMemoria = new JLabel("Tamanho total de Mem\u00F3ria");
								lblTamanhoTotalMemoria.setHorizontalTextPosition(SwingConstants.CENTER);
								lblTamanhoTotalMemoria.setHorizontalAlignment(SwingConstants.CENTER);
								lblTamanhoTotalMemoria.setForeground(Color.WHITE);
								lblTamanhoTotalMemoria.setBounds(882, 376, 140, 14);
								panelMenu.add(lblTamanhoTotalMemoria);
								
								JTextPane textPaneTamanhoMemoria = new JTextPane();
								textPaneTamanhoMemoria.setBounds(891, 400, 122, 20);
								panelMenu.add(textPaneTamanhoMemoria);
								
										JLabel lblQuantidadeDeCores = new JLabel("Quantidade de Cores");
										lblQuantidadeDeCores.setHorizontalTextPosition(SwingConstants.CENTER);
										lblQuantidadeDeCores.setHorizontalAlignment(SwingConstants.CENTER);
										lblQuantidadeDeCores.setForeground(Color.WHITE);
										lblQuantidadeDeCores.setBounds(891, 320, 122, 14);
										panelMenu.add(lblQuantidadeDeCores);
										
												JLabel lblQuantidadeDeProcessos = new JLabel("Quantidade de Processos Pre-Carregados");
												lblQuantidadeDeProcessos.setHorizontalAlignment(SwingConstants.CENTER);
												lblQuantidadeDeProcessos.setHorizontalTextPosition(SwingConstants.CENTER);
												lblQuantidadeDeProcessos.setForeground(Color.WHITE);
												lblQuantidadeDeProcessos.setBounds(809, 264, 287, 14);
												panelMenu.add(lblQuantidadeDeProcessos);
												
														JButton btnRoundRobin = new JButton("Round Robin");
														btnRoundRobin.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent arg0) {

																int totalCores = Integer.parseInt("" + textPaneTotalCores.getText());
																int preLoaded = Integer.parseInt("" + textPanePreLoaded.getText());
																int memorySpace = Integer.parseInt("" + textPaneTamanhoMemoria.getText());
																memoryProgressBar.setMaximum(memorySpace);
																cpu = new CpuRobin(totalCores, memorySpace, panelRobin);
																for (int i = 0; i < preLoaded; i++) {
																	cpu.queueProcess(new ProcessScreen(panelRobin, false, new structure.Process()));
																	memoryProgressBar.setValue(cpu.getMemoryUsed());
																	memoryProgressBar.updateUI();
																}
																panelMenu.setVisible(false);
																panelRobin.setVisible(true);
																cpu.organizePanels();
																lblValorQuantum.setText(cpu.getQuantum() + "");
																JOptionPane.showMessageDialog(null, "Quantidade de Cores: " + totalCores
																		+ "\nProcessos Pre-Carregados: " + preLoaded + "\nQuantum: " + cpu.getQuantum(), "Round Robin",
																		JOptionPane.PLAIN_MESSAGE);
															}
														});
														btnRoundRobin.setBounds(891, 479, 122, 23);
														panelMenu.add(btnRoundRobin);
														
														btnNewProcess.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent arg0) {

																cpu.queueProcess(new ProcessScreen(panelRobin, true, new structure.Process()));
																cpu.organizePanels();
																memoryProgressBar.setValue(cpu.getMemoryUsed());
																memoryProgressBar.updateUI();

															}
														});
	}
}
