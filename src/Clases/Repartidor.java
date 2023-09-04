package Clases;

public class Repartidor extends Thread
{
	public Producto paquete;
	public CentroDistribucion cd;
	public boolean faltan;
	public Contador contador;
	public int id;
	
	public Repartidor(CentroDistribucion pCd, Contador pContador, int pId)
	{
		faltan = true;
		cd = pCd;
		paquete = null;
		contador = pContador;
		id = pId;
	}

	public void run()
	{
		while(faltan)
		{

			paquete = cd.retirar();
			if(paquete!=null)
			{
				System.out.println("El repartidor "+id + " empieza a repartir del producto " +paquete.id);
				int duracion = (int) (Math.random() * (5-3)+3);
				try {
					Thread.sleep(duracion*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				contador.aumentarEntregados();
				System.out.println("Se entrego el producto "+paquete.id + " producido por "+paquete.producidorPor);
				paquete.despertar();
				faltan = !contador.verificarEstado();
				if(!faltan)
				{
					cd.finalizo();
				}
			}
			else
			{
				faltan = false;
			}


		}
		System.out.println("Termino el repartidor "+id);
	}
}
