package Clases;

import java.util.Scanner;

public class Despachador extends Thread
{
	public static Bodega bodega;
	public static CentroDistribucion centro = new CentroDistribucion();
	
	public Despachador()
	{
		
	}
	
	public static void main(String[] args) 
	{
		Scanner lector = new Scanner(System.in);
		System.out.println("Por favor ingrese el numero de productos a producir: ");
		int totalProductos = lector.nextInt();
		System.out.println("Por favor ingrese el numero de productores: ");
		int numProductores = lector.nextInt();
		System.out.println("Por favor ingrese el numero de repartidores: ");
		int numRepartidores = lector.nextInt();
		System.out.println("Por favor ingrese la capacidad de la bodega: ");
		int tam = lector.nextInt();
		
		int undPProductor = totalProductos/numProductores;
		int extra = totalProductos-undPProductor*numProductores;
		
		bodega = new Bodega(tam);
//		for(int i = 1; i<=numProductores; i++)
//		{
//			if(i==numProductores)
//			{
//				Productor act = new Productor(i, undPProductor+extra, bodega);
//				act.start();	
//			}
//			else
//			{
//				Productor act = new Productor(i, undPProductor, bodega);
//				act.start();
//			}
//			
//		}
		
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
