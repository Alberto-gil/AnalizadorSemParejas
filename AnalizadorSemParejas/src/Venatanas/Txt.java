package Venatanas;

import java.io.BufferedReader;
import java.io.FileReader;

public class Txt {
	
	private String texto;
	
	public Txt(String ruta) {
		try{
			BufferedReader bf=new BufferedReader(new FileReader(ruta));
			String temp="";
			String input;
			while((input= bf.readLine())!=null ){
				temp+=input;
				
			}
			this.texto=temp;
		}catch(Exception e)
		{
			this.texto="Error al Abrir el Archivo";
		}
		
	}
	
	public String getTexto(){
		return this.texto;
	}

}
