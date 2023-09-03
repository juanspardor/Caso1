package Clases;

public class Repartidor extends Thread
{
	public Producto paquete;
	public CentroDistribucion cd;
	public boolean faltan;
	
	public Repartidor(CentroDistribucion pCd)
	{
		faltan = true;
		cd = pCd;
		paquete = null;
	}
	
	public void run()
	{
		while(faltan)
		{
			paquete = cd.retirar();
			int duracion = (int) (Math.random() * (10-3)+3);
			try {
				Thread.sleep(duracion*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paquete.despertar();
		}
	}
}
