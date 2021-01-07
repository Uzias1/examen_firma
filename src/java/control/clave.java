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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;


@WebServlet(name = "clave", urlPatterns = {"/clave"})
public class clave extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para llamar a hacer archivo necesitas varias cosas:
        /*
        *obtener el .key
        *enviarle los parametros nombre, edad, mensaje y firma todos son Strings
        */
        
        String n="", e="", m="", f="";
        
        /*
        //suponer que esto es el doc a firmar
        byte[] dato = "Hola como estan xd".getBytes();
        
        //tenemos que usar signatura que se
        //encarga de realizar la firma
        Signature firma = Signature.getInstance("MD5WithRSA");
        //inicializar
        firma.initSign(llaves.getPrivate());
        
        firma.update(dato);
        
        //firmado
        byte[] firmabytes = firma.sign();
        
        //visualisar
        System.out.println("Firma: "+ new BASE64Encoder().encode(firmabytes));
        */
        String semiruta = "C://firmas/";
        String llave = request.getParameter("archivo");
        //System.out.println(llave);
        
        try {
            PrivateKey llaveprivada = cargarPrivadaKey(semiruta+llave);
            Signature firma = Signature.getInstance("MD5WithRSA");
            firma.initSign(llaveprivada);
            
            //creo el archivo
            n = request.getParameter("nombree");
            e = request.getParameter("edadd");
            m = request.getParameter("mensajee");
            hacerArchivosin(n, e, m);
            
            //esto ya es para el archivo
            Path pdfPath = Paths.get("C://firmas/datos.pdf");
            byte[] pdf = Files.readAllBytes(pdfPath);
            
            firma.update(pdf);
            
            byte[] firmabytes = firma.sign();
        
            //visualisar
            //System.out.println("Firma: "+ new BASE64Encoder().encode(firmabytes));
            String firmaa = new BASE64Encoder().encode(firmabytes);
            
            
            f = firmaa;
            
            hacerArchivo(n, e, m, f);
            
            FileOutputStream fi = new FileOutputStream("C://firmas/fir");
            fi.write(firmabytes);
            fi.close();
            
            response.sendRedirect("index.jsp");
            
        } catch(Exception ee){
            System.out.println("Problemas para leer la llave master o algo con las llaves");
        }
    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void hacerArchivo(String name, String old, String msj, String firma) throws FileNotFoundException{
        //generar el archivo
        FileOutputStream archivo = new FileOutputStream("C:/firmas/firma.pdf");
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, archivo);
            documento.open();
            documento.add(new Paragraph("Nombre: "+name));
            documento.add(new Paragraph("Edad: "+old));
            documento.add(new Paragraph("Mensaje: "+msj));
            documento.add(new Paragraph("Firma: "+firma));
            
            documento.close();
        } catch (DocumentException ex) {
            //Logger.getLogger(Obtenerdatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en el pdf");
        }
    }
    
    private void hacerArchivosin(String name, String old, String msj) throws FileNotFoundException{
        //generar el archivo
        FileOutputStream archivo = new FileOutputStream("C:/firmas/datos.pdf");
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, archivo);
            documento.open();
            documento.add(new Paragraph("Nombre: "+name));
            documento.add(new Paragraph("Edad: "+old));
            documento.add(new Paragraph("Mensaje: "+msj));
            
            documento.close();
        } catch (DocumentException ex) {
            //Logger.getLogger(Obtenerdatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en el pdf");
        }
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
