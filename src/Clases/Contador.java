package Clases;

/**
 * Clase que representa el contador general del sistema. Este sabe sincronicamente cuantos productos llevan producidos y entregados

 */
public class Contador 
{
	/**
	 * Numero de productos producidos en cualquier momento de ejecucion
	 */
	public int numProducidos;
	
	/**
	 * Numero de productos entregados en cualquier momento de ejecucion
	 */
	public int numEntregados;
	
	/**
	 * Numero total de productos a producir y entregar
	 */
	public int totales;
	
	/**
	 * Constructor
	 * @param pTotales - productos totales a producir y entregar
	 */
	public Contador(int pTotales)
	{
		totales = pTotales;
		numProducidos = 0;
		numEntregados = 0;
	}
	

	
	public synchronized int aumentarProducidos()
	{
		numProducidos++;
		System.out.println("\n Numero total de productos producidos: "+numProducidos +"\n");
		return numProducidos;
	}
	
	/**
	 * Metodo que aumenta el numero de productos entregados 
	 * @return
	 */
	public synchronized void aumentarEntregados()
	{
		//Aumenta el valor
		numEntregados++;
		
		//Reporta que el valor de productos que se han entregado hasta el momento que se llama al metodo (incluyendo el nuevo)
		System.out.println("\n Numero total de productos entregados: "+numEntregados +"\n");
	}
	
	/**
	 * Metodo que indica el despachador si debe seguir buscando productos en bodega para entregar
	 * @return True si faltan productos por producir. False si ya se produjeron todos los productos que se tenian que hacer
	 */
	public synchronized boolean pararBusqueda()
	{
		return numProducidos == totales;
	}
	
	/**
	 * Metodo que indica si ya se entregaron todos los productos
	 * @return True si ya se entregaron todos los productos. False d.l.c
	 */
	public synchronized boolean verificarEstado()
	{
		return numEntregados == totales;
	}
}
