package Clases;

public class Contador 
{
	private int numProducidos;
	private int numEntregados;
	
	public Contador()
	{
		numProducidos = 0;
		numEntregados = 0;
	}
	
	public synchronized int darProducidos()
	{
		return numProducidos;
	}
	
	public synchronized int aumentarProducidos()
	{
		numProducidos++;
		System.out.println("Productos producidos: "+numProducidos);
		return numProducidos;
	}
	
	public synchronized int darEntregados()
	{
		return numEntregados;
	}
	
	public synchronized int aumentarEntregados()
	{
		numEntregados++;
		System.out.println("Productos entregados: "+numEntregados);
		return numEntregados;
	}
}
