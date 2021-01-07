/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Uzías
 */
@WebServlet(name = "Comprobar", urlPatterns = {"/Comprobar"})
public class Comprobar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            PrivateKey llaveprivada = cargarPrivadaKey("C:/firmas/privatekey.key");
            PublicKey llavepublica = cargarPublicaKey("C:/firmas/publickey.key");
            
            
            
            Signature firma = Signature.getInstance("MD5WithRSA");
            firma.initVerify(llavepublica);
            
            String nombreArchivo = request.getParameter("archivo");
            
            Path pdfPatha = Paths.get("C:/firmas/"+nombreArchivo);
            byte[] dato = Files.readAllBytes(pdfPatha);
            
            firma.update(dato);
            
            FileInputStream sig = new FileInputStream("C://firmas/fir");
            byte[] firmabytes = new byte[sig.available()];
            sig.read(firmabytes);
            sig.close();
            
            Boolean valido = firma.verify(firmabytes);
            
            if (valido){
                response.sendRedirect("correcto.jsp");
            } else {
                response.sendRedirect("incorrecto.jsp");
            }
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Comprobar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Comprobar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Comprobar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Comprobar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
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
}
