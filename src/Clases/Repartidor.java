package Clases;

/**
 * Clase que representa un repartidor
 */
public class Repartidor extends Thread
{
	/**
	 * Producto que va a entregar
	 */
	private Producto paquete;
	
	/**
	 * Centro de distribucion de donde saca los productos
	 */
	private CentroDistribucion cd;
	
	/**
	 * Variable que indica si faltan productos por recogeer
	 */
	private boolean faltan;
	
	/**
	 * Contador global del sistema (le avisa si al entregar, si esa entrega fue la ultima y ya no hay mas por hacer)
	 */
	private Contador contador;
	
	/**
	 * Id del repartidor
	 */
	private int id;
	
	/**
	 * Constructor del repartidor
	 * @param pCd - centro de distribucion
	 * @param pContador - contador
	 * @param pId - id del repartidor
	 */
	public Repartidor(CentroDistribucion pCd, Contador pContador, int pId)
	{
		//Al crearse faltan productor por recoger
		faltan = true;
		cd = pCd;
		
		//Spawnea sin producto recogido
		paquete = null;
		contador = pContador;
		id = pId;
	}

	public void run()
	{
		//Mientras hayan productos por recoger, va a estar dando vueltas intentando recoger
		while(faltan)
		{
			//Intenta retirar un producto del centro de distribucion (aca se puede dormir)
			paquete = cd.retirar();
			
			//Al despertarse, saco un producto del centro de distribucion. Hay dos posibilidades
			
			//POSIBILIDAD 1: El producto que saco no es null (aun no se entregaron todos los productos que toca)
			if(paquete!=null)
			{
				
				//Avisa que inicia su trayecto de delivery
				System.out.println("El repartidor "+id + " empieza a repartir del producto " +paquete.id);
				int duracion = (int) (Math.random() * (5-3)+3); //Se calcula el tiempo de entrega
				try 
				{
					Thread.sleep(duracion*1000); //El thread se duerme el tiempo que se demora en entregar
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				//Le avisa al contador que acaba de entregar un producto
				contador.aumentarEntregados();
				
				//Avisa por consola que se entrego el producto
				System.out.println("Se entrego el producto "+paquete.id + " producido por "+paquete.producidorPor);
				
				//Despierta al productor que estaba dormido sobre el producto para que siga produciendo sus unidades faltantes
				paquete.despertar();
				
				//Le pregunta al contador si faltan productos por entregar
				faltan = !contador.verificarEstado();
				
				//Si no faltan: manda a todos los repartidores que faltan a recoger un null para que terminen su operacion
				if(!faltan)
				{
					cd.finalizo();
				}
			}
			
			//POSIBILIDAD 2: el producto que recogio es null (ya no hay productos por entregar)
			else
			{
				//Ya no faltan productos por entregar. Puede salirse del while
				faltan = false;
			}

		}
		//Avisa por consola que el thread termino de operar
		System.out.println("Termino el repartidor "+id);
	}
}
