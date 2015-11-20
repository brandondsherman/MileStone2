package physics;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class StartPhysics extends AbstractInputAction{

	private PhysicsBase physics;
	
	public StartPhysics(PhysicsBase physics){
		this.physics = physics;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("in startphyics performaction");
		physics.setRunning(true);
		
	}

}
