/**
 * ****************************************************************************
 * Smart Robot
 *
 * Inteligencia Artificial: Proyecto No 1 Oscar Bedoya
 *
 * Presentado por: 
 * Madeleine Bustamante - 201410017 
 * Kelly Johana Cordoba - 201410029
 * Yully Andrea Guzman  - 201410055 
 * 
 * Clase: Main Clase principal, donde se almacena la interfaz y acciones
 * principales.
 * ****************************************************************************
 */
package interfaz;

import busquedas.algoritmoNoInfCostoUniforme;
import java.io.File;
import smartrobot2.*;
import busquedas.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Main extends javax.swing.JFrame implements IdObjetos{
    //Elementos Utilizado para  cargar archivo
    private File ambienteArchivo;
    private Archivo archivo;
    private Matriz matriz;
    private Estado estado;
    
    //Necesario para condicionales de items y traje
    ArrayList<Estado> objetivos;
    public Estado[][] matrix;
    public Estado[][] matrixGrafico;
    SitioObjeto inicia = new SitioObjeto();
    Estado init = new Estado();
    
    //Numero de filas de la matriz
    private int _filas; 
    //Numero de columnas de la matriz
    private int _columnas;
  
    //Hilos
    int id_contador=0;
    private int pararHilo = 2;
    private int numeroAlgoritmo=0;
    //Animacion Robot 
    int nodoCaminoSeleccionado = 0;
    private ArrayList<Estado> ruta;
    private int _tamanoSolucion = 0;
    private ArrayList<String> movimientoResultado;
    
    //GUI
    //Arreglo con Iconos necesario para la visualizacion
    private ImageIcon arregloDeImagenes[] = new ImageIcon[9];
    //Matriz de Jlabel
    private PintarfondoCeldas tablero[][]; 
    //Ruta del Fondo
    String path = "/Imagenes/fondo.png";
    URL url = this.getClass().getResource(path);
    private Image fondodetablero = new ImageIcon(url).getImage();
           
    public Main() {
        super("SmartRobot Univalle");
        initComponents();
        bAmplitud.setEnabled(false);
        bAEstrella.setEnabled(false);
        bCostoUniforme.setEnabled(false);
        bAvara.setEnabled(false);
        bProfundidad.setEnabled(false);
        this.objetivos = new ArrayList<>();
        Image icono = Toolkit.getDefaultToolkit().getImage("/Imagenes/univalle.png");
        this.setIconImage(icono);
        ImageIcon ImageIcon = new ImageIcon(getClass().getResource("/Imagenes/univalle.png"));
        Image image = ImageIcon.getImage();
        this.setIconImage(image);
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        //Cargando imagenes
        arregloDeImagenes[0] = null;
        arregloDeImagenes[1] = new ImageIcon(this.getClass().getResource("/Imagenes/Muro.png"));
        arregloDeImagenes[2] = new ImageIcon(this.getClass().getResource("/Imagenes/Robot.png"));
        arregloDeImagenes[3] = new ImageIcon(this.getClass().getResource("/Imagenes/Traje.png"));
        arregloDeImagenes[4] = new ImageIcon(this.getClass().getResource("/Imagenes/Campo1.png"));
        arregloDeImagenes[5] = new ImageIcon(this.getClass().getResource("/Imagenes/Campo2.png"));
        arregloDeImagenes[6] = new ImageIcon(this.getClass().getResource("/Imagenes/Item.png"));
        arregloDeImagenes[7] = new ImageIcon(this.getClass().getResource("/Imagenes/idea.png"));
    }
     
    
    //Se crea el tablero a manejar, el conjunto de labels
    public void crearTablero(Matriz _matriz) {
        panel_Tablero.removeAll();
        int[][] matri = null;
        panel_Tablero.setLayout(new GridLayout(obtenerFilas(), obtenerColumnas()));
        for (int i = 0; i < obtenerFilas(); i++) {
            for (int j = 0; j < obtenerColumnas(); j++) {
                tablero[i][j] = new PintarfondoCeldas();
                tablero[i][j].setImage(fondodetablero);
                tablero[i][j].setIcon(retornarImagenDeCasillas(_matriz.getMatriz()[i][j]));
                tablero[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                tablero[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                tablero[i][j].setFont(new Font("Arial", 1, 14));

                panel_Tablero.add(tablero[i][j]);
           }
        }
        panel_Tablero.updateUI();
    }
    
        //Se crea el tablero a manejar, el conjunto de labels
    public void crearTableroAnimacion(Estado[][] _matriz) {
        //matriz.imprimirMatriz();
        panel_Tablero.removeAll();
        int[][] matri = null;
        panel_Tablero.setLayout(new GridLayout(obtenerFilas(), obtenerColumnas()));
        for (int i = 0; i < obtenerFilas(); i++) {
            for (int j = 0; j < obtenerColumnas(); j++) {
                tablero[i][j] = new PintarfondoCeldas();
                tablero[i][j].setImage(fondodetablero);
                tablero[i][j].setIcon(retornarImagenDeCasillas(_matriz[i][j].getIdObjetos()));
                tablero[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                tablero[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                tablero[i][j].setFont(new Font("Arial", 1, 14));

                panel_Tablero.add(tablero[i][j]);
           }
        }
        panel_Tablero.updateUI();
    }
      
    //Le asigna a cada posicion la imagen que le corresponda.
    private Icon retornarImagenDeCasillas(int posicion) {
//        if (posicion > 0) {
//            return arregloDeImagenes[8];
//        } else {
            switch (posicion) {
                case ID_VACIA:
                    return arregloDeImagenes[0];
                case ID_MURO:
                    return arregloDeImagenes[1];
                case ID_ROBOT:
                    return arregloDeImagenes[2];
                case ID_TRAJE:
                    return arregloDeImagenes[3];
                case ID_CAMPO1:
                    return arregloDeImagenes[4];
                case ID_CAMPO2:
                    return arregloDeImagenes[5];
                case ID_ITEM:
                    return arregloDeImagenes[6];
                case ID_MARCA:
                    return arregloDeImagenes[7];
                default:
                    return null;
//            }
       }
}
    
   
    public void establecerFilas(int filas) {
        this._filas = filas;
    }

    public void establecerColumnas(int columnas) {
        this._columnas = columnas;
    }

    public int obtenerFilas() {
        return _filas;
    }

    public int obtenerColumnas() {
        return _columnas;
    }
    
    void printmatrix(Estado[][] matrix) {
        System.out.println("Pintando Matriz");
        for (int i = 0; i < matrix.length; i++) {
            //Filas
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(" " + matrix[j][i].getIdObjetos()+ " |");
            }
            System.out.println("-");
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cargarArchivo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bAmplitud = new javax.swing.JButton();
        bProfundidad = new javax.swing.JButton();
        bCostoUniforme = new javax.swing.JButton();
        bAEstrella = new javax.swing.JButton();
        bAvara = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        bAyuda = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bInformacion = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panel_Tablero = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Menù", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));

        cargarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar archivo.png"))); // NOI18N
        cargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarArchivoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Busquedas"));

        jLabel1.setText("No Informada");

        jLabel2.setText("Informada");

        bAmplitud.setText("Amplitud");
        bAmplitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAmplitudActionPerformed(evt);
            }
        });

        bProfundidad.setText("Profundidad");
        bProfundidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProfundidadActionPerformed(evt);
            }
        });

        bCostoUniforme.setText("Costo Uniforme");
        bCostoUniforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCostoUniformeActionPerformed(evt);
            }
        });

        bAEstrella.setText("A*");
        bAEstrella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAEstrellaActionPerformed(evt);
            }
        });

        bAvara.setText("Avara");
        bAvara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAvaraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bProfundidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bAmplitud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bCostoUniforme, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bAvara, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bAEstrella, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAmplitud, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAEstrella, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bProfundidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAvara, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bCostoUniforme, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.MatteBorder(null));

        bAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ayuda.png"))); // NOI18N
        bAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAyudaActionPerformed(evt);
            }
        });

        jLabel3.setText("Ayuda");

        jLabel4.setText("Informacion");

        bInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/informacion.png"))); // NOI18N
        bInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInformacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(20, 20, 20))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(bAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bInformacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel5.setText("Cargar Archivo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(cargarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cargarArchivo)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Tablero", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 500));

        panel_Tablero.setBackground(new java.awt.Color(255, 255, 255));
        panel_Tablero.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout panel_TableroLayout = new javax.swing.GroupLayout(panel_Tablero);
        panel_Tablero.setLayout(panel_TableroLayout);
        panel_TableroLayout.setHorizontalGroup(
            panel_TableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );
        panel_TableroLayout.setVerticalGroup(
            panel_TableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_Tablero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_Tablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void cargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarArchivoActionPerformed
        // TODO add your handling code here:
        try {
            //reiniciar();
            this.setVisible(false);
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            boolean salida = false;
            do {
                JTextArea texto = new JTextArea();
                int opcion = selectorArchivo.showOpenDialog(texto);
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    salida = true;
                    ambienteArchivo = selectorArchivo.getSelectedFile();
                    bAmplitud.setEnabled(true);
                    bAEstrella.setEnabled(true);
                    bCostoUniforme.setEnabled(true);
                    bAvara.setEnabled(true);
                    bProfundidad.setEnabled(true);
                }
                if (opcion == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "Debe cargar un archivo");
                }
            } while (!salida);
            archivo = new Archivo(ambienteArchivo);
            //System.out.print(archivo);
            matriz = new Matriz(archivo.leerArchivo());
            matriz.imprimirMatriz();
            estado = new Estado(matriz);
            
            this.objetivos = (ArrayList<Estado>) matriz.getObjetivos().clone();
