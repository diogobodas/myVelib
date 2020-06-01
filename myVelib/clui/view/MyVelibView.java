package view;

import java.util.Observable;
import java.util.Observer;

import model.MyVelibModel;

@SuppressWarnings("deprecation")
/**
 * View for the Velub System CLUI.
 *
 */
public class MyVelibView implements Observer{
	
	private MyVelibModel model;
	
	public MyVelibView(MyVelibModel model) {
		super();
		this.model = model;
		this.model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// update pra toda vez que o sistema mudar printar a atualizacao
		System.out.println(arg);
	}
	
	public void displayUser(MyVelibModel m,Integer ID) {
		System.out.println("To no display users");
		// completar
	}
	
	public void displayStation(MyVelibModel m,Integer ID) {
		System.out.println("To no display stations");
		// completar
	} 
	
	public void sortStation

}
