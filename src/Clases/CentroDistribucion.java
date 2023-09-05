package Clases;

/**
 * Clase que representa el buffer que hay entre despachador y repartidores
 */
public class CentroDistribucion 
{
	/**
	 * Unico producto que puede tener el centro de distribucion
	 */
	public Producto producto;
	
	/**
	 * Variable que indica si ya se entregaron todos los productos a entregar
	 */
	public boolean estadoSistema;
	
	/**
	 * Constructor. Se inicia con ningun producto, y con estado de finalizacion en falso
	 */
	public CentroDistribucion()
	{
		producto = null;
		estadoSistema = false;
	}
	
	/**
	 * Metodo para que el despachador pueda almacenar un producto
	 * @param pProducto - producto a almacenar
	 */
	public synchronized void almacenar(Producto pProducto)
	{
		//Se revisa si hay espacio (no hay producto actualmente)
		while(producto!=null)
		{
			try 
			{
				//Si no hay espacio, el despachador espera pasivamente
				System.out.println("El despachador se durmio en el C.D");
				wait();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Al despertarse se avisa al usuario
		System.out.println("Se almaceno en CD el producto "+pProducto.id + " hecho por el productor "+pProducto.producidorPor);
		
		//Se almacena el producto
		producto = pProducto;
		
		//Notifica que sale del monitor para que entre algun repartidor si hay en espera
		notify();
	}
	
	/**
	 * Metodo que permite a un repartidor retirar un producto. 
	 * Es un metodo muy importante porque tambien ayuda a terminar la ejecucion de los repartidores que se quedaron esperando
	 * mientras se entregaba el ultimo producto. 
	 * @return Producto, si el sistema aun no se finaliza y hay producto para recoger. null si ya termino la operacion del sistema
	 */
	public synchronized Producto retirar()
	{
		//Pregunta si: hay producto? termino el sistema?
		while(producto==null && !estadoSistema)
		{
			//Si todavia esta activo el sistema, pero no hay producto para recoger, hay espera pasiva
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}	
		//Si paso del while hay 2 posibilidades:
		
		
		//POSIBILIDAD 1: aun no se entregan todos los productos, salio de espera pasiva y recogio un producto 
		if(!estadoSistema)
		{
			//NotifyAll, porque puede que el despachador este en espera pasiva
			notifyAll();
			
			//Se coge el producto a sacar
			Producto rta = producto;
			
			//Ahora el C.D no tiene producto 
			producto = null;
			
			//Se avisa por consola de la accion
			System.out.println("Se retiro de C.D el producto "+rta.id + " producido por "+rta.producidorPor);
			
			//Se retorna el producto
			return rta;
		}
		
		//POSIBILIDAD 2: ya se entregaron todos los productos, es decir, el estado de finalizacion es cierto
		else
		{
			//Retorna null
			//No necesitamos llamar a ningun notify porque si se finalizo, ningun thread entro al while. Por ende, ninguno se durmio
			return null;
		}
	}
	
	/**
	 * Metodo que le permite al sistema mandar a todos los repartidores en espera a recoger un null (porque ya se entregaron todos los productos)
	 */
	public synchronized void finalizo()
	{
		//Se avisa por consola que termina la opracion
		System.out.println("\nIncia terminacion de operadores (threads) faltantes/sobrantes:");
		
		//La variable de estado de finalizacion toma el valor de verdadero
		estadoSistema = true;
		
		//Notify all a todos los repartidores en espera pasiva
		notifyAll();

	}
	
}
