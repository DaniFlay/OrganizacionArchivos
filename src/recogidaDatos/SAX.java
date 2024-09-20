package recogidaDatos;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import registros.Registro;
import sujetos.Cliente;
import sujetos.DetallePedido;
import sujetos.Empleado;
import sujetos.Pedido;
import sujetos.Producto;
import sujetos.Proveedor;
import sujetos.Sucursal;

/**
 * Clase para crear todos los parseadores de SAX para cada elemento
 * Todos los metodos funcionan igual, leen un archivo, en el caso de faltar algun atributo 
 * pregutan al usuario si desea completar o descartar dicho objeto, en el caso de descarte
 * se apunta en un archivo txt, sino se le piden los datos que faltan al usuario y se completa
 * se mete en una lista y se devuelve la lista con los objetos encontrados en el archivo
 * @author super
 *
 */
public class SAX {
	/**
	 * SAX para el cliente
	 * @author super
	 *
	 */
    public static class ClienteSAX extends DefaultHandler {
    private List<Cliente> clientes;
    private Cliente cliente;
    private String atributo;
    private File archivo;

    public ClienteSAX(File f) {
        clientes = new ArrayList<>();
        this.archivo= f;
    }
    

    public List<Cliente> getClientes() {
        return clientes;
    }


    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("cliente")) {
            cliente = new Cliente();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (cliente != null) {
            if (atributo.equals("idCliente")) {
                cliente.setIdCliente(Integer.parseInt(value));
            }
            else if (atributo.equals("nombre")) {
                cliente.setNombre(value);
            } else if (atributo.equals("apellidos")) {
                cliente.setApellidos(value);
            }
            else if(atributo.equals("tipoCliente")){
                cliente.setTipoCliente(value);
            }
            else if(atributo.equals("nCompras")){
                cliente.setnCompras(Integer.parseInt(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("cliente")) {
            if (cliente.getNombre() == null || cliente.getApellidos() == null || cliente.getTipoCliente() == null || String.valueOf(cliente.getnCompras()) == null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al cliente con el id "+cliente.getIdCliente()+" le faltan atributos, pulsa 1 para descartar y 2 para completar: ");
                if(sc.nextInt()==2){
                  if (cliente.getNombre()== null) {
                    System.out.print("Introduce el nombre del cliente "+cliente.getIdCliente());
                    cliente.setNombre(sc.nextLine()) ;
                }
                if (cliente.getApellidos() == null) {
                    System.out.print("Introduce los apellidos del cliente "+cliente.getIdCliente());
                    cliente.setApellidos(sc.nextLine());
                }
                if (cliente.getTipoCliente() == null) {
                    System.out.print("Introduce el tipo de cliente del cliente "+cliente.getIdCliente());
                    cliente.setTipoCliente(sc.nextLine());
                }
                if(String.valueOf(cliente.getnCompras())==null){
                    System.out.println("Introduce el número de compras del cliente "+cliente.getIdCliente());
                    cliente.setnCompras(sc.nextInt());
                }  
                clientes.add(cliente);
                cliente= null;
                }
                else{
                    
                    cliente= null;
                }
                Registro.registroDescartes(archivo);
                sc.close();
            }
            
        }
        
    }
}
    /**
     * Creador del SAX para el cliente
     * @param f
     * @return la lista de los clientes
     */
public static List<Cliente> crearParseadorCliente(File f){
    ClienteSAX handler = new ClienteSAX(f);
    try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler.getClientes();
    }
/**
 * SAX para los detalles de los pedidos
 * @author super
 *
 */
    public static class DetalleSAX extends DefaultHandler {
    private List<DetallePedido> detalles;
    private DetallePedido detalle;
    private String atributo;
    private File archivo;

    public DetalleSAX(File f) {
        detalles = new ArrayList<>();
        this.archivo= f;
    }

    
    public List<DetallePedido> getDetalles() {
		return detalles;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("detallesDelPedido")) {
            detalle = new DetallePedido();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (detalle != null) {
            if (atributo.equals("idPedido")) {
                detalle.setIdPedido(Integer.parseInt(value));
            }
            else if (atributo.equals("idProducto")) {
                detalle.setIdProducto(Integer.parseInt(value));
            } 
            else if (atributo.equals("idProveedorProducto")) {
                detalle.setIdProveedorProducto(Integer.parseInt(value));
            }
            else if(atributo.equals("precioUnidad")){
                detalle.setPrecioUnidad(Double.parseDouble(value));
            }
            else if(atributo.equals("cantidad")){
                detalle.setCantidad(Integer.parseInt(value));
            }
            else if(atributo.equals("descuento")){
                detalle.setDescuento(Double.parseDouble(value));
            }
            else if(atributo.equals("precioFinal")){
                detalle.setPrecioFinal(Double.parseDouble(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("detallesDelPedido")) {
            
            if ( String.valueOf(detalle.getPrecioUnidad())== null || String.valueOf(detalle.getCantidad()) == null || String.valueOf(detalle.getDescuento()) == null|| String.valueOf(detalle.getPrecioFinal())==null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al detalle pedido con id del pedido "+detalle.getIdPedido()+" id del producto "+detalle.getIdProducto()+" y el id del proveedor del producto "+detalle.getIdProveedorProducto());
               if(sc.nextInt()==2){
                if (String.valueOf(detalle.getPrecioFinal())== null ) {
                    System.out.print("Introduce el precioFinal del detalle pedido con id del pedido "+detalle.getIdPedido()+" id del producto "+detalle.getIdProducto()+" y el id del proveedor del producto "+detalle.getIdProveedorProducto());
                    detalle.setPrecioFinal(sc.nextDouble()); ;
                }
                if (String.valueOf(detalle.getPrecioUnidad() ) == null) {
                     System.out.print("Introduce el precioUnidad del detalle pedido con id del pedido "+detalle.getIdPedido()+" id del producto "+detalle.getIdProducto()+" y el id del proveedor del producto "+detalle.getIdProveedorProducto());
                    detalle.setPrecioUnidad(sc.nextDouble());
                }
                if (String.valueOf(detalle.getCantidad()) == null) {
                     System.out.print("Introduce la cantidad del detalle pedido con id del pedido "+detalle.getIdPedido()+" id del producto "+detalle.getIdProducto()+" y el id del proveedor del producto "+detalle.getIdProveedorProducto());
                    detalle.setCantidad(sc.nextInt());
                }
                if(String.valueOf(detalle.getDescuento())==null){
                     System.out.print("Introduce el descuento del detalle pedido con id del pedido "+detalle.getIdPedido()+" id del producto "+detalle.getIdProducto()+" y el id del proveedor del producto "+detalle.getIdProveedorProducto());
                    detalle.setDescuento(sc.nextDouble());
                }
                detalles.add(detalle);
                 detalle= null;
               }else{
                Registro.registroDescartes(archivo);
                detalle=null;
               }
                
                sc.close();
            }
            
        }
        
    }
}
    /**
     * Creador del SAX para los detalles de los pedidos
     * @param f
     * @return lista de los detalles de los pedidos
     */
public static List<DetallePedido> crearParseadorDetalles(File f){
 DetalleSAX handler = new DetalleSAX(f);
	try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	return handler.getDetalles();
    }
/**
 * SAX para el empleado
 * @author super
 *
 */
 public static class EmpleadoSAX extends DefaultHandler {
    private List<Empleado> empleados;
    private Empleado empleado;
    private String atributo;
    private File archivo;

    public EmpleadoSAX(File f) {
        empleados = new ArrayList<>();
        this.archivo=f;
    }
    

    public List<Empleado> getEmpleados() {
		return empleados;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("empleado")) {
            empleado = new Empleado();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (empleado != null) {
            if (atributo.equals("idEmpleado")) {
                empleado.setIdEmpleado(Integer.parseInt(value));
            }
            else if (atributo.equals("nombre")) {
                empleado.setNombre(value);
            } else if (atributo.equals("apellidos")) {
                empleado.setApellidos(value);
            }
            else if(atributo.equals("idSucursalEmpleado")){
                empleado.setIdSucursalEmpleado(Integer.parseInt(value));
            }
            else if(atributo.equals("categoria")){
                empleado.setCategoria(Integer.parseInt(value));
            }
            else if(atributo.equals("antiguedad")){
                empleado.setAntiguedad(Integer.parseInt(value));
            }
            else if(atributo.equals("salario")){
                empleado.setSalario(Double.parseDouble(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("empleado")) {
            if (empleado.getNombre() == null || empleado.getApellidos() == null || String.valueOf(empleado.getIdSucursalEmpleado()) == null || String.valueOf(empleado.getCategoria()) == null||String.valueOf(empleado.getAntiguedad())== null|| String.valueOf(empleado.getSalario())==null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al empleado con el id "+empleado.getIdEmpleado()+" le faltan atributos, pulse 1 para descartar y 2 para completar: ");
                if(sc.nextInt()==2){
                if (empleado.getNombre()== null) {
                    System.out.print("Introduce el nombre del empleado "+empleado.getIdEmpleado());
                    empleado.setNombre(sc.nextLine()) ;
                }
                if (empleado.getApellidos() == null) {
                    System.out.print("Introduce los apellidos del empleado "+empleado.getIdEmpleado());
                    empleado.setApellidos(sc.nextLine());
                }
                if (String.valueOf(empleado.getIdSucursalEmpleado())==null) {
                    System.out.print("Introduce el id de la sucursal del empleado "+empleado.getIdEmpleado());
                    empleado.setIdSucursalEmpleado(sc.nextInt());
                }
                if(String.valueOf(empleado.getCategoria())==null){
                    System.out.println("Introduce la categoria del empleado "+empleado.getIdEmpleado());
                    empleado.setCategoria(sc.nextInt());

                }
                if(String.valueOf(empleado.getAntiguedad())==null){
                    System.out.println("Introduce la antiguedad del empleado "+empleado.getIdEmpleado());
                    empleado.setAntiguedad(sc.nextInt());
                }
                if(String.valueOf(empleado.getSalario())==null){
                    System.out.println("Introduce el salario del empleado "+empleado.getIdEmpleado());
                    empleado.setSalario(sc.nextDouble());
                }
                empleados.add(empleado);
                empleado= null;
            }
            else{
                Registro.registroDescartes(archivo);
                empleado= null;
            }
            sc.close();
        }
            
        }
        
    }
}
 /**
  * Creador de SAX para el empleado
  * @param f
  * @return lista con los empleados
  */
public static List<Empleado> crearParseadorEmpleado(File f){
 EmpleadoSAX handler = new EmpleadoSAX(f);
	try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	return handler.getEmpleados();
    }
/**
 * SAX para pedido
 * @author super
 *
 */
 public static class PedidoSAX extends DefaultHandler {
    private List<Pedido> pedidos;
    private Pedido pedido;
    private String atributo;
    private File archivo;

    public PedidoSAX(File f) {
        pedidos = new ArrayList<>();
        this.archivo=f;
    }
    
    
    public List<Pedido> getPedidos() {
		return pedidos;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("pedido")) {
            pedido = new Pedido();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (pedido != null) {
            if (atributo.equals("idPedido")) {
                pedido.setIdPedido(Integer.parseInt(value));
            }
            else if (atributo.equals("idCliente")) {
                pedido.setIdCliente(Integer.parseInt(value));
            } else if (atributo.equals("idEmpleado")) {
                pedido.setIdEmpleado(Integer.parseInt(value));
            }
            else if(atributo.equals("fecha")){
                pedido.setFecha(Date.valueOf(value));
            }
            else if(atributo.equals("precioFinal")){
                pedido.setPrecioFinal(Double.parseDouble(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("pedido")) {
            if (String.valueOf(pedido.getIdCliente())== null || String.valueOf(pedido.getIdEmpleado())== null || String.valueOf(pedido.getFecha()) == null || String.valueOf(pedido.getPrecioFinal()) == null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al pedido con el id "+pedido.getIdPedido()+" le faltan atributos, pulse 1 para descartar y 2 para completar: ");
                if(sc.nextInt()==2){
                    if (String.valueOf(pedido.getIdCliente())== null) {
                    System.out.print("Introduce el id del cliente del pedido "+pedido.getIdPedido());
                    pedido.setIdCliente(sc.nextInt());
                }
                if (String.valueOf(pedido.getIdEmpleado()) == null) {
                    System.out.print("Introduce el id del empleado del pedido "+pedido.getIdPedido());
                    pedido.setIdEmpleado(sc.nextInt());
                }
                if (String.valueOf(pedido.getFecha()) == null) {
                    System.out.print("Introduce la fecha del pedido "+pedido.getIdPedido());
                    pedido.setFecha(Date.valueOf(sc.nextLine()));
                }
                if(String.valueOf(pedido.getPrecioFinal())==null){
                    System.out.println("Introduce el precio final del pedido "+pedido.getIdPedido());

                }
                pedidos.add(pedido);
                pedido= null;
                }
                else{
                    Registro.registroDescartes(archivo);
                    pedido = null;
                }
                
                sc.close();
            }
            
        }
        
    }
}
 /**
  * Creador del SAX para el pedido
  * @param f
  * @return lista de los pedidos
  */
public static List<Pedido> crearParseadorPedido(File f){
	PedidoSAX handler = new PedidoSAX(f);
 try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
 return handler.getPedidos();
    }

/**
 * SAX para producto
 * @author super
 *
 */
 public static class ProductoSAX extends DefaultHandler {
    private List<Producto> productos;
    private Producto producto;
    private String atributo;
    private File archivo;

    public ProductoSAX(File f) {
        productos = new ArrayList<>();
        this.archivo=f;
    }
    

    public List<Producto> getProductos() {
		return productos;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("producto")) {
            producto = new Producto();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (producto != null) {
            if (atributo.equals("idProducto")) {
                producto.setIdProducto(Integer.parseInt(value));
            }
            else if (atributo.equals("tipoProducto")) {
                producto.setTipoProducto(value);
            } else if (atributo.equals("precioC")) {
                producto.setPrecioC(Double.parseDouble(value));
            }
            else if(atributo.equals("idProveedorProducto")){
                producto.setIdProveedorProducto(Integer.parseInt(value));
            }
            else if(atributo.equals("precioV")){
                producto.setPrecioV(Double.parseDouble(value));
            }
            else if(atributo.equals("rotacion")){
                producto.setRotacion(Integer.parseInt(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("producto")) {
            if (String.valueOf(producto.getPrecioC()) == null || producto.getTipoProducto()== null || String.valueOf(producto.getIdProveedorProducto()) == null || String.valueOf(producto.getPrecioV()) == null || String.valueOf(producto.getRotacion())==null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al producto con el id "+producto.getIdProducto()+" le faltan atributos, pulse 1 para descartar y 2 para completar");
                if(sc.nextInt()==2){
                    if (producto.getTipoProducto()== null) {
                        System.out.print("Introduce el tipo del producto "+producto.getIdProducto());
                        producto.setTipoProducto(sc.nextLine());
                    }
                    if (String.valueOf(producto.getPrecioC())== null) {
                        System.out.print("Introduce el precioC del producto "+producto.getIdProducto());
                        producto.setPrecioC(sc.nextDouble());
                    }
                    if (String.valueOf(producto.getIdProveedorProducto()) == null) {
                        System.out.print("Introduce el id del proveedor del producto "+producto.getIdProducto());
                        producto.setIdProveedorProducto(sc.nextInt());
                    }
                    if(String.valueOf(producto.getPrecioV())==null){
                        System.out.println("Introduce el precioV del producto "+producto.getPrecioV());
                        producto.setPrecioV(sc.nextDouble());
                    }
                    if(String.valueOf(producto.getRotacion())==null){
                    
                        System.out.println("Introduce la rotacion del producto "+producto.getIdProducto());
                        producto.setRotacion(sc.nextInt());
                    }
                    productos.add(producto);
                     producto= null;
                }
                else{
                    Registro.registroDescartes(archivo);
                    producto= null;
                }
                sc.close();
            }
            
        }
        
    }
}
 /**
  * Creador del SAX para el producto
  * @param f
  * @return la lista con los productos
  */
public static List<Producto> crearParseadorProducto(File f){
	ProductoSAX handler = new ProductoSAX(f);
	try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	return handler.getProductos();
    }

/**
 * SAX para el proveedor
 * @author super
 *
 */
 public static class ProveedorSAX extends DefaultHandler {
    private List<Proveedor> proveedores;
    private Proveedor proveedor;
    private String atributo;
    private File archivo;

    public ProveedorSAX(File f) {
        proveedores = new ArrayList<>();
        this.archivo= f;
    }
    
    
    public List<Proveedor> getProveedores() {
		return proveedores;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("proveedor")) {
            proveedor = new Proveedor();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (proveedor != null) {
            if (atributo.equals("idProveedor")) {
                proveedor.setIdProveedor(Integer.parseInt(value));
            }
            else if (atributo.equals("direccion")) {
                proveedor.setDireccion(value);
            } else if (atributo.equals("telefono")) {
                proveedor.setTelefono(value);
            }
            else if(atributo.equals("tipoP")){
                proveedor.setTipoP(value);
            }
            else if(atributo.equals("incidencias")){
                proveedor.setIncidencias(Integer.parseInt(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("proveedor")) {
            if (String.valueOf(proveedor.getIncidencias())== null || proveedor.getDireccion() == null || proveedor.getTelefono() == null || String.valueOf(proveedor.getTipoP()) == null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Al proveedor con el id "+proveedor.getIdProveedor()+" le fatlan atributos, pulse 1 para descartar y 2 para completar");
                if(sc.nextInt()==2){
                    if (proveedor.getDireccion() == null) {
                    System.out.print("Introduce la direccion del proveedor "+proveedor.getIdProveedor());
                    proveedor.setDireccion(sc.nextLine());
                }
                if (proveedor.getTelefono() == null) {
                    System.out.print("Introduce el telefono del proveedor "+proveedor.getIdProveedor());
                    proveedor.setTelefono(sc.nextLine());
                }
                if (String.valueOf(proveedor.getTipoP())== null) {
                    System.out.print("Introduce el tipoP del proveedor "+proveedor.getIdProveedor());
                    proveedor.setTipoP(sc.nextLine());
                }
                if(String.valueOf(proveedor.getIncidencias())==null){
                    System.out.println("Introduce las incidencias del proveedor "+proveedor.getIdProveedor());

                }
                proveedores.add(proveedor);
                proveedor= null;
                }
                else{
                    Registro.registroDescartes(archivo);
                    proveedor=null;
                }
                
                sc.close();
            }
            
        }
        
    }
}
 /**
  * Creador del SAX para el proveedor
  * @param f
  * @return
  */
public static List<Proveedor> crearParseadorProveedor(File f){
 ProveedorSAX handler = new ProveedorSAX(f);
	try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	return handler.getProveedores();
    }
/**
 * SAX para la sucursal
 * @author super
 *
 */
 public static class SucursalSAX extends DefaultHandler {
    private List<Sucursal> sucursales;
    private Sucursal sucursal;
    private String atributo;
    private File archivo;

    public SucursalSAX(File f) {
        sucursales = new ArrayList<>();
        this.archivo= f;
    }
   
    
    public List<Sucursal> getSucursales() {
		return sucursales;
	}


	@Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startDocument() throws SAXException {

    }
    
    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws SAXException {
        atributo = qName;
        if (qName.equals("sucursal")) {
            sucursal = new Sucursal();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (sucursal != null) {
            if (atributo.equals("idSucursal")) {
                sucursal.setIdSucursal(Integer.parseInt(value));
            }
            else if (atributo.equals("provincia")) {
                sucursal.setProvincia(value);
            } else if (atributo.equals("localidad")) {
                sucursal.setLocalidad(value);
            }
            else if(atributo.equals("direccion")){
                sucursal.setDireccion(value);
            }
            else if(atributo.equals("telefono")){
                sucursal.setTelefono(value);
            }
            else if(atributo.equals("idDirectorSucursal")){
                sucursal.setIdDirectorSucursal(Integer.parseInt(value));
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("sucursal")) {
            if (sucursal.getProvincia()== null || sucursal.getLocalidad()== null || sucursal.getDireccion() == null || String.valueOf(sucursal.getIdDirectorSucursal()) == null || sucursal.getTelefono()==null) {
                Scanner sc = new Scanner(System.in);
                System.out.println("A la sucursal con el id "+sucursal.getIdSucursal()+" le faltan atributos, pulse 1 para descartar o pulse 2 para completar");
                if(sc.nextInt()==2){
                    if (sucursal.getProvincia()== null) {
                    System.out.print("Introduce la provincia de la sucursal "+sucursal.getIdSucursal());
                    sucursal.setProvincia(sc.nextLine());
                }
                if (sucursal.getLocalidad() == null) {
                    System.out.print("Introduce la localidad de la sucursal "+sucursal.getLocalidad());
                   sucursal.setLocalidad(sc.nextLine());
                }
                if (sucursal.getDireccion() == null) {
                    System.out.print("Introduce la direccion de la sucursal "+sucursal.getIdSucursal());
                    sucursal.setDireccion(sc.nextLine());
                }
                if(String.valueOf(sucursal.getIdDirectorSucursal())==null){
                    System.out.println("Introduce el id del director de la sucursal "+sucursal.getIdSucursal());
                    sucursal.setIdDirectorSucursal(sc.nextInt());
                }
                if(sucursal.getTelefono()==null){
                    System.out.println("Introduce el telefono de la sucursal "+sucursal.getIdSucursal());
                    sucursal.setTelefono(sc.nextLine());
                }
                sucursales.add(sucursal);
                sucursal= null;
                }
               else{
                Registro.registroDescartes(archivo);
                sucursal = null;
               }
                sc.close();
            }
            
        }
        
    }
}
 /**
  * Creador del SAX para la sucursal
  * @param f
  * @return lista con las sucursales
  */
public static List<Sucursal> crearParseadorSucursal(File f){
 SucursalSAX handler = new SucursalSAX(f);
	try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader procesadorXML= saxParser.getXMLReader();


            
            procesadorXML.setContentHandler(handler);
            saxParser.parse(f, handler);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
	return handler.getSucursales();
    }
}
