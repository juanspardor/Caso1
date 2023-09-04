package Clases;

import java.util.LinkedList;

public class Bodega 
{
	public int capacidad;
	
	public LinkedList<Producto> inventario;
	
	public Bodega(int pCapacidad)
	{
		capacidad = pCapacidad;
		inventario = new LinkedList<Producto>();
	}
	
	
	public synchronized void almacenar(Producto productoNuevo)
	{
		while(inventario.size() == capacidad)
		{
			try {
				System.out.println("Se duerme el productor " + productoNuevo.producidorPor +"  en bodega");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Se almaceno en bodega el producto "+productoNuevo.id + " hecho por el productor "+productoNuevo.producidorPor);
		inventario.add(productoNuevo);
		
		
	}
	
	public synchronized Producto retirar()
	{
		Producto rta = inventario.pop();
		System.out.println("El despachador retiro de bodega el producto "+rta.id + " hecho por el productor "+rta.producidorPor);
		notify();
		return rta;
	}
	
	public synchronized boolean hayProductos()
	{
		return (inventario.size() != 0);
	}
}


