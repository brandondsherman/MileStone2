package physics;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import gameWorldObjects.MySphere;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.scene.SceneNode;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Sphere;

public class PhysicsBase {
	 private ICamera camera;
	 private Rectangle graphicalGroundPlane;
	 private SceneNode graphicalBall;
	 private SceneNode player;
	 private boolean running;
	 private Vector3D playerLoc;

	private BroadphaseInterface broadPhaseHandler;
	private int maxProxies = 1024;
	private Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
	private Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
	private DynamicsWorld physicsWorld;
	private ConstraintSolver solver;
	private CollisionConfiguration collConfig;
	private RigidBody physicsGround;
	private CollisionDispatcher collDispatcher;
	private RigidBody physicsBall;
	
	
	public PhysicsBase(SceneNode player, SceneNode ball){
		Transform myTransform;
		graphicalBall = ball;
		this.player=player;
		running=false;
		
		
		broadPhaseHandler = new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
		
		collConfig = new DefaultCollisionConfiguration();
		collDispatcher = new CollisionDispatcher(collConfig);
	
		solver = new SequentialImpulseConstraintSolver();
		
		physicsWorld = new DiscreteDynamicsWorld(collDispatcher, broadPhaseHandler, solver, collConfig);
		physicsWorld.setGravity(new Vector3f(0,-10,0));
	
		CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0,1,0),1);
		
		myTransform = new Transform();
		myTransform.origin.set(new Vector3f(0,-1,0));
		myTransform.setRotation(new Quat4f(0,0,0,1));
		
		DefaultMotionState groundMotionState = new DefaultMotionState(myTransform);
		RigidBodyConstructionInfo groundCl = new RigidBodyConstructionInfo(0,groundMotionState,groundShape,new Vector3f(0,0,0));
	
		groundCl.restitution = .8f;
		
		physicsGround = new RigidBody(groundCl);
		physicsWorld.addRigidBody(physicsGround);
		
		CollisionShape fallShape = new SphereShape(1);
		myTransform = new Transform();
		myTransform.origin.set(new Vector3f(15,1,-5));
		myTransform.setRotation(new Quat4f(0,0,0,1));
		
		DefaultMotionState fallMotionState = new DefaultMotionState(myTransform);
		
		float myFallMass = 1;
		Vector3f myFallIntertia = new Vector3f(0,0,0);
		fallShape.calculateLocalInertia(myFallMass, myFallIntertia);
		
		RigidBodyConstructionInfo fallRigidBodyCl = new RigidBodyConstructionInfo(myFallMass, fallMotionState, fallShape,myFallIntertia);
		fallRigidBodyCl.restitution = .8f;
		
		physicsBall = new RigidBody(fallRigidBodyCl);
		physicsWorld.addRigidBody(physicsBall);
	}
	
	public void update(float time){
		 if (running)
		 { physicsWorld.stepSimulation(1.0f / 60.0f, 8); // 1/60th sec, 8 steps

		 
		 
		 
		 Transform pBallTransform = new Transform();
		 physicsBall.getMotionState().getWorldTransform(pBallTransform);
	
		 float[] vals = new float[16];
		 pBallTransform.getOpenGLMatrix(vals);
		 Matrix3D gBallXform = new Matrix3D(vals);
		 graphicalBall.setLocalTranslation(gBallXform);
		 }else{
			 playerLoc = player.getLocalTranslation().getCol(3);
		 }
	}
	
	public void setRunning(boolean isRunning){
		running = isRunning;
	}
}
