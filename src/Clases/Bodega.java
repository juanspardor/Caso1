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
				System.out.println("Se duerme el productor en bodega " + productoNuevo.producidorPor);
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Se almaceno en bodega el producto "+productoNuevo.id + " producido por "+productoNuevo.producidorPor);
		inventario.add(productoNuevo);
		
		
	}
	
	public synchronized Producto retirar()
	{
		Producto rta = inventario.pop();
		System.out.println("Se retiro de bodega el producto "+rta.id + " producido por "+rta.producidorPor);
		notifyAll();
		return rta;
	}
	
	public synchronized boolean hayProductos()
	{
		return !(inventario.size() == 0);
	}
}


