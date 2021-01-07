/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//para el pdf
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

//para la firma B) 
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import sun.misc.BASE64Encoder;
@WebServlet(name = "Obtenerdatos", urlPatterns = {"/Obtenerdatos"})
public class Obtenerdatos extends HttpServlet {
/*
    Para poder firmar el documento les solicitara en un formulario la clave privada (aquí pueden hacer uso de los .key que les enseñe a realizar)
Una vez que se introduce se genera el PDF con la información que se introdujo + la visualización de la firma en la parte inferior del PDF.
    */
    String name="", old="", msj="", firma="";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String redirect = "firmar.jsp";
        
        //obtener los datos
        name = request.getParameter("name");
        old = request.getParameter("old");
        msj = request.getParameter("msj");
        
        request.setAttribute("name", name);
        request.setAttribute("edad", old);
        request.setAttribute("mensaje", msj);
        request.getRequestDispatcher("firmar.jsp").forward(request, response);
        
        
       try {
            //se crean las claves
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");

            //inicializar
            generador.initialize(1024);

            //crear llaves
            KeyPair llaves = generador.genKeyPair();
 
            //tenemos que usar signatura que se encarga de realizar la firma
            Signature firma = Signature.getInstance("MD5WithRSA");
            //inicializar
            firma.initSign(llaves.getPrivate());
            
            
            //para guardar los keys
            //ahora necesitamos la llave publica y la privada
            PublicKey llavepublica = llaves.getPublic();
            PrivateKey llaveprivada = llaves.getPrivate();

            //vamos a guardar y cargar un archivo con el contenido de la llave publica
            guardarKey(llavepublica, "C:/firmas/publickey.key");
            llavepublica = cargarPublicaKey("C:/firmas/publickey.key");

            //vamos a guardar y cargar un archivo con el contenido de la llave privada
            guardarKey(llaveprivada, "C:/firmas/privatekey.key");
            llaveprivada = cargarPrivadaKey("C:/firmas/privatekey.key");
            
            
            //tenemos que llegar a otro jsp que muestre el formulario raro
       } catch(Exception e){
           System.out.println("Error en el cifrado");
       }
        
        
        
        
        
        response.sendRedirect(redirect); //+"?variable="+name
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    private static void guardarKey(Key llave, String archivo) throws FileNotFoundException, IOException {
        //generarme un archivo .dat
        byte[] llavepublic = llave.getEncoded();
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavepublic);
        fos.close();
        
    }

    private static PublicKey cargarPublicaKey(String archivo) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        /*
        para poder exportar la llave publica es necesario codificarla mediante una codificacion
        certificada por X509 es para la certificacion de la llave
        */
        
        FileInputStream fis = new FileInputStream(archivo);
        //comprobacion si es valida 
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        //para comprobar la llave
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        //generar la subllaves
        KeySpec spec = new X509EncodedKeySpec(bytes);
        
        PublicKey llavePublic = keyfactory.generatePublic(spec);
        
        return llavePublic;
        
    }

    private static PrivateKey cargarPrivadaKey(String archivo) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        
        FileInputStream fis = new FileInputStream(archivo);
        //comprobacion si es valida 
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        /*porque para la comprobacion de la llave privada, es necesario el 
        certificado por parte del estandar PKCS8 el cual nos dice el tipo 
        de codificacion que acepta una llave privada en RSA
        */
         //para comprobar la llave
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        KeySpec spec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey llavePrivate = keyfactory.generatePrivate(spec);
        return llavePrivate;
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
