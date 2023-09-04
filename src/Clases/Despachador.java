package Clases;

import java.util.Scanner;

public class Despachador extends Thread
{
	public static Bodega bodega;
	public static CentroDistribucion centro = new CentroDistribucion();
	public static Contador contador;
	
	public Producto aEnviar;
	public int numAEntregar;
	
	public Despachador(int pNumAEntregar)
	{
		aEnviar = null;
		numAEntregar = pNumAEntregar; //Se puede quitar
	}
	
	public void run()
	{
		boolean pararActivo = false;

		while(!contador.verificarEstado())
		{
			//parar activo va primero porque no implica un llamado a un metodo
			while(!pararActivo && !bodega.hayProductos())
			{
				System.out.println("...Despachador esperando productos para dar a repartidores...");
				pararActivo = contador.pararBusqueda();
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!pararActivo)
			{
				Producto recibido = bodega.retirar();
				centro.almacenar(recibido);	
			}
		}
//		centro.finalizo();z
		
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
		
		contador = new Contador(totalProductos);
		
		Despachador des = new Despachador(totalProductos);
		des.start();
		
		System.out.println("");
		System.out.println("");
		
		for(int i = 1; i<=numProductores; i++)
		{
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
		
		for(int i = 1; i<= numRepartidores; i++)
		{
			Repartidor act = new Repartidor(centro, contador, i);
			act.start();
		}
		
		
//		Productor p1 = new Productor(1, 1, bodega);
//		Productor p2 = new Productor(2, 1, bodega);
//		p1.start();
//		p2.start();
//		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		bodega.retirar().despertar();
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		bodega.retirar().despertar();
	

	}

}
