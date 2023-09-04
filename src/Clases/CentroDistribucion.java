package Clases;

public class CentroDistribucion 
{
	public Producto producto;
	public boolean estadoSistema;
	
	
	public CentroDistribucion()
	{
		producto = null;
		estadoSistema = false;
	}
	
	public synchronized void almacenar(Producto pProducto)
	{
		while(producto!=null)
		{
			try {
				System.out.println("El despachador se durmio en el C.D");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Se almaceno en CD el producto "+pProducto.id + " hecho por el productor "+pProducto.producidorPor);
		producto = pProducto;
		notify();
	}
	
	public synchronized Producto retirar()
	{
		while(producto==null && !estadoSistema)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(!estadoSistema)
		{
			notifyAll();
		}
		
		Producto rta = producto;
		producto = null;
		if(rta != null)
		{
			System.out.println("Se retiro de C.D el producto "+rta.id + " producido por "+rta.producidorPor);	
		}
		return rta;
	}
	
	public synchronized void finalizo()
	{
		System.out.println("Incia terminacion de operadores (threads) faltantes/sobrantes");
		estadoSistema = true;
		notifyAll();

	}
	
}
