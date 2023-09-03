package Clases;

public class Productor extends Thread
{
	public int id;
	public int cantidadAProducir; 
	private Producto producto;
	public Bodega bodega;
	
	public Productor(int pId, int pCantidadAProducir, Bodega pBodega)
	{
		id = pId;
		cantidadAProducir = pCantidadAProducir;
		bodega = pBodega;
		producto = null;
	}
	
	public void run()
	{
		for(int i = 0; i<cantidadAProducir; i++)
		{
			producto = new Producto(i, id);
			synchronized(producto)
			{
				bodega.almacenar(producto);	
				producto.dormir(); //Este tiene un sync en la clase Producto
			}
			
		}
	}
	
}
