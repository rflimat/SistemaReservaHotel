/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.vhabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raul
 */
public class fhabitacion {
    private conexion mysql;
    private Connection cn;
    private String sSQL="";
    public Integer totalregistros;

    public fhabitacion() throws ClassNotFoundException {
        mysql=new conexion();
        cn=mysql.conectar();
    }
    
    public DefaultTableModel mostrar(String buscar){
        DefaultTableModel modelo;
        
        String [] titulos ={"ID","Número","Descripción","Caracteristicas","Precio","Estado","Tipo habitación"};
        
        String [] registro=new String[8];
        
        totalregistros=0;
        modelo=new DefaultTableModel(null,titulos);
        
        sSQL="select * from habitacion where piso like '&"+ buscar + "% order by idhabitacion";
        
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sSQL);
            
            while(rs.next()){
                registro[0]=rs.getString("idhabitacion");
                registro[1]=rs.getString("numero");
                registro[2]=rs.getString("piso");
                registro[3]=rs.getString("descripcion");
                registro[4]=rs.getString("caracteristicas");
                registro[5]=rs.getString("precio_diario");
                registro[6]=rs.getString("estado");
                registro[7]=rs.getString("tipo_habitacion");
                
                totalregistros=totalregistros+1;
                modelo.addRow(registro);
            }
            return modelo;
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    public boolean insertar(vhabitacion dts){
        sSQL="insert into habitacion (idhabitacion,numero,piso,descripcion,caracteristicas,precio_diario,estado,tipo_habitacion)"+
                "values (?,?,?,?,?,?,?,?)";
        
        try{
            PreparedStatement pst=cn.prepareStatement(sSQL);
            System.out.println(dts.getIdhabitacion());
            pst.setInt(1, dts.getIdhabitacion());
            pst.setString(2, dts.getNumero());
            pst.setString(3, dts.getPiso());
            pst.setString(4, dts.getDescripcion());
            pst.setString(5, dts.getCaracteristicas());
            pst.setDouble(6, dts.getPrecio_diario());
            pst.setString(7, dts.getEstado());
            pst.setString(8, dts.getTipo_habitacion());
            
            int n=pst.executeUpdate();
            
            if(n!=0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println("xd");
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
    public boolean editar(vhabitacion dts){
        sSQL="update habitacion set numero=?,piso=?,descripcion=?,caracteristicas=?,precio_diario=?,estado=?,tipo_habitacion=?"+
                "where idhabitacion=?";
        
        try{
            PreparedStatement pst=cn.prepareCall(sSQL);
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            pst.setInt(8, dts.getIdhabitacion());
            
            int n=pst.executeUpdate();
            
            if(n!=0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }   
    }
    
    public boolean eliminar(vhabitacion dts){
        sSQL="delete from habitacion where idhabitacion=?";
        
        try{
            PreparedStatement pst=cn.prepareCall(sSQL);
            
            pst.setInt(1, dts.getIdhabitacion());
            
            int n=pst.executeUpdate();
            
            if(n!=0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
}
