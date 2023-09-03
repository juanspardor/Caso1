package Clases;

public class Despachador extends Thread
{
	public static Bodega bodega = new Bodega(1);
	
	public Despachador()
	{
		
	}
	
	public static void main(String[] args) 
	{
		Productor p1 = new Productor(1, 1, bodega);
		Productor p2 = new Productor(2, 1, bodega);
		p1.start();
		p2.start();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bodega.retirar().despertar();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bodega.retirar().despertar();
	

	}

}
