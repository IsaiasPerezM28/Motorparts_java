package beans;

import dao.ProveedorDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Proveedor;

@ManagedBean
@SessionScoped
public class ProveedorBean {
    Proveedor proveedor = new Proveedor();
    List<Proveedor> listaProv = new ArrayList<>();
    ProveedorDAO provDAO = new ProveedorDAO();
    
    private String filtroNit = "";
    private String filtroNombre = "";
    private String filtroTelefono = "";

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Proveedor> getListaProv() {
        return listaProv;
    }

    public void setListaProv(List<Proveedor> listaProv) {
        this.listaProv = listaProv;
    }
    
    public void listar(){
        proveedor = new Proveedor();
        listaProv = provDAO.listar();
    }
    
    public void inicializar(){
        if(listaProv == null || listaProv.isEmpty()){
            listar();
        }
        if(proveedor == null){
            proveedor = new Proveedor();
        }
    }
    
    public void guardar(){
        provDAO.guardar(proveedor);
        proveedor = new Proveedor();
        listar();
    }
    
    public void buscar(int id){
        proveedor = provDAO.buscar(id);
    }
    
    public String buscarYEditar(int id){
        proveedor = provDAO.buscar(id);
        if(proveedor != null){
            return "editar";
        }
        return null;
    }
    
    public void actualizar(){
        provDAO.actualizar(proveedor);
        proveedor = new Proveedor();
        listar();
    }
    
    public void eliminar(int id){
        provDAO.eliminar(id);
        listar();
    }
    
    public String filtrar(){
        if(filtroNit == null) filtroNit = "";
        if(filtroNombre == null) filtroNombre = "";
        if(filtroTelefono == null) filtroTelefono = "";
        
        String nitFiltro = filtroNit.trim();
        String nombreFiltro = filtroNombre.trim();
        String telefonoFiltro = filtroTelefono.trim();
        
        if(nitFiltro.isEmpty()) nitFiltro = null;
        if(nombreFiltro.isEmpty()) nombreFiltro = null;
        if(telefonoFiltro.isEmpty()) telefonoFiltro = null;
        
        listaProv = provDAO.filtrar(nitFiltro, nombreFiltro, telefonoFiltro);
        return null;
    }
    
    public String limpiarFiltros(){
        filtroNit = "";
        filtroNombre = "";
        filtroTelefono = "";
        listar();
        return null;
    }

    public String getFiltroNit() {
        return filtroNit;
    }

    public void setFiltroNit(String filtroNit) {
        this.filtroNit = filtroNit;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public String getFiltroTelefono() {
        return filtroTelefono;
    }

    public void setFiltroTelefono(String filtroTelefono) {
        this.filtroTelefono = filtroTelefono;
    }
}


