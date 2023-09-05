package Clases;

import java.util.LinkedList;

/**
 * Clase que representa la bodega del sistema (buffer entre productor y despachador)

 */
public class Bodega 
{
	/**
	 * Capacidad de la bodega
	 */
	public int capacidad;
	
	/**
	 * Productos que estan en inventario
	 */
	public LinkedList<Producto> inventario;
	
	/**
	 * Constructor de la bodega
	 * @param pCapacidad - capacidad maxima de la bodega
	 */
	public Bodega(int pCapacidad)
	{
		capacidad = pCapacidad;
		inventario = new LinkedList<Producto>();
	}
	
	/**
	 * Metodo que permite a un productor almacenar un producto en la bodega
	 * @param productoNuevo - producto a almacenar
	 */
	public synchronized void almacenar(Producto productoNuevo)
	{
		while(inventario.size() == capacidad)
		{
			//Si esta llena la bodega, el productor se duerme sobre esta
			try 
			{
				//Se avisa por consola que se durmio en bodega
				System.out.println("Se duerme el productor " + productoNuevo.producidorPor +"  en bodega");
				wait();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Al despertarse el productor agrega su producto al inventario (se avisa por consola)
		System.out.println("Se almaceno en bodega el producto "+productoNuevo.id + " hecho por el productor "+productoNuevo.producidorPor);
		inventario.add(productoNuevo);
		
		
	}
	
	/**
	 * Metodo que le permite al despachador retirar un producto
	 * @return Producto retirado
	 */
	public synchronized Producto retirar()
	{
		//Se saca un producto
		Producto rta = inventario.pop();
		
		//Se avisa por consola el retiro
		System.out.println("El despachador retiro de bodega el producto "+rta.id + " hecho por el productor "+rta.producidorPor);
		
		//Se notifica para que pueda entrar algun productor al monitor (si hay alguno en fila)
		notify();
		return rta;
	}
	
	/**
	 * Metodo que avisa si hay productos por entregar
	 * @return True si hay productos por entregar. False d.l.c
	 */
	public synchronized boolean hayProductos()
	{
		return (inventario.size() != 0);
	}
}


