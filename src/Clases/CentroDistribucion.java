package Clases;

public class CentroDistribucion 
{
	public Producto producto;
	public boolean estadoSistema;
	
	public CentroDistribucion()
	{
		producto = null;
		boolean estadoSistema = false;
	}
	
	public synchronized void almacenar(Producto pProducto)
	{
		while(producto==null)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		producto = pProducto;
		notify();
	}
	
	public synchronized Producto retirar()
	{
		while(producto==null)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		notifyAll();
		Producto rta = producto;
		producto = null;
		return rta;
	}
	
	public synchronized void finalizo()
	{
		estadoSistema = true;
		notifyAll();
	}
}