//            System.out.printf("objetivos  ",this.objetivos);
             this.inicia = matriz.inicio;
//            System.out.printf("inicia  ",this.inicia);
             init = matriz.init;
//            System.out.printf("init ", init);


            //se crea el tablero
            establecerFilas(matriz.getDimension());
            establecerColumnas(matriz.getDimension());
            tablero = new PintarfondoCeldas[obtenerFilas()][obtenerColumnas()];
            crearTablero(matriz);
            //pararHilo = 0;

        } catch (NullPointerException e) {
        }
        this.setVisible(true);
    }//GEN-LAST:event_cargarArchivoActionPerformed

    private void bAmplitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAmplitudActionPerformed
        // TODO add your handling code here:
       
        
        bAEstrella.setEnabled(false);
        bCostoUniforme.setEnabled(false);
        bAvara.setEnabled(false);
        bProfundidad.setEnabled(false);
       
        Estado [][] hp=matriz.getMatrizEstados().clone();
        algoritmoNoInfAmplitud amplitud;
        amplitud = new algoritmoNoInfAmplitud(hp, init);

        ArrayList<Estado> s = new ArrayList<>();
        s = amplitud.BusquedaAmplitud();

        hiloRobot hilo = new hiloRobot(s, this);
        hilo.execute();
        
        bAEstrella.setEnabled(true);
        bCostoUniforme.setEnabled(true);
        bAvara.setEnabled(true);
        bProfundidad.setEnabled(true);

    }//GEN-LAST:event_bAmplitudActionPerformed

    private void bProfundidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProfundidadActionPerformed

      bAEstrella.setEnabled(false);
      bCostoUniforme.setEnabled(false);
      bAvara.setEnabled(false);
      bAmplitud.setEnabled(false);
      
      Estado [][] estadoProfundidad=matriz.getMatrizEstados().clone();
      algoritmoNoInfProfundidad a = new algoritmoNoInfProfundidad(estadoProfundidad, init);
      ArrayList<Estado> estadoProfundd = new ArrayList<>();
      estadoProfundd = a.BusquedaProfundidad();

      hiloRobot hilo = new hiloRobot(estadoProfundd, this);
      hilo.execute();
      
      bAEstrella.setEnabled(true);
      bCostoUniforme.setEnabled(true);
      bAvara.setEnabled(true);
      bAmplitud.setEnabled(true);
        
    }//GEN-LAST:event_bProfundidadActionPerformed

    private void bCostoUniformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCostoUniformeActionPerformed
        // TODO add your handling code here:

        bAEstrella.setEnabled(false);
        bProfundidad.setEnabled(false);
        bAvara.setEnabled(false);
        bAmplitud.setEnabled(false);
     
        Estado [][] estadoUniforme=matriz.getMatrizEstados().clone();
        algoritmoNoInfCostoUniforme costo;
        costo = new algoritmoNoInfCostoUniforme(estadoUniforme, init);     
        ArrayList<Estado> s = new ArrayList<>();
        s = costo.bCostoUniforme();
    
        hiloRobot hilo = new hiloRobot(s, this);
        hilo.execute();
        
        bAEstrella.setEnabled(true);
        bProfundidad.setEnabled(true);
        bAvara.setEnabled(true);
        bAmplitud.setEnabled(true);
    }//GEN-LAST:event_bCostoUniformeActionPerformed

    private void bAEstrellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAEstrellaActionPerformed
        // TODO add your handling code here:
         bCostoUniforme.setEnabled(false);
         bProfundidad.setEnabled(false);
         bAvara.setEnabled(false);
         bAmplitud.setEnabled(false);


         Estado [][] estadoAEstrella=matriz.getMatrizEstados().clone();
         algoritmoInfAEstrella a = new algoritmoInfAEstrella(estadoAEstrella, init, objetivos);
         ArrayList<Estado> estadoaE = new ArrayList<>();
         estadoaE = a.busquedaSolucionConAEstrella();
         
         hiloRobot hilo = new hiloRobot(estadoaE, this);
         hilo.execute();
         
         bCostoUniforme.setEnabled(true);
         bProfundidad.setEnabled(true);
         bAvara.setEnabled(true);
         bAmplitud.setEnabled(true);
    }//GEN-LAST:event_bAEstrellaActionPerformed

    private void bAvaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAvaraActionPerformed
        // TODO add your handling code here:

      bCostoUniforme.setEnabled(false);
      bProfundidad.setEnabled(false);
      bAEstrella.setEnabled(false);
      bAmplitud.setEnabled(false);   
        
      Estado [][] estadoAvara=matriz.getMatrizEstados().clone();
      algoritmoInfAvara a = new algoritmoInfAvara(estadoAvara, init, objetivos);
      ArrayList<Estado> estadoA = new ArrayList<>();
      estadoA = a.BusquedaAvara();
    
      hiloRobot hilo = new hiloRobot(estadoA, this);
      hilo.execute();

        bCostoUniforme.setEnabled(true);
        bProfundidad.setEnabled(true);
        bAEstrella.setEnabled(true);
        bAmplitud.setEnabled(true);

    }//GEN-LAST:event_bAvaraActionPerformed

    private void bInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInformacionActionPerformed
        // TODO add your handling code here:
        new Informacion();
    }//GEN-LAST:event_bInformacionActionPerformed

    private void bAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAyudaActionPerformed
        // TODO add your handling code here:
        new Ayuda();
    }//GEN-LAST:event_bAyudaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    public class hiloRobot extends SwingWorker< Integer, Integer> {
        
        ArrayList<Estado> entrada = new ArrayList<>();
        Main grafico;
        
        public hiloRobot(ArrayList<Estado> entrada, Main gui) {
        this.entrada = entrada;
        this.grafico = gui;
        }
        
        @Override
        protected Integer doInBackground() throws Exception {
                                
            Estado [][] mapa = new Estado[matriz.getDimension()][matriz.getDimension()];
            mapa = matriz.construirMatriz();
            for (int i = 0; i < entrada.size(); i++) {
                try {
                    mapa[entrada.get(i).getX()][entrada.get(i).getY()].setIdObjetos(2);
                    grafico.crearTableroAnimacion(mapa);
                    Thread.sleep(100);
                    grafico.printmatrix(mapa);
                    grafico.crearTableroAnimacion(mapa);
                    Thread.sleep(100);
                    mapa[entrada.get(i).getX()][entrada.get(i).getY()].setIdObjetos(-1);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error en Tiempo de espera del Hilo de Ejecucion");
                }
            }
            return 0;
           
        }
    
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAEstrella;
    private javax.swing.JButton bAmplitud;
    private javax.swing.JButton bAvara;
    private javax.swing.JButton bAyuda;
    private javax.swing.JButton bCostoUniforme;
    private javax.swing.JButton bInformacion;
    private javax.swing.JButton bProfundidad;
    private javax.swing.JButton cargarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panel_Tablero;
    // End of variables declaration//GEN-END:variables

}
