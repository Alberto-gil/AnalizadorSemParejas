package Venatanas;

import AnalizadorLex_Sem.Analisis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;
import java.awt.
        SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.TextArea;
import java.awt.Scrollbar;


public class Interfaz extends JFrame {

	private JPanel contentPane;
	private String Ruta="";


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public static TextArea textLex,textSin;
    public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(SystemColor.controlText);
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnSalir.setBounds(300, 400, 89, 27);
		contentPane.add(btnSalir);
		
		JButton btnArchG = new JButton("Guardar Archivo");		
		btnArchG.setForeground(Color.WHITE);
		btnArchG.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnArchG.setBackground(Color.BLACK);
		btnArchG.setBounds(270, 250, 146, 27);
		contentPane.add(btnArchG);
		
		JButton btnArch = new JButton("Cargar Archivo");		
		btnArch.setForeground(Color.WHITE);
		btnArch.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnArch.setBackground(Color.BLACK);
		btnArch.setBounds(270, 300, 146, 27);
		contentPane.add(btnArch);
		
		TextArea textin = new TextArea();
		textin.setBounds(10, 10, 680, 200);
		contentPane.add(textin);
		
		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(373, 11, 17, 159);
		contentPane.add(scrollbar);
		
		JLabel lblNewLabel_1 = new JLabel("Componentes Lexicos");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(22, 215, 153, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPilaSintactica = new JLabel("Pila Semantica");
		lblPilaSintactica.setForeground(Color.BLACK);
		lblPilaSintactica.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblPilaSintactica.setBounds(540, 215, 153, 14);
		contentPane.add(lblPilaSintactica);
		
		JButton btnAnalizar = new JButton("Analizar");	
		btnAnalizar.setForeground(Color.WHITE);
		btnAnalizar.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
		btnAnalizar.setBackground(Color.BLACK);
		btnAnalizar.setBounds(270, 350, 146, 27);
		contentPane.add(btnAnalizar);
		
		textLex = new TextArea();
		textLex.setBounds(10, 235, 230, 214);
		contentPane.add(textLex);
		
		textSin = new TextArea();
		textSin.setBounds(450, 235, 230, 214);
		contentPane.add(textSin);
		
		
		JLabel lblNewLabel = new JLabel("");		
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 700, 460);
		contentPane.add(lblNewLabel);
		setUndecorated(true);
	    this.setLocationRelativeTo(null);
	    
	    
	    
	    //Acciones de botones
	    
	    btnArch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File obf = null;
				 JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "TXT","txt","JAC","jac");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(getParent());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	textin.setText("");
				    	obf=chooser.getSelectedFile();
				    	Txt obx=new Txt(obf.getAbsolutePath());// abrimos el contenido del archivo
				    	//
                        //
                        // System.out.println(obf);
				    	Ruta=obf+"";
						textin.setText(obx.getTexto());	// se lo asignamos al control
				    }
				    else{
				    	textin.setText("");
				    	textin.setText("---------------------------------------------No se cargo ningun archivo--------------------------------");
				    	
				    }
			   			
			}
		});
	    
	    btnArchG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					FileWriter f=new FileWriter(Ruta);
					f.write(textin.getText());
					f.close();
				}catch(IOException e1) 
				{
				}
			}
	    });
	    btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	    
	    btnAnalizar.addActionListener(new ActionListener() {// analizarrr
			public void actionPerformed(ActionEvent e) {
				
				Analisis ob=new Analisis(textin.getText());
				textLex.setText("");
                textSin.setText("");
                ob.analizar();
//                if(ob.getMsgError().length()>0){
//                    textSin.setText(ob.getMsgError().toString());
//                }
//				textLex.setText(ob.getMsgError().toString());
            }
		});
	}

}
