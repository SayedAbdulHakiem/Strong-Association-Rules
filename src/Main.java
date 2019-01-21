public class Main 
{
	public static void main(String[] args) 
	{
		Controller controller=new Controller();
		controller.setMinSupport(5);
		controller.setConfidence(0.15);
		controller.printStrongCorrelatedItems();
		
		//System.out.println(controller.getFrequentSet());
	}

}
