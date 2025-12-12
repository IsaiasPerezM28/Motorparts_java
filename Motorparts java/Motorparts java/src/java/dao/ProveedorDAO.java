package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;

public class ProveedorDAO {
     private Connection con = ConnBD.conectar();
     private PreparedStatement ps;
     private ResultSet rs;
     
     public List<Proveedor> listar(){
         List<Proveedor> listaProv = new ArrayList<>();
         
         try {
             String sql = "SELECT * FROM proveedor ORDER BY nombre";
             ps = con.prepareStatement(sql);
             
             rs = ps.executeQuery();
             
             while(rs.next()){
                 Proveedor prov = new Proveedor();
                 prov.setId(rs.getInt("id"));
                 prov.setNit(rs.getString("nit"));
                 prov.setNombre(rs.getString("nombre"));
                 prov.setTel(rs.getString("tel"));
                 
                 listaProv.add(prov);
            }
             
         } catch (SQLException e) {
             System.err.println("Error al listar proveedores: " + e.getMessage());
             e.printStackTrace();
         } finally {
             try {
                 if(rs != null) rs.close();
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
         }
         
         return listaProv;
     }
     
     public void guardar(Proveedor prov){
         try {
             String sql = "INSERT INTO proveedor VALUES(null, ?, ?, ?)";
             ps = con.prepareStatement(sql);
             ps.setString(1, prov.getNit());
             ps.setString(2, prov.getNombre());
             ps.setString(3, prov.getTel());
             
             ps.executeUpdate();
             
         } catch (SQLException e) {
             System.err.println("Error al guardar proveedor: " + e.getMessage());
             e.printStackTrace();
             throw new RuntimeException("Error al guardar proveedor", e);
         } finally {
             try {
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
         }
     }
     
     public Proveedor buscar(int id){
         Proveedor prov = null;
         
         try {
             String sql = "SELECT * FROM proveedor WHERE id = ?";
             ps = con.prepareStatement(sql);
             ps.setInt(1, id);
             
             rs = ps.executeQuery();
             
             if(rs.next()){
                 prov = new Proveedor();
                 prov.setId(rs.getInt("id"));
                 prov.setNit(rs.getString("nit"));
                 prov.setNombre(rs.getString("nombre"));
                 prov.setTel(rs.getString("tel"));
             }
         } catch (SQLException e) {
             System.err.println("Error al buscar proveedor: " + e.getMessage());
             e.printStackTrace();
         } finally {
             try {
                 if(rs != null) rs.close();
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
         }
         
         return prov;
     }
     
     public void actualizar(Proveedor prov){
         try {
             String sql = "UPDATE proveedor SET nit = ?, nombre = ?, tel = ? WHERE id = ?";
             ps = con.prepareStatement(sql);
             ps.setString(1, prov.getNit());
             ps.setString(2, prov.getNombre());
             ps.setString(3, prov.getTel());
             ps.setInt(4, prov.getId());
             
             ps.executeUpdate();
             
         } catch (SQLException e) {
             System.err.println("Error al actualizar proveedor: " + e.getMessage());
             e.printStackTrace();
             throw new RuntimeException("Error al actualizar proveedor", e);
         } finally {
             try {
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
         }
     }
     
     public void eliminar(int id){
         try {
             String sql = "DELETE FROM proveedor WHERE id = ?";
             ps = con.prepareStatement(sql);
             ps.setInt(1, id);
             
             ps.executeUpdate();
             
         } catch (SQLException e) {
             System.err.println("Error al eliminar proveedor: " + e.getMessage());
             e.printStackTrace();
             throw new RuntimeException("Error al eliminar proveedor", e);
         } finally {
             try {
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
        }
    }
    
    public List<Proveedor> filtrar(String nit, String nombre, String telefono){
         List<Proveedor> listaProv = new ArrayList<>();
         
         try {
             StringBuilder sql = new StringBuilder("SELECT * FROM proveedor WHERE 1=1");
             
             if(nit != null && !nit.trim().isEmpty()){
                 sql.append(" AND nit LIKE ?");
             }
             if(nombre != null && !nombre.trim().isEmpty()){
                 sql.append(" AND nombre LIKE ?");
             }
             if(telefono != null && !telefono.trim().isEmpty()){
                 sql.append(" AND tel LIKE ?");
             }
             
             sql.append(" ORDER BY nombre");
             
             ps = con.prepareStatement(sql.toString());
             int paramIndex = 1;
             
             if(nit != null && !nit.trim().isEmpty()){
                 ps.setString(paramIndex++, "%" + nit.trim() + "%");
             }
             if(nombre != null && !nombre.trim().isEmpty()){
                 ps.setString(paramIndex++, "%" + nombre.trim() + "%");
             }
             if(telefono != null && !telefono.trim().isEmpty()){
                 ps.setString(paramIndex++, "%" + telefono.trim() + "%");
             }
             
             rs = ps.executeQuery();
             
             while(rs.next()){
                 Proveedor prov = new Proveedor();
                 prov.setId(rs.getInt("id"));
                 prov.setNit(rs.getString("nit"));
                 prov.setNombre(rs.getString("nombre"));
                 prov.setTel(rs.getString("tel"));
                 
                 listaProv.add(prov);
             }
             
         } catch (SQLException e) {
             System.err.println("Error al filtrar proveedores: " + e.getMessage());
             e.printStackTrace();
         } finally {
             try {
                 if(rs != null) rs.close();
                 if(ps != null) ps.close();
             } catch (SQLException e) {
                 System.err.println("Error al cerrar recursos: " + e.getMessage());
             }
         }
         
         return listaProv;
     }
}
