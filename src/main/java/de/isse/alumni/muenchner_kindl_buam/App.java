package de.isse.alumni.muenchner_kindl_buam;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	LombokHelloWorld lhw = new LombokHelloWorld("Arjen", 12);
    	
        System.out.println( "Hello World! " + lhw.getAge() );
    }
}