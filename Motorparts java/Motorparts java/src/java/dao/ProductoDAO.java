package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class ProductoDAO {
    private final Connection con = ConnBD.conectar();
    private PreparedStatement ps;
    private ResultSet rs;
    private final ProveedorDAO provDAO = new ProveedorDAO();   
    
    public List<Producto> listar(){
        List<Producto> listaProd = null;
        
        try {
            String sql = "SELECT * FROM producto";
            ps = con.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            listaProd = new ArrayList<>();
            
            while(rs.next()){
                Producto prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setCod(rs.getString("cod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescr(rs.getString("descr"));
                prod.setPrecio(rs.getFloat("precio"));
                prod.setExist(rs.getInt("exist"));
                prod.setFven(rs.getDate("fven"));
                prod.setFoto(rs.getString("foto"));
                prod.setId_prov(rs.getInt("id_prov"));
                prod.setProv(provDAO.buscar(rs.getInt("id_prov")));
                
                listaProd.add(prod);
            }
            
        } catch (SQLException e) {
        }
        
        return listaProd;
    }
    
    public void guardar(Producto prod){
        try {
            String sql = "INSERT INTO producto VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getCod());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getDescr());
            ps.setFloat(4, prod.getPrecio());
            ps.setInt(5, prod.getExist());
            if(prod.getFven() != null){
                ps.setDate(6, new java.sql.Date(prod.getFven().getTime()));
            } else {
                ps.setDate(6, null);
            }
            ps.setString(7, prod.getFoto() != null ? prod.getFoto() : "");
            ps.setInt(8, prod.getId_prov());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public Producto buscar(int id){
        Producto prod = null;
        
        try {
            String sql = "SELECT * FROM producto WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setCod(rs.getString("cod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescr(rs.getString("descr"));
                prod.setPrecio(rs.getFloat("precio"));
                prod.setExist(rs.getInt("exist"));
                prod.setFven(rs.getDate("fven"));
                prod.setFoto(rs.getString("foto"));
                prod.setId_prov(rs.getInt("id_prov"));
                prod.setProv(provDAO.buscar(rs.getInt("id_prov")));
            }
            
        } catch (SQLException e) {
        }
        
        return prod;
    }
    
    public void actualizar(Producto prod){
        try {
            String sql = "UPDATE producto SET cod = ?, nombre = ?, descr = ?, precio = ?, exist = ?, fven = ?, foto = ?, id_prov = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getCod());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getDescr());
            ps.setFloat(4, prod.getPrecio());
            ps.setInt(5, prod.getExist());
            if(prod.getFven() != null){
                ps.setDate(6, new java.sql.Date(prod.getFven().getTime()));
            } else {
                ps.setDate(6, null);
            }
            ps.setString(7, prod.getFoto() != null ? prod.getFoto() : "");
            ps.setInt(8, prod.getId_prov());
            ps.setInt(9, prod.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public void eliminar(int id){
        try {
            String sql = "DELETE FROM producto WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public List<Producto> filtrar(String codigo, String nombre, Float precioMin, Float precioMax, Integer existMin, Integer idProv){
        List<Producto> listaProd = new ArrayList<>();
        
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM producto WHERE 1=1");
            
            if(codigo != null && !codigo.trim().isEmpty()){
                sql.append(" AND cod LIKE ?");
            }
            if(nombre != null && !nombre.trim().isEmpty()){
                sql.append(" AND nombre LIKE ?");
            }
            if(precioMin != null){
                sql.append(" AND precio >= ?");
            }
            if(precioMax != null){
                sql.append(" AND precio <= ?");
            }
            if(existMin != null){
                sql.append(" AND exist >= ?");
            }
            if(idProv != null && idProv > 0){
                sql.append(" AND id_prov = ?");
            }
            
            ps = con.prepareStatement(sql.toString());
            int paramIndex = 1;
            
            if(codigo != null && !codigo.trim().isEmpty()){
                ps.setString(paramIndex++, "%" + codigo + "%");
            }
            if(nombre != null && !nombre.trim().isEmpty()){
                ps.setString(paramIndex++, "%" + nombre + "%");
            }
            if(precioMin != null){
                ps.setFloat(paramIndex++, precioMin);
            }
            if(precioMax != null){
                ps.setFloat(paramIndex++, precioMax);
            }
            if(existMin != null){
                ps.setInt(paramIndex++, existMin);
            }
            if(idProv != null && idProv > 0){
                ps.setInt(paramIndex++, idProv);
            }
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                Producto prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setCod(rs.getString("cod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescr(rs.getString("descr"));
                prod.setPrecio(rs.getFloat("precio"));
                prod.setExist(rs.getInt("exist"));
                prod.setFven(rs.getDate("fven"));
                prod.setFoto(rs.getString("foto"));
                prod.setId_prov(rs.getInt("id_prov"));
                prod.setProv(provDAO.buscar(rs.getInt("id_prov")));
                
                listaProd.add(prod);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al filtrar productos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return listaProd;
    }
    
}
