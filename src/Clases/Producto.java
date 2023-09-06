package Clases;

/**
 * Clase que representa un producto del sistema

 */
public class Producto 
{
	/**
	 * Id del producto
	 */
	public int id;
	
	/**
	 * Id del productor de este producto
	 */
	public int producidorPor;
	
	/**
	 * Constructor de un producto
	 * @param pId - id del producto
	 * @param pProducidorPor - id del productor
	 */
	public Producto(int pId, int pProducidorPor)
	{
		id = pId;
		producidorPor = pProducidorPor;
	}
	
	/**
	 * Metodo que hace que el productor se duerma sobre el producto (cuando se deja en bodega)
	 */
	public synchronized void dormir()
	{
		try 
		{
			//Se avisa por consola que se durmio el productor
			System.out.println("Se durmio el productor "+ producidorPor + " en el producto "+id);
			wait();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que despierta al productor que se durmio sobre el producto (al entregarse)
	 */
	public synchronized void despertar()
	{
		//Este notify despierta al productor que estaba dormido sobre el objeto (producto)
		notify();
		
		//Se avisa que se desperto el productor
		System.out.println("Se desperto el productor "+ producidorPor);
	}
}
