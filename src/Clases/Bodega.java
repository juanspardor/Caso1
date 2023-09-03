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
		inventario.add(productoNuevo);
		
		
		
	}
	
	public void esperar(Producto pProducto)
	{
		try {
			pProducto.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized Producto retirar()
	{
		Producto rta = inventario.pop();
		System.out.println("Se saca el producto de: " +rta.producidorPor);
		notify();
		return rta;
	}
	
	public synchronized boolean hayProductos()
	{
		return inventario.size() == 0;
	}
}


