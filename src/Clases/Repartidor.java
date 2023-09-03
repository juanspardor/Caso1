package Clases;

public class Repartidor extends Thread
{
	public Producto paquete;
	public CentroDistribucion cd;
	public boolean faltan;
	public Contador contador;

	public Repartidor(CentroDistribucion pCd, Contador pContador)
	{
		faltan = true;
		cd = pCd;
		paquete = null;
		contador = pContador;
	}

	public void run()
	{
		while(faltan)
		{

			paquete = cd.retirar();
			if(paquete!=null)
			{
				int duracion = (int) (Math.random() * (5-3)+3);
				try {
					Thread.sleep(duracion*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				paquete.despertar();
				contador.aumentarEntregados();
				faltan = !contador.verificarEstado();
			}
			else
			{
				faltan = false;
			}


		}
		System.out.println("Termino un repartidor ");
	}
}
