package Clases;

import java.util.ArrayList;
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
		while(inventario.size() == 0)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		inventario.add(productoNuevo);
		synchronized (productoNuevo)
		{
			try {
				productoNuevo.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized Producto retirar()
	{
		Producto rta = inventario.pop();
		return rta;
	}
	
	public synchronized boolean hayProductos()
	{
		return inventario.size() == 0;
	}
}


