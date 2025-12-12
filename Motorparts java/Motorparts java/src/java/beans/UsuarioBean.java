package beans;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    List<Usuario> listaU = new ArrayList<>();
    UsuarioDAO uDAO = new UsuarioDAO();
    
    private String filtroNombre = "";
    private String filtroCorreo = "";
    private String filtroTipo = "T";

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaU() {        
        return listaU;
    }

    public void setListaU(List<Usuario> listaU) {
        this.listaU = listaU;
    }
    
    public void listar(){
        usuario = new Usuario();
        listaU = uDAO.listarU();
    }
    
    public void inicializar(){
        if(listaU == null || listaU.isEmpty()){
            listar();
        }
        if(usuario == null){
            usuario = new Usuario();
        }
    }
    
    public void guardar(){
        usuario.setPass(Utils.encriptar(usuario.getPass()));
        uDAO.guardar(usuario);
        usuario = new Usuario();
        listar();
    }
    
    public void buscar(int id){
        usuario = uDAO.buscar(id);
    }
    
    public String buscarYEditar(int id){
        usuario = uDAO.buscar(id);
        if(usuario != null){
            return "editar";
        }
        return null;
    }
    
    public void cargarUsuarioParaEditar(){
    }
    
    public void actualizar(){
        if(usuario.getPass() == null || usuario.getPass().trim().isEmpty()){
            usuario.setPass(usuario.getPass1());
        }else{
            usuario.setPass(Utils.encriptar(usuario.getPass()));
        }        
        uDAO.actualizar(usuario);
        usuario = new Usuario();
        listar();
    }
    
    public void eliminar(int id){
        uDAO.eliminar(id);
        listar();
    }
    
    public String filtrar(){
        if(filtroNombre == null) filtroNombre = "";
        if(filtroCorreo == null) filtroCorreo = "";
        if(filtroTipo == null) filtroTipo = "T";
        
        String nombreFiltro = filtroNombre.trim();
        String correoFiltro = filtroCorreo.trim();
        String tipoFiltro = filtroTipo.trim();
        
        if(nombreFiltro.isEmpty()) nombreFiltro = null;
        if(correoFiltro.isEmpty()) correoFiltro = null;
        if(tipoFiltro.isEmpty() || tipoFiltro.equals("T")) tipoFiltro = null;
        
        listaU = uDAO.filtrar(nombreFiltro, correoFiltro, tipoFiltro);
        return null;
    }
    
    public String limpiarFiltros(){
        filtroNombre = "";
        filtroCorreo = "";
        filtroTipo = "T";
        listar();
        return null;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public String getFiltroCorreo() {
        return filtroCorreo;
    }

    public void setFiltroCorreo(String filtroCorreo) {
        this.filtroCorreo = filtroCorreo;
    }

    public String getFiltroTipo() {
        return filtroTipo;
    }

    public void setFiltroTipo(String filtroTipo) {
        this.filtroTipo = filtroTipo;
    }
}
