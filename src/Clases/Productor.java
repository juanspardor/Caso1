package Clases;

/**
 * Clase que representa un thread de productor
 */
public class Productor extends Thread
{
	/**
	 * Id del productor
	 */
	private int id;
	
	/**
	 * Cantidad de productos que este productor debe hacer
	 */
	private int cantidadAProducir; 
	
	/**
	 * Producto que acaba de producir y va a entregar en bodega
	 */
	private Producto producto;
	
	/**
	 * Bodega en la que almacena productos
	 */
	private Bodega bodega;
	
	/**
	 * Contador para asignar id a cada producto (para que quede en orden con respecto a los demas threads)
	 */
	private Contador contador;
	
	/**
	 * Constructor
	 * @param pId - id del productor
	 * @param pCantidadAProducir - cantidad de productos que debe hacer
	 * @param pBodega - bodega en la que deposita productos
	 * @param pContador - contador del sistema
	 */
	public Productor(int pId, int pCantidadAProducir, Bodega pBodega, Contador pContador)
	{
		id = pId;
		cantidadAProducir = pCantidadAProducir;
		bodega = pBodega;
		
		//Producto empieza en null porque al spawnear aun no ha creado ningun producto
		producto = null;
		contador = pContador;
	}
	
	public void run()
	{
//		Ciclo que controla la ejecucion del productor (por cada producto que debe hacer se ejecutan las instrucciones)
		for(int i = 0; i<cantidadAProducir; i++)
		{
			//Se crea un nuevo producto. Aca se tiene el aumentar producidos adentro del constructor, porque el valor que
			// retorna es el id del producto con respecto a los demas threads. Ese id es global en el orden de produccion por que es sync
			producto = new Producto(contador.aumentarProducidos(), id);
			
			//Almacena en bodega el producto (puede que se duerma en bodega si no hay espacio)
			bodega.almacenar(producto);	
			
			//Si sale de bodega, significa que logro almacenar el producto. Por lo tanto se duerme en este hasta que sea entregado
			producto.dormir();
			
			//Al despertarse vuelve a interar el loop
		}
		
		//Cuando termina el for significa que ya termino de producir todas las unidades que le tocaba. El thread se termina
		System.out.println("Termino el productor "+id);
	}
	
}
