package Clases;

public class Producto 
{
	public int id;
	
	public int producidorPor;
	
	public Producto(int pId, int pProducidorPor)
	{
		id = pId;
		producidorPor = pProducidorPor;
	}
	
	public synchronized void dormir()
	{
		try {
			System.out.println("Se durmio el productor "+ producidorPor + " en el producto "+id);
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void despertar()
	{
		notify();
		System.out.println("Se desperto "+ producidorPor);
	}
}
