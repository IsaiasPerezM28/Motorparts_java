package beans;

import dao.ProductoDAO;
import dao.ProveedorDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import modelo.Producto;
import modelo.Proveedor;

@ManagedBean
@ApplicationScoped
public class ProductoBean {
    Producto producto = new Producto();
    List<Producto> lstProd = new ArrayList<>();
    List<Proveedor> lstProv = new ArrayList<>();
    ProductoDAO prodDAO = new ProductoDAO();
    Part imagen;
    
    private String filtroCodigo = "";
    private String filtroNombre = "";
    private Float filtroPrecioMin;
    private Float filtroPrecioMax;
    private Integer filtroExistMin;
    private Integer filtroIdProv;

    public void listar(){
        lstProd = prodDAO.listar();
    }
    
    public void listarProv(){
        ProveedorDAO provDAO = new ProveedorDAO();
        lstProv = provDAO.listar();
    }
    
    public void inicializar(){
        if(lstProd == null || lstProd.isEmpty()){
            listar();
        }
        if(lstProv == null || lstProv.isEmpty()){
            listarProv();
        }
        if(producto == null){
            producto = new Producto();
        }
    }
    
    public void guardar(){
        if(imagen != null && imagen.getSize() > 0){
            String nombreArchivo = imagen.getSubmittedFileName();
            if(nombreArchivo != null && !nombreArchivo.isEmpty()){
                producto.setFoto("images/" + nombreArchivo);
            }
        }
        prodDAO.guardar(producto);
        producto = new Producto();
        imagen = null;
        listar();
    }
    
    public void buscar(int id){
        producto = prodDAO.buscar(id);
    }
    
    public void actualizar(){
        if(imagen != null && imagen.getSize() > 0){
            String nombreArchivo = imagen.getSubmittedFileName();
            if(nombreArchivo != null && !nombreArchivo.isEmpty()){
                producto.setFoto("images/" + nombreArchivo);
            }
        }
        prodDAO.actualizar(producto);
        producto = new Producto();
        imagen = null;
        listar();
    }
    
    public void eliminar(int id){
        prodDAO.eliminar(id);
        listar();
    }
    
    public String filtrar(){
        if(filtroCodigo == null) filtroCodigo = "";
        if(filtroNombre == null) filtroNombre = "";
        
        lstProd = prodDAO.filtrar(
            filtroCodigo.trim().isEmpty() ? null : filtroCodigo.trim(),
            filtroNombre.trim().isEmpty() ? null : filtroNombre.trim(),
            filtroPrecioMin,
            filtroPrecioMax,
            filtroExistMin,
            filtroIdProv
        );
        return null;
    }
    
    public String limpiarFiltros(){
        filtroCodigo = "";
        filtroNombre = "";
        filtroPrecioMin = null;
        filtroPrecioMax = null;
        filtroExistMin = null;
        filtroIdProv = null;
        listar();
        return null;
    }
    
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getLstProd() {
        return lstProd;
    }

    public void setLstProd(List<Producto> lstProd) {
        this.lstProd = lstProd;
    }

    public Part getImagen() {
        return imagen;
    }

    public void setImagen(Part imagen) {
        this.imagen = imagen;
    }

    public List<Proveedor> getLstProv() {
        return lstProv;
    }

    public void setLstProv(List<Proveedor> lstProv) {
        this.lstProv = lstProv;
    }

    public String getFiltroCodigo() {
        return filtroCodigo;
    }

    public void setFiltroCodigo(String filtroCodigo) {
        this.filtroCodigo = filtroCodigo;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public Float getFiltroPrecioMin() {
        return filtroPrecioMin;
    }

    public void setFiltroPrecioMin(Float filtroPrecioMin) {
        this.filtroPrecioMin = filtroPrecioMin;
    }

    public Float getFiltroPrecioMax() {
        return filtroPrecioMax;
    }

    public void setFiltroPrecioMax(Float filtroPrecioMax) {
        this.filtroPrecioMax = filtroPrecioMax;
    }

    public Integer getFiltroExistMin() {
        return filtroExistMin;
    }

    public void setFiltroExistMin(Integer filtroExistMin) {
        this.filtroExistMin = filtroExistMin;
    }

    public Integer getFiltroIdProv() {
        return filtroIdProv;
    }

    public void setFiltroIdProv(Integer filtroIdProv) {
        this.filtroIdProv = filtroIdProv;
    }
}
