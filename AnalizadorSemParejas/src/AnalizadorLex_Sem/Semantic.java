package AnalizadorLex_Sem;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Vector;

public class Semantic {
    public ArrayList<Vector<String>> tablaP;

    private Boolean isError = false;
    private StringBuilder msgError;
    private ArrayDeque<String> pilaC, pilaER, pilaE;
    private String msg = "";
    private Vector<String> noms, tipos;
    private String tipoId = "", tipo_final = "";

    public Semantic() {
        pilaC  = new ArrayDeque<>();
        pilaE = new ArrayDeque<>();
        pilaER  = new ArrayDeque<>();
    }

    public void setTablaP(ArrayList<Vector<String>> tabla) {
        tablaP = tabla;
        noms = tablaP.get(0);
        tipos = tablaP.get(1);
    }

    public StringBuilder getMsgError() {
        return msgError;
    }

    public void setMsgError(StringBuilder msg) {
        msgError = msg;
    }
    public Boolean isError() {
        return isError;
    }
    public void setIsError(Boolean val) {
        isError = val;
    }

    public String getEstatus() {
        return msg;
    }
    public void analizarP(int prod, String nom) {//Recibe la produccion actual
        String tipo1 = "", tipo2 = "";
        int pos = -1;
        switch(prod) {
            //Ya no hay mas declaraciones
            //TIPOS DE DATOS QUE MANEJA Y ANALIZA SI SON COMPATIBLES O NO
            case 3: tipoId = "int"; break;
            case 4: tipoId = "float"; break;
            case 5: tipoId = "char"; break;
            //Valida si es compatible antes de realizar una inicializacion
            case 6: case 7:
                tipo1 = tipoId;
                tipo2 = tipo_final;
                switch(tipo1) {
                    case "float":
                        if(!tipo2.equals("int") && !tipo2.equals("float"))
                            isError = true;
                        break;
                    default:
                        if(!tipo2.equals(tipo1))
                            isError = true;
                }
                if(isError) {
                    msgError.append("\n\nLos tipos de dato son incompatibles " + tipo1 + " - " + tipo2);
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta","Datos incompatibles",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            //Operaciones aritmeticas con E'
            case 9: case 10: case 12: case 13:
                tipo1 = pilaC.pop();
                tipo2 = pilaC.pop();
                if(tipo1.equals("int")) {
                    if(tipo2.equals("int"))
                        pilaC.push("int");
                    else if(tipo2.equals("float"))
                        pilaC.push("float");
                    else
                        isError = true;
                }else if(tipo1.equals("float")) {
                    if(tipo2.equals("int") || tipo2.equals("float"))
                        pilaC.push("float");
                    else
                        isError = true;
                }else
                    isError = true;
                if(isError) {
                    msgError.append("\n\nLos tipos de dato son incompatibles " + tipo1 + " - " + tipo2);
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta","Datos incompatibles",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 14: case 16://Identificador F --> id	//prob aqui
                pos = noms.indexOf(nom);
                //No esta en la tabla de simbolos por lo tanto error semantico
                if(pos == -1) {
                    msgError.append("\n\nEl identificador "+nom+" no esta registrado");
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta","Id no registrado",
                            JOptionPane.ERROR_MESSAGE);
                    isError = true;
                }
                else {
                    pilaC.push(tipos.get(pos));
                }
                break;
            case 15:
                pilaC.push(tipo_final);
            case 11:
                if(!pilaE.isEmpty()) {
                    tipo_final = pilaE.getFirst();
                    pilaER.push(pilaE.pop());
                }
                msgError.append("\n\n").append(pilaE);
//                System.out.println(pilaE);
                break;
            case 8:
                tipo_final = pilaC.getFirst();
                pilaE.push(pilaC.pop());
                break;
        }
    }

    public void analizarE(String estado, String nombre, String componente ) {
        int pos = 0;
        switch(estado) {
            //Asignaciones en sentencias
            case "I7":

                pos = noms.indexOf(nombre);
                if(pos==-1) {
                    isError=true;
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                            "Id no declarado",
                            JOptionPane.ERROR_MESSAGE);
                    msgError.append("\n\nEl identificador "+nombre+" no esta declarado");
                }else
                    tipoId = tipos.get(pos);

                break;
            //Declaraciones
            case "I8": case "I18":
                //Si ese nombre ya esta guardado es un error
                if(noms.contains(nombre)) {
                    isError = true;
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                            "Variable ya declarada",
                            JOptionPane.ERROR_MESSAGE);
                    msgError.append("\n\nLa variable "+nombre+" ya esta declarada");
                }else {
                    noms.add(nombre);
                    tipos.add(tipoId);

                }
                break;
            case "I17":
                //Recibe un id
                pos = noms.indexOf(nombre);
                if(pos==-1) {
                    isError=true;
                    JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                            "Id no declarado",
                            JOptionPane.ERROR_MESSAGE);
                    msgError.append("\n\nEl identificador "+nombre+" no esta declarado");
                }
                break;
        }
    }
}
