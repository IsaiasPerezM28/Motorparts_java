package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public UsuarioDAO() {
        con = ConnBD.conectar();
    }
    
    public List<Usuario> listarU(){
        List<Usuario> listUsr = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM usuario";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Usuario u = new Usuario();
                
                u.setId(rs.getInt("id"));
                u.setDoc(rs.getInt("doc"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPass(rs.getString("pass"));
                
                String tipo = "";
                
                switch(rs.getString("tipo")){
                    case "A":
                        tipo = "Administrador";
                        break;
                    case "C":
                        tipo = "Cliente";
                        break;
                    case "V":
                        tipo = "Vendedor";
                        break;
                    default:
                        tipo = rs.getString("tipo");
                        break;
                }
                
                u.setTipo(tipo);
                
                listUsr.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return listUsr;
    }
    
    public void guardar(Usuario usr){
        try {
            String sql = "INSERT INTO usuario VALUES(null, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getDoc());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getCorreo());
            ps.setString(4, usr.getPass());
            ps.setString(5, usr.getTipo());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar usuario", e);
        } finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    public Usuario buscar(int id){
        Usuario usr = null;
        
        try {
            String sql = "SELECT * FROM usuario WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                usr = new Usuario();
                
                usr.setId(rs.getInt("id"));
                usr.setDoc(rs.getInt("doc"));
                usr.setNombre(rs.getString("nombre"));
                usr.setCorreo(rs.getString("correo"));
                usr.setPass(rs.getString("pass"));
                usr.setPass1(rs.getString("pass"));
                usr.setTipo(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usr;
    }
    
    public void actualizar(Usuario usr){
        try {
            String sql = "UPDATE usuario SET doc = ?, nombre = ?, correo = ?, pass = ?, tipo = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getDoc());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getCorreo());
            ps.setString(4, usr.getPass());
            ps.setString(5, usr.getTipo());
            ps.setInt(6, usr.getId());
                        
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar usuario", e);
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
            String sql = "DELETE FROM usuario WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar usuario", e);
        } finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    public List<Usuario> filtrar(String nombre, String correo, String tipo){
        List<Usuario> listUsr = new ArrayList<>();
        
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM usuario WHERE 1=1");
            int paramIndex = 1;
            boolean usarNombre = false;
            boolean usarCorreo = false;
            boolean usarTipo = false;
            String tipoLimpio = null;
            
            if(nombre != null && !nombre.trim().isEmpty()){
                sql.append(" AND nombre LIKE ?");
                usarNombre = true;
            }
            
            if(correo != null && !correo.trim().isEmpty()){
                sql.append(" AND correo LIKE ?");
                usarCorreo = true;
            }
            
            if(tipo != null && !tipo.trim().isEmpty()){
                tipoLimpio = tipo.trim().toUpperCase();
                if(tipoLimpio.equals("A") || tipoLimpio.equals("C") || tipoLimpio.equals("V")){
                    sql.append(" AND tipo = ?");
                    usarTipo = true;
                }
            }
            
            ps = con.prepareStatement(sql.toString());
            
            if(usarNombre){
                ps.setString(paramIndex++, "%" + nombre.trim() + "%");
            }
            if(usarCorreo){
                ps.setString(paramIndex++, "%" + correo.trim() + "%");
            }
            if(usarTipo && tipoLimpio != null){
                ps.setString(paramIndex++, tipoLimpio);
            }
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                Usuario u = new Usuario();
                
                u.setId(rs.getInt("id"));
                u.setDoc(rs.getInt("doc"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setPass(rs.getString("pass"));
                
                String tipoStr = "";
                String tipoBD = rs.getString("tipo");
                
                if(tipoBD != null){
                    switch(tipoBD){
                        case "A":
                            tipoStr = "Administrador";
                            break;
                        case "C":
                            tipoStr = "Cliente";
                            break;
                        case "V":
                            tipoStr = "Vendedor";
                            break;
                        default:
                            tipoStr = tipoBD;
                            break;
                    }
                }
                
                u.setTipo(tipoStr);
                
                listUsr.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar usuarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return listUsr;
    }
}
