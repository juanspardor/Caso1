package Clases;

import java.util.Scanner;

public class Despachador extends Thread
{
	/**
	 * Bodega de productos. Es estatica para no tener que mandarselo al despachador como parametro
	 */
	private static Bodega bodega;
	
	/**
	 * Centro de distribucion de productos. Es estatico para no tener que mandarselo al despachador como parametro
	 */
	private static CentroDistribucion centro = new CentroDistribucion();
	
	/**
	 * Contador general del sistema. Es estatico para no tener que mandarselo al despachador como parametro
	 */
	private static Contador contador;
	
	/**
	 * Producto a enviar a los repartidores
	 */
	private Producto aEnviar;

	/**
	 * Constructor del despachador
	 */
	public Despachador()
	{
		//Inicia sin producto a entregar
		aEnviar = null;
	
	}
	
	public void run()
	{
		//Variable que guarda si debe serguir buscando productos en bodega
		boolean pararActivo = false;

		//Mientras aun no se entreguen productos, debe dar vueltas activamente
		while(!contador.verificarEstado())
		{
			
			//Primero debe revisar si hay productos en bodega para entregar, y si debe seguir buscando productos en bodega
			// pararActivo va de primeras en la condicion porque no implica un llamado a un metodo en contador (no entra a un monitor)
			//parar activo va primero porque no implica un llamado a un metodo
			while(!pararActivo && !bodega.hayProductos())
			{
				//Si entro es porque no hay productos en bodega, pero aun no se producen todos los que se necesita. Le toca hacer espera activa
				System.out.println("...Despachador esperando productos para dar a repartidores...");
				
				//Mientras espera le pregunta al contador si mientras ha estado aca adentro se produjeron todos los productos a producir
				pararActivo = contador.pararBusqueda();
				
				//Se duerme un ratico
				try 
				{
					Thread.sleep(750);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			//Al salir del while hay 2 posibilidades
			
			//POSIBILIDAD 1: todavia habian productos por producirse y habia al menos 1 en bodega ==> Debe guardarlo en el centro de distribucion
			if(!pararActivo)
			{
				//Retira el producto de bodega (aca se puede dormir)
				Producto recibido = bodega.retirar();
				
				//Almacena el producto en centro de distribucion
				centro.almacenar(recibido);	
			}
			
			//POSIBILIDAD 2: ya no tiene que intentar sacar productos de bodega porque ya se produjeron todos ==> no hace nada
		}
		
		//Al salir de este ciclo significa que ya termino el sistema de funcionar, puede terminar su operacion
		System.out.println("Termino el despachador");
		
	}
	public static void main(String[] args) 
	{
		//Se leen los datos de entrada
		Scanner lector = new Scanner(System.in);
		//Numero de productos
		System.out.println("Por favor ingrese el numero de productos a producir: ");
		int totalProductos = lector.nextInt();
		
		//Numero de productores
		System.out.println("Por favor ingrese el numero de productores: ");
		int numProductores = lector.nextInt();
		
		//Numero de repartidores
		System.out.println("Por favor ingrese el numero de repartidores: ");
		int numRepartidores = lector.nextInt();
		
		//TAM bodega
		System.out.println("Por favor ingrese la capacidad de la bodega: ");
		int tam = lector.nextInt();
		
		//Se calculan cuantos productos cada productor tiene que hacer
		int undPProductor = totalProductos/numProductores;
		
		//Extra por hay residuo en la division
		int extra = totalProductos-undPProductor*numProductores;
		
		//Se crea la bodega con el TAM especificado
		bodega = new Bodega(tam);
		
		//Se crea el contador con el total de productos a hacer y entregar
		contador = new Contador(totalProductos);
		
		//Se crea el despachador y se pone a ejecutar
		Despachador des = new Despachador();
		des.start();
		
		System.out.println("");
		System.out.println("");
		
		
		//Se crean todos los productores especificados y se ponene a correr
		for(int i = 1; i<=numProductores; i++)
		{
			//Al ultimo productor se le asigna un extra por si la divison entera tuvo sobrante
			if(i==numProductores)
			{
				Productor act = new Productor(i, undPProductor+extra, bodega, contador);
				act.start();	
			}
			else
			{
				Productor act = new Productor(i, undPProductor, bodega, contador);
				act.start();
			}
			
		}
		
		//Se crean los repartidores especificados y se ponen a correr
		for(int i = 1; i<= numRepartidores; i++)
		{
			Repartidor act = new Repartidor(centro, contador, i);
			act.start();
		}
		
	

	}

}
