package AnalizadorLex_Sem;

import Venatanas.Interfaz;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Hashtable;

public class Analisis {
    Lexic lexico = new Lexic();
    public StringBuilder msgError = new StringBuilder(35);
    boolean error;
    ArrayDeque<String> pila = new ArrayDeque<>();
    String[] simbolos = {"id","int","float","char",",",";","+","-","*","/","(",")","=","$","P","Tipo","V","A","E","T","F"};

    //No terminales de cada producci�n
    String[] pn = {"P'","P","P","Tipo","Tipo","Tipo","V","V","A","E","E","E","T","T","T","F","F"};
    int[] pc = {1,3,1,1,1,1,3,2,4,3,3,1,3,3,1,3,1};

    String[][] vals = {
            //  id    int  float  char    ,    ;      +    -     *     /     (     )     =     $     P    Tipo   V     A     E     T     F
            /*0*/{ "I7", "I4", "I5", "I6", null, null, null, null, null, null, null, null, null, null, "I1", "I2", null, "I3", null, null, null},
            /*1*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "0", null, null, null, null, null, null, null},
            /*2*/{ "I8", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*3*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "2", null, null, null, null, null, null, null},
            /*4*/{  "3", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*5*/{  "4", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*6*/{  "5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*7*/{ null, null, null, null, null, null, null, null, null, null, null, null, "I9", null, null, null, null, null, null, null, null},
            /*8*/{ null, null, null, null,"I11","I12", null, null, null, null, null, null, null, null, null, null,"I10", null, null, null, null},
            /*9*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null,"I13","I14","I15"},
            /*10*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "1", null, null, null, null, null, null, null},
            /*11*/{"I18", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*12*/{ "I7", "I4", "I5", "I6", null, null, null, null, null, null, null, null, null, null,"I19", "I2", null, "I3", null, null, null},
            /*13*/{ null, null, null, null, null,"I20","I21","I22", null, null, null, null, null, null, null, null, null, null, null, null, null},
            /*14*/{ null, null, null, null, null, "11", "11", "11","I23","I24", null, "11",null, null, null, null, null, null, null, null, null},
            /*15*/{ null, null, null, null, null, "14", "14", "14", "14", "14", null, "14", null, null, null, null, null, null, null, null, null},
            /*16*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null,"I25","I14","I15"},
            /*17*/{ null, null, null, null, null, "16", "16", "16", "16", "16", null, "16", null, null, null, null, null, null, null, null, null},
            /*18*/{ null, null, null, null,"I11","I12", null, null, null, null, null, null, null, null, null, null,"I26", null, null, null, null},
            /*19*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "7", null, null, null, null, null, null, null},
            /*20*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "8", null, null, null, null, null, null, null},
            /*21*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null, null,"I27","I15"},
            /*22*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null, null,"I28","I15"},
            /*23*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null, null, null,"I29"},
            /*24*/{"I17", null, null, null, null, null, null, null, null, null,"I16", null, null, null, null, null, null, null, null, null,"I30"},
            /*25*/{ null, null, null, null, null, null,"I21","I22", null, null, null,"I31", null, null, null, null, null, null, null, null, null},
            /*26*/{ null, null, null, null, null, null, null, null, null, null, null, null, null,  "6", null, null, null, null, null, null, null},
            /*27*/{ null, null, null, null, null,  "9",  "9",  "9","I23","I24", null,  "9", null, null, null, null, null, null, null, null, null},
            /*28*/{ null, null, null, null, null, "10", "10", "10","I23","I24", null, "10", null, null, null, null, null, null, null, null, null},
            /*29*/{ null, null, null, null, null, "12", "12", "12", "12", "12", null, "12", null, null, null, null, null, null, null, null, null},
            /*30*/{ null, null, null, null, null, "13", "13", "13", "13", "13", null, "13", null, null, null, null, null, null, null, null, null},
            /*31*/{ null, null, null, null, null, "15", "15", "15", "15", "15", null, "15", null, null, null, null, null, null, null, null, null}
    };

    Hashtable<String, HashMap<String, String>> table = new Hashtable<>();
    HashMap<String, String> I;

    public Analisis(String contenido) {
        lexico.setContenido(contenido);
        crearM();
        pila.push("I0");
    }


    void crearM() {
        for(int i = 0; i < vals.length; i++) {
            I = new HashMap<>();
            table.put("I"+i, I);
            for(int pos = 0; pos < vals[0].length; pos++) {
                if(vals[i][pos] != null)
                    I.put(simbolos[pos] , vals[i][pos]);
            }
        }
    }

    public void analizar() {
        //Estado y Nuevo Estado
        String e = "", ne = "";
        int i = 0;
        int cantidad = 0; //Cantidad de producciones a eliminar de la pila

        try {
            lexico.nuevoToken();

            if(lexico.isError()) {
                Interfaz.textLex.setText(lexico.getMsgError().toString());
                JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                        "Error en analisis lexico",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }catch(NullPointerException ex){
            {
                Interfaz.textLex.setText("Analisis Lexico ha termminado con errores");
                JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                        "Error en analisis lexico",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


        String token = lexico.getComponente();	//Lee el token
        String no_term = ""; 	//No terminal de la produccion que se reduzca
        e = pila.getFirst(); 	//Obtiene el ultimo en la pila

        Semantic semantico=new Semantic();
        semantico.setTablaP(lexico.getTSimbolos());
        semantico.setMsgError(msgError);

        while(true) {//La tabla de tranciciones almacena los estados y las producciones
            msgError.append("\n").append(pila).append(" ").append("Token: ").append(token);
            I = table.get(e);
            ne = I.get(token);

            if(ne == null) {

                msgError.append("\nEstado: ").append(e).append("\nToken: ").append(token).append
                        ("\n").append(pila).append("\n\nLa cadena no se acepta");

                JOptionPane.showMessageDialog(null,"La cadena no se acepta","Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
            //Comprobar si se desplaza o se reduce
            if(ne.startsWith("I")) { 	//Es un estado, se desplaza
                pila.push(token); 	//Agrega el token a la pila
                pila.push(ne); 	//Agrega a la pila el estado en el que se quedo
                semantico.analizarE(ne, lexico.getNombre(), token);
                if(semantico.isError()) {
                    msgError.append("\n Renglon "+(lexico.getRenglon()-1));
                    break;
                }

                try {
                    lexico.nuevoToken();

                    if(lexico.isError()) {
                        Interfaz.textLex.setText(lexico.getMsgError().toString());
                        JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                                "Error en analisis lexico",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }catch(NullPointerException ex){
                    {
                        Interfaz.textLex.setText("Analisis Lexico ha termminado con errores");
                        JOptionPane.showMessageDialog(null,"La cadena no se acepta",
                                "Error en analisis lexico",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                token = lexico.getComponente();

                e = ne;
            }else
            { //Si solo es un numero reduce esa produccion
                i = Integer.parseInt(ne);
                lexico.msgError.append("\n").append(lexico.getToken_ant());
//                System.out.println(lexico.getToken_ant());
                semantico.analizarP(i, lexico.getToken_ant());
                if(semantico.isError()) {
                    break;
                }

                if(token.equals("$") && i == 0) {
                    pila.pop();
                    pila.pop();
                    msgError.append(pila).append(" ").append("La cadena es aceptada exitosamente\n");
                    JOptionPane.showMessageDialog(null,"La Cadena es Aceptada",
                            "Analisis finalizado",
                            JOptionPane.INFORMATION_MESSAGE);

                    break;
                }

                no_term = pn[i];
                cantidad = pc[i];
                i = 0;
                while(i < cantidad) {
                    pila.pop(); //Elimina el estado
                    pila.pop(); //Elimina el token (terminal o no terminal)
                    i++;
                }

                e = pila.getFirst(); //Obtiene el ultimo estado de la pila
                pila.push(no_term); //Agrega a la pila el no terminal que hace la producci�n
                I = table.get(e);
                ne = I.get(no_term); //Nuevo estado para buscar el token

                if(ne == null) {

                    msgError.append("\nEstado: ").append(e).append("\nToken: ").append(token).append
                            ("\n").append(pila).append("\n\nLa cadena no se acepta");

                    JOptionPane.showMessageDialog(null,"La cadena no se acepta","Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                pila.push(ne); //Agrega el estado a la pila
                e = ne;
            }
        }
        Interfaz.textSin.setText(semantico.getMsgError().toString());
    }

    public StringBuilder getMsgError() {
        return msgError;
    }

    public static void main(String[] args) {
        Analisis s = new Analisis("int var1, x, y; float var2; char c; var1=c;$");
        s.analizar();
        if(s.getMsgError().length()>0)
            System.err.println(s.getMsgError().toString());
    }
}
