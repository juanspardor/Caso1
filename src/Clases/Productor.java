package Clases;

public class Productor extends Thread
{
	public int id;
	public int cantidadAProducir; 
	private Producto producto;
	public Bodega bodega;
	public Contador contador;
	
	public Productor(int pId, int pCantidadAProducir, Bodega pBodega, Contador pContador)
	{
		id = pId;
		cantidadAProducir = pCantidadAProducir;
		bodega = pBodega;
		producto = null;
		contador = pContador;
	}
	
	public void run()
	{
		for(int i = 0; i<cantidadAProducir; i++)
		{
			producto = new Producto(contador.aumentarProducidos(), id);
			synchronized(producto)
			{
				bodega.almacenar(producto);	//Este tiene un sync en clase Bodega (buffer)
				producto.dormir(); //Este tiene un sync en la clase Producto
			}
		}
		System.out.println("Termino el productor "+id);
	}
	
}
