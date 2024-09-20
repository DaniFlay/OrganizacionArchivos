drop database practica_acceso_a_datos;
create database practica_acceso_a_datos;
use practica_acceso_a_datos;

create table clientes(idCliente int, nombre varchar(15), apellidos varchar(50), tipoCliente varchar(10),
 nCompras int, primary key (idCliente));
 
 create table sucursales( idSucursal int, provincia varchar(50), localidad varchar(50), direccion varchar(80), telefono char(9),
 idDirectorSucursal int, primary key(idSucursal));
 
create table proveedores(idProveedor int, direccion varchar(50), telefono char(9), tipoP varchar(15), incidencias int,
 primary key(idProveedor));
 
 create table empleados(idEmpleado int, nombre varchar(30), apellidos varchar(50), idSucursalEmpleado int,
 categoria int, antiguedad int, salario double, primary key(idEmpleado, idSucursalEmpleado), foreign key (idSucursalEmpleado) references
 sucursales (idSucursal));
 
 create table productos( idProducto int, tipoProducto varchar(50), precioC double, idProveedorProducto int, precioV double, 
 rotacion int, primary key(idProducto,idProveedorProducto), foreign key (idProveedorProducto) references proveedores(idProveedor));
 
 create table pedidos( idPedido int, idCliente int, idEmpleado int, fecha date, precioFinal double,
 primary key (idPedido), foreign key(idCliente) references clientes(idCliente), foreign key(idEmpleado) references empleados(idEmpleado));
 
 create table detallesDeLosPedidos(idPedido int, idProducto int, idProveedorProducto int, precioUniad double, 
 cantidad int, descuento double, precioFinal double, primary key(idPedido, idProducto, idProveedorProducto),
 foreign key (idPedido) references pedidos(idPedido), foreign key(idProducto) references productos(idProducto),
 foreign key (idProveedorProducto) references proveedores(idProveedor));