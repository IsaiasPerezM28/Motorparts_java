package beans;

import dao.ConnBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {
    private Usuario usuario = new Usuario();
    String nombreUsuario = "";

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getUsuario() {
        if(usuario == null){
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void autenticar(){        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            if(usuario == null){
                usuario = new Usuario();
            }
            
            if(usuario.getDoc() == 0 || usuario.getPass() == null || usuario.getPass().trim().isEmpty()){
                redirectToError();
                return;
            }
            
            con = ConnBD.conectar();
            
            if(con == null){
                redirectToError();
                return;
            }
            
            String pwd = Utils.encriptar(usuario.getPass());
            
            if(pwd == null || pwd.isEmpty()){
                redirectToError();
                return;
            }
            
            String sql = "SELECT * FROM usuario WHERE doc = ? AND pass = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getDoc());
            ps.setString(2, pwd);
            
            rs = ps.executeQuery();
                       
            if(rs != null && rs.next()){
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                
                if(nombre != null && tipo != null){
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", nombre);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipo", tipo);
                    
                    String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    String redirectUrl = "";
                    
                    switch(tipo){
                        case "A":
                            redirectUrl = rootPath + "/faces/admin/index.xhtml";
                            break;
                        case "C":
                            redirectUrl = rootPath + "/faces/cliente.xhtml";
                            break;
                        case "V":
                            redirectUrl = rootPath + "/faces/vende.xhtml";
                            break;
                        default:
                            redirectUrl = rootPath + "/faces/error.xhtml";
                            break;
                    }
                    
                    if(!redirectUrl.isEmpty()){
                        FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
                        FacesContext.getCurrentInstance().responseComplete();
                    }
                } else {
                    redirectToError();
                }
            }else{
                redirectToError();
            }
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            redirectToError();
        } catch (Exception e) {
            e.printStackTrace();
            redirectToError();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void redirectToError(){
        try {
            String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/error.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void cerrar_sesion(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        try {
            String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
        }
    }
    
    public void verif_sesion(String t){
        String nom = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String tipo = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipo");
        
        if(nom == null){
            try {
                String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/sinacceso.xhtml");
            } catch (IOException e) {
            }
        }else{
            if(!tipo.equals(t)){
                try {
                    String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/sinacceso.xhtml");
                } catch (IOException e) {
                }
            }else{
                nombreUsuario = nom;
            }
        }
    }   
    
}
