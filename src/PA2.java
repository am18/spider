//****************************************************************************
// Name: Ayusha Mittal
// Class: CS 480 - Intro to Computer Graphics
// Programming Assignment 2
// File: PA2.java
// Due: October 18 by midnight
//
// This code is used to run a spider simulation, modeled using OpenGL functions and
// the homogeneous coordinate system. There are 8 legs, the head, body, eyes and pincers.
// The code below is repurposed from the original provided by Professor Bargal for the
// hand model simulation. This code has kept the original naming conventions and
// functions and added/removed functionality to resemble a spider, such as more
// joints, different body components, and rotation limits for the joints. The code
// uses the following classes to model the spider: Point 3D, Palm, Rounded Cylinder,
// and Component. The five test cases are provided in the TestCases.java file.
// The user interacts with the interface using mouse process to change the viewing
// angle and a keyboard interface described below to change the joint rotations. 
//****************************************************************************


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl
import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

/**
 * The following were provided as part of the assignment by Prof. Bargal:
 * 
 * The main class which drives the hand model simulation.
 * 
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2008
 */
public class PA2 extends JFrame implements GLEventListener, KeyListener,
    MouseListener, MouseMotionListener {

  /**
   * The leg of a spider which has four parts:
   * 	the body joint, Femur, Tibia, and a claw.
   * 
   * Using the code of 'Finger' class by @author Jeffrey Finkelstein 
   * <jeffrey.finkelstein@gmail.com>
   * @since Spring 2011
   */
	
  private class Leg {
	  /** The list of all the joints in this leg. */
	  private final List<Component> joints;
	  /** The claw joint of this leg. */
	  private final Component clawJoint;
	  /** The distal part of this leg. */
	  private final Component tibiaJoint;
	  /** The middle part of this leg. */
	  private final Component femurJoint;
	  /** The body joint of this leg. */
	  private final Component bodyJoint;
   
         
    /**
     * Instantiates this leg with the four specified joints.
     * 
     * @param bodyJoint
     *          The body joint of this leg.
     * @param femurJoint
     *          The middle joint of this leg.
     * @param tibiaJoint
     *          The distal joint of this leg.
     * @param clawJoint
     * 			The claw joint of this leg.
     */
	  public Leg (final Component bodyJoint, final Component femurJoint,
	        final Component tibiaJoint, final Component clawJoint) {
	      this.bodyJoint = bodyJoint;
	      this.femurJoint = femurJoint;
	      this.tibiaJoint = tibiaJoint;
	      this.clawJoint = clawJoint;

      this.joints = Collections.unmodifiableList(Arrays.asList(this.bodyJoint,
          this.femurJoint, this.tibiaJoint, this.clawJoint));
    }   
	  
	  List<Component> joints() {
        return this.joints;
      }
	  Component clawJoint() {
		  return this.clawJoint;
    }   
	  Component tibiaJoint(){
    	return this.tibiaJoint;
    }
	  Component femurJoint() {
		  return this.femurJoint;
    }
	  Component bodyJoint() {
		  return this.bodyJoint;
    }
  }
  
  /** The default width of the created window. */
  public static final int DEFAULT_WINDOW_HEIGHT = 512;
  /** The default height of the created window. */
  public static final int DEFAULT_WINDOW_WIDTH = 512; 
  /** The color for components which are selected for rotation. */
  public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  /** The color for components which are not selected for rotation. */
  public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
  /** The initial position of the top level component in the scene. */
  public static final Point3D INITIAL_POSITION = new Point3D(0, 0.3, 0);
  
  //radius and heights of spider components
  /** The radius of the component that comprises the head. */
  public static final double HEAD_RADIUS = 0.35;
  /** The radius of first three leg joints */
  public static final double LEG_RADIUS = 0.06;
  /** The radius of the claws, or end joint */
  public static final double CLAW_RADIUS = 0.045;
  /** The height of the body. */
  public static final double BODY_RADIUS = 0.75;
  /** The radius of the eyes of the spider */
  public static final double EYE_RADIUS = 0.04;
  /** The radius for pincers and the top */
  public static final double PIN_RADIUS = 0.02;
  public static final double PIN_TOP_RADIUS = 0.014;
  /** The height of the claw joint on each of the legs. */
  public static final double CLAW_JOINT_HEIGHT = 0.4;
  /** The height of the distal joint on each of the legs. */
  public static final double TIBIA_JOINT_HEIGHT = 0.75; 
  /** The height of the middle joint on each of the legs. */
  public static final double FEMUR_JOINT_HEIGHT = 0.55;
  /** The height of the body joint on each of the legs. */
  public static final double BODY_JOINT_HEIGHT = 0.11;
  /** The height of the pincers on the head */
  public static final double PIN_HEIGHT = 0.16;
  
  /** The angle by which to rotate the joint on user request to rotate. */
  public static final double ROTATION_ANGLE = 1.0;  
  /** Randomly generated serial version UID. */
  private static final long serialVersionUID = -7060944143920496524L;

  /**
   * Runs the spider simulation in a single JFrame.
   * 
   * @param args
   *          This parameter is ignored.
   */
  public static void main(final String[] args) {
    new PA2().animator.start();
  }

  /**
   * The animator which controls the framerate at which the canvas is animated.
   */
  final FPSAnimator animator;
  /** The canvas on which we draw the scene. */
  private final GLCanvas canvas;
  /** The capabilities of the canvas. */
  private final GLCapabilities capabilities = new GLCapabilities(null);
  /** The legs on the body to be modeled. */
  private final Leg[] legs;
  /** The body to be modeled. */
  private final Component body;
  /** The head to be modeled. */
  private final Component head;
  /** The OpenGL utility object. */
  private final GLU glu = new GLU();
  /** The OpenGL utility toolkit object. */
  private final GLUT glut = new GLUT();

  /** The last x and y coordinates of the mouse press. */
  private int last_x = 0, last_y = 0;
  /** Whether the world is being rotated. */
  private boolean rotate_world = false;
  /** The axis around which to rotate the selected joints. */
  private Axis selectedAxis = Axis.X;
  /** The set of components which are currently selected for rotation. */
  private final Set<Component> selectedComponents = new HashSet<Component>(40);
  
  /**
   * The set of legs which have been selected for rotation.
   * 
   * Selecting a joint will only affect the joints in this set of selected
   * legs.
   **/
  private final Set<Leg> selectedLegs = new HashSet<Leg>(8);
  
  /** Whether the state of the model has been changed. */
  private boolean stateChanged = true;
  
  /**
   * The top level component in the scene which controls the positioning and
   * rotation of everything in the scene.
   */
  private final Component topLevelComponent;

  /** The quaternion which controls the rotation of the world. */
  private Quaternion viewing_quaternion = new Quaternion();
  
  /** The set of all components. */
  private final List<Component> components;
  
  //the front legs
  public static String TOP1_BODY_NAME = "top1 body";
  public static String TOP1_FEMUR_NAME = "top1 femur";
  public static String TOP1_TIBIA_NAME = "top1 tibia";
  public static String TOP1_CLAW_NAME = "top1 claw";
  public static String TOP2_BODY_NAME = "top2 body";
  public static String TOP2_FEMUR_NAME = "top2 femur";
  public static String TOP2_TIBIA_NAME = "top2 tibia";
  public static String TOP2_CLAW_NAME = "top2 claw";
  
  //the top middle legs
  public static String T_MIDDLE1_BODY_NAME = "tmiddle1 body";
  public static String T_MIDDLE1_FEMUR_NAME = "tmiddle1 femur";
  public static String T_MIDDLE1_TIBIA_NAME = "tmiddle1 tibia";
  public static String T_MIDDLE1_CLAW_NAME = "tmiddle1 claw";
  public static String T_MIDDLE2_BODY_NAME = "tmiddle2 body";
  public static String T_MIDDLE2_FEMUR_NAME = "tmiddle2 femur";
  public static String T_MIDDLE2_TIBIA_NAME = "tmiddle2 tibia";
  public static String T_MIDDLE2_CLAW_NAME = "tmiddle2 claw";
  
  //the hind middle legs
  public static String H_MIDDLE1_BODY_NAME = "hmiddle1 body";
  public static String H_MIDDLE1_FEMUR_NAME = "hmiddle1 femur";
  public static String H_MIDDLE1_TIBIA_NAME = "hmiddle1 tibia";
  public static String H_MIDDLE1_CLAW_NAME = "hmiddle1 claw";
  public static String H_MIDDLE2_BODY_NAME = "hmiddle2 body";
  public static String H_MIDDLE2_FEMUR_NAME = "hmiddle2 femur";
  public static String H_MIDDLE2_TIBIA_NAME = "hmiddle2 tibia";
  public static String H_MIDDLE2_CLAW_NAME = "hmiddle2 claw";
  
  //the hind legs
  public static String HIND1_BODY_NAME = "hind1 body";
  public static String HIND1_FEMUR_NAME = "hind1 femur";
  public static String HIND1_TIBIA_NAME = "hind1 tibia";
  public static String HIND1_CLAW_NAME = "hind1 claw";
  public static String HIND2_BODY_NAME = "hind2 body";
  public static String HIND2_FEMUR_NAME = "hind2 femur";
  public static String HIND2_TIBIA_NAME = "hind2 tibia";
  public static String HIND2_CLAW_NAME = "hind2 claw";
  
  //rest of the components
  public static String TOP_LEVEL_NAME = "top level";
  public static String HEAD_NAME = "head";
  public static String BODY_NAME = "body";
  public static String LEYE_NAME = "left eye";
  public static String REYE_NAME = "right eye";
  public static String PIN1_NAME = "pin1";
  public static String PIN2_NAME = "pin2";
  public static String PIN1_TOP_NAME = "pin1 top";
  public static String PIN2_TOP_NAME = "pin2 top";
		  

  /**
   * Initializes the necessary OpenGL objects and adds a canvas to this JFrame.
   */
  public PA2() {
    this.capabilities.setDoubleBuffered(true);

    this.canvas = new GLCanvas(this.capabilities);
    this.canvas.addGLEventListener(this);
    this.canvas.addMouseListener(this);
    this.canvas.addMouseMotionListener(this);
    this.canvas.addKeyListener(this);
    // this is true by default, but we just add this line to be explicit
    this.canvas.setAutoSwapBufferMode(true);
    this.getContentPane().add(this.canvas);

    // refresh the scene at 60 frames per second
    this.animator = new FPSAnimator(this.canvas, 60);

    this.setTitle("CS480/CS680 : Spider Simulator");
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    //eyes and pincers
    final Component leye = new Component(new Point3D(0.22,-0.1, 0.5), 
    		new Palm(EYE_RADIUS, this.glut), LEYE_NAME);
    final Component reye = new Component(new Point3D(-0.22,-0.1, 0.5),
    		new Palm(EYE_RADIUS, this.glut), REYE_NAME); 
    final Component pin1 = new Component(new Point3D(0.1, 0, 0.65),
    		new RoundedCylinder(PIN_RADIUS, PIN_HEIGHT, this.glut), PIN1_NAME);  
    final Component pin2 = new Component(new Point3D(-0.1, 0, 0.65),
    		new RoundedCylinder(PIN_RADIUS, PIN_HEIGHT, this.glut), PIN2_NAME);  
    final Component pintop1 = new Component (new Point3D(0,0,PIN_HEIGHT),
    		new Palm(PIN_TOP_RADIUS, this.glut), PIN1_TOP_NAME);
    final Component pintop2 = new Component (new Point3D(0,0,PIN_HEIGHT),
    		new Palm(PIN_TOP_RADIUS, this.glut), PIN2_TOP_NAME);
    
    //all the claw joints
    final Component claw1 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), TOP1_CLAW_NAME);
    final Component claw2 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), TOP2_CLAW_NAME);
    final Component claw3 = new Component(new Point3D(0, 0,
        TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
        CLAW_JOINT_HEIGHT, this.glut), T_MIDDLE1_CLAW_NAME);
    final Component claw4 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), T_MIDDLE2_CLAW_NAME);
    final Component claw5 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), H_MIDDLE1_CLAW_NAME);
    final Component claw6 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), H_MIDDLE2_CLAW_NAME);
    final Component claw7 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), HIND1_CLAW_NAME);
    final Component claw8 = new Component(new Point3D(0, 0,
            TIBIA_JOINT_HEIGHT), new RoundedCylinder(CLAW_RADIUS,
            CLAW_JOINT_HEIGHT, this.glut), HIND2_CLAW_NAME);
    
    // all the tibia joints
    final Component tibia1 = new Component(new Point3D(0, 0,
        FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        TIBIA_JOINT_HEIGHT, this.glut), TOP1_TIBIA_NAME);
    final Component tibia2 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), TOP2_TIBIA_NAME);
    final Component tibia3 = new Component(new Point3D(0, 0,
        FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        TIBIA_JOINT_HEIGHT, this.glut), T_MIDDLE1_TIBIA_NAME);
    final Component tibia4 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), T_MIDDLE2_TIBIA_NAME);
    final Component tibia5 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), H_MIDDLE1_TIBIA_NAME);
    final Component tibia6 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), H_MIDDLE2_TIBIA_NAME);
    final Component tibia7 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), HIND1_TIBIA_NAME);
    final Component tibia8 = new Component(new Point3D(0, 0,
            FEMUR_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            TIBIA_JOINT_HEIGHT, this.glut), HIND2_TIBIA_NAME);

    // all the femur joints
    final Component femur1 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        FEMUR_JOINT_HEIGHT, this.glut), TOP1_FEMUR_NAME);
        final Component femur2 = new Component(new Point3D(0, 0,
                 BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
                FEMUR_JOINT_HEIGHT, this.glut), TOP2_FEMUR_NAME);
        final Component femur3 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
            FEMUR_JOINT_HEIGHT, this.glut), T_MIDDLE1_FEMUR_NAME);
        final Component femur4 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        		FEMUR_JOINT_HEIGHT, this.glut), T_MIDDLE2_FEMUR_NAME);
        final Component femur5 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        		FEMUR_JOINT_HEIGHT, this.glut), H_MIDDLE1_FEMUR_NAME);
        final Component femur6 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        		FEMUR_JOINT_HEIGHT, this.glut), H_MIDDLE2_FEMUR_NAME);
        final Component femur7 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        		FEMUR_JOINT_HEIGHT, this.glut), HIND1_FEMUR_NAME);
        final Component femur8 = new Component(new Point3D(0, 0,
        		BODY_JOINT_HEIGHT), new RoundedCylinder(LEG_RADIUS,
        		FEMUR_JOINT_HEIGHT, this.glut), HIND2_FEMUR_NAME);
        
    //body joints displaced by various amounts to resemble a spider
    //how the spider looks like when we initially run the file
    final Component body1 = new Component(new Point3D(-0.4, 0, 1.3),
        new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        TOP1_BODY_NAME);
    final Component body2 = new Component(new Point3D(0.4, 0, 1.3),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            TOP2_BODY_NAME);
    final Component body3 = new Component(new Point3D(-0.6, 0, 0.95),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            T_MIDDLE1_BODY_NAME);
    final Component body4 = new Component(new Point3D(0.6, 0, 0.95),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            T_MIDDLE2_BODY_NAME);
    final Component body5 = new Component(new Point3D(-0.55, 0, 0.6),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            H_MIDDLE1_BODY_NAME);
    final Component body6 = new Component(new Point3D(0.55, 0, 0.6),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            H_MIDDLE2_BODY_NAME);
    final Component body7 = new Component(new Point3D(-0.4, 0, 0.2),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            HIND1_BODY_NAME);
    final Component body8 = new Component(new Point3D(0.4, 0, 0.2),
            new RoundedCylinder(LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
            HIND2_BODY_NAME);

    //put together the legs for selection by keyboard input
    this.legs = new Leg[] { new Leg(body1, femur1, tibia1, claw1),
        new Leg(body2, femur2, tibia2,claw2),
        new Leg(body3, femur3, tibia3, claw3),
        new Leg(body4, femur4, tibia4, claw4),
        new Leg(body5, femur5, tibia5, claw5),
        new Leg(body6, femur6, tibia6, claw6), 
        new Leg(body7, femur7, tibia7, claw7),
        new Leg(body8, femur8, tibia8,claw8) 
        };

    //the head, which models the head joint of the spider at the body as a sphere
    this.head = new Component(new Point3D(0, 0, 1.25), new Palm(
        HEAD_RADIUS, this.glut), HEAD_NAME);

    //the body, models the body of the spider as a sphere
    this.body = new Component(new Point3D(0, 0, 0),
       new Palm(BODY_RADIUS, this.glut), BODY_NAME);

    // the top level component which provides an initial position and rotation
    // to the scene (but does not cause anything to be drawn)
    this.topLevelComponent = new Component(INITIAL_POSITION, TOP_LEVEL_NAME);
    
    //adding child(ren)
    this.topLevelComponent.addChild(this.body);
    // the head is connected to the body
    this.body.addChild(this.head);
    //the eyes and pincers are on the head
    this.head.addChildren(leye, reye, pin1, pin2);
    pin1.addChild(pintop1);
    pin2.addChild(pintop2);
    // the legs are connected to the body...
    this.body.addChildren(body1, body2, body3, body4, body5, body6, body7, body8);
    body1.addChild(femur1);
    body2.addChild(femur2);
    body3.addChild(femur3);
    body4.addChild(femur4);
    body5.addChild(femur5);
    body6.addChild(femur6);
    body7.addChild(femur7);
    body8.addChild(femur8);
    femur1.addChild(tibia1);
    femur2.addChild(tibia2);
    femur3.addChild(tibia3);
    femur4.addChild(tibia4);
    femur5.addChild(tibia5);
    femur6.addChild(tibia6);
    femur7.addChild(tibia7);
    femur8.addChild(tibia8);
    tibia1.addChild(claw1);
    tibia2.addChild(claw2);
    tibia3.addChild(claw3);
    tibia4.addChild(claw4);
    tibia5.addChild(claw5);
    tibia6.addChild(claw6);
    tibia7.addChild(claw7);
    tibia8.addChild(claw8);

    //model front view of the spider
    this.topLevelComponent.rotate(Axis.Z, 180);
    this.topLevelComponent.rotate(Axis.X, 30);
    
    //model pincers
    pin1.rotate(Axis.Y, 15);
    pin2.rotate(Axis.Y, -15);
    
    //model eyes
    leye.rotate(Axis.X, -45);
    reye.rotate(Axis.X, -45);
    
    //model the body joints and set rotation limits
    body1.rotate(Axis.Y, -40);
    body1.setYPositiveExtent(-25);
    body1.setYNegativeExtent(-55);
    
    body2.rotate(Axis.Y, 40);
    body2.setYPositiveExtent(55);
    body2.setYNegativeExtent(25);
    
    body3.rotate(Axis.Y, -60);
    body3.setYPositiveExtent(-45);
    body3.setYNegativeExtent(-75);
    
    body4.rotate(Axis.Y, 60);
    body4.setYPositiveExtent(75);
    body4.setYNegativeExtent(45);
    
    body5.rotate(Axis.Y, -120);
    body5.setYPositiveExtent(-105);
    body5.setYNegativeExtent(-135);
    
    body6.rotate(Axis.Y, 120);
    body6.setYPositiveExtent(135);
    body6.setYNegativeExtent(105);
    
    body7.rotate(Axis.Y, -140);
    body7.setYPositiveExtent(-125);
    body7.setYNegativeExtent(-155);
    
    body8.rotate(Axis.Y, 140);
    body8.setYPositiveExtent(155);
    body8.setYNegativeExtent(125);
    
    for (final Component bodyJoint : Arrays.asList(body1, body2, body3, body4, body5, body6, body7, body8)) {
        bodyJoint.setXPositiveExtent(10);
        bodyJoint.setXNegativeExtent(-10);		   
        bodyJoint.setZPositiveExtent(10);
        bodyJoint.setZNegativeExtent(-10);
      }
    
    //model femur joints and set limits 
    femur1.rotate(Axis.X, 30);
    femur2.rotate(Axis.X, 30);
    femur3.rotate(Axis.X, 30);
    femur4.rotate(Axis.X, 30);
    femur5.rotate(Axis.X, 30);
    femur6.rotate(Axis.X, 30);
    femur7.rotate(Axis.X, 30);
    femur8.rotate(Axis.X, 30);
    
    for (final Component femurJoint : Arrays.asList(femur1, femur2,
          femur3, femur4, femur5, femur6, femur7, femur8)) {
          femurJoint.setXPositiveExtent(60);
          femurJoint.setXNegativeExtent(10);
          femurJoint.setYPositiveExtent(0);
          femurJoint.setYNegativeExtent(0);
          femurJoint.setZPositiveExtent(0);
          femurJoint.setZNegativeExtent(0);
        }
    
    //model the tibia joints and set limits
    tibia1.rotate(Axis.X, -80);
    tibia2.rotate(Axis.X, -80);
    tibia3.rotate(Axis.X, -80);
    tibia4.rotate(Axis.X, -80);
    tibia5.rotate(Axis.X, -80);
    tibia6.rotate(Axis.X, -80);
    tibia7.rotate(Axis.X, -80);
    tibia8.rotate(Axis.X, -80);
    
    for (final Component tibiaJoint : Arrays.asList(tibia1, tibia2,
          tibia3, tibia4, tibia5, tibia6, tibia7, tibia8)) {
          tibiaJoint.setXPositiveExtent(-60);
          tibiaJoint.setXNegativeExtent(-120);
          tibiaJoint.setYPositiveExtent(0);
          tibiaJoint.setYNegativeExtent(0);
          tibiaJoint.setZPositiveExtent(0);
          tibiaJoint.setZNegativeExtent(0);
        }
    
    //model claw joints and set limits
    claw1.rotate(Axis.X, -10);
    claw2.rotate(Axis.X, -10);
    claw3.rotate(Axis.X, -10);
    claw4.rotate(Axis.X, -10);
    claw5.rotate(Axis.X, -10);
    claw6.rotate(Axis.X, -10);
    claw7.rotate(Axis.X, -10);
    claw8.rotate(Axis.X, -10);
    
    for (final Component clawJoint : Arrays.asList(claw1, claw2,
    		claw3, claw4, claw5, claw6, claw7, claw8)) {
          clawJoint.setXPositiveExtent(-10);
          clawJoint.setXNegativeExtent(-30);
          clawJoint.setYPositiveExtent(0);
          clawJoint.setYNegativeExtent(0);
          clawJoint.setZPositiveExtent(0);
          clawJoint.setZNegativeExtent(0);
        }

    //set rotation limits for the head of the spider
    this.head.setXPositiveExtent(30);
    this.head.setXNegativeExtent(-45);
    this.head.setYPositiveExtent(5);
    this.head.setYNegativeExtent(-5);
    this.head.setZPositiveExtent(0);
    this.head.setZNegativeExtent(0); 

    //create the list of all the components for debugging purposes
    this.components = Arrays.asList(body1, femur1, tibia1, body2, femur2,
        tibia2, body3, femur3, tibia3, body4, femur4, tibia4, body5,
        femur5, tibia5,body6,femur6, tibia6, body7, femur7, tibia7,
        body8,femur8, tibia8, claw1, claw2, claw3, claw4, claw5, claw6, claw7, claw8,
        pin1, pin2, pintop1, pintop2, leye, reye, this.head, this.body);
  }

  /**
   * Redisplays the scene containing the hand model.
   * 
   * @param drawable
   *          The OpenGL drawable object with which to create OpenGL models.
   */
  public void display(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // clear the display
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // from here on affect the model view
    gl.glMatrixMode(GL2.GL_MODELVIEW);

    // start with the identity matrix initially
    gl.glLoadIdentity();

    // rotate the world by the appropriate rotation quaternion
    gl.glMultMatrixf(this.viewing_quaternion.toMatrix(), 0);

    // update the position of the components which need to be updated
    // TODO only need to update the selected and JUST deselected components
    if (this.stateChanged) {
      this.topLevelComponent.update(gl);
      this.stateChanged = false;
    }

    // redraw the components
    this.topLevelComponent.draw(gl);
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param drawable
   *          This parameter is ignored.
   * @param modeChanged
   *          This parameter is ignored.
   * @param deviceChanged
   *          This parameter is ignored.
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged) {
    // intentionally unimplemented
  }

  /**
   * Initializes the scene and model.
   * 
   * @param drawable
   *          {@inheritDoc}
   */
  public void init(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // perform any initialization needed by the hand model
    this.topLevelComponent.initialize(gl);

    // initially draw the scene
    this.topLevelComponent.update(gl);

    // set up for shaded display of the hand
    final float light0_position[] = { 1, 1, 1, 0 };
    final float light0_ambient_color[] = { 0.25f, 0.25f, 0.25f, 1 };
    final float light0_diffuse_color[] = { 1, 1, 1, 1 };

    gl.glPolygonMode(GL.GL_FRONT, GL2.GL_FILL);
    gl.glEnable(GL2.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glShadeModel(GL2.GL_SMOOTH);

    // set up the light source
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light0_ambient_color, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse_color, 0);

    // turn lighting and depth buffering on
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_NORMALIZE);
  }

  /**
   * Interprets key presses according to the following scheme:
   * 
   * up-arrow, down-arrow: increase/decrease rotation angle
   * 
   * @param key
   *          The key press event object.
   */
  public void keyPressed(final KeyEvent key) {
    switch (key.getKeyCode()) {
    case KeyEvent.VK_KP_UP:
    case KeyEvent.VK_UP:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    case KeyEvent.VK_KP_DOWN:
    case KeyEvent.VK_DOWN:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, -ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    default:
      break;
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param key
   *          This parameter is ignored.
   */
  public void keyReleased(final KeyEvent key) {
    // intentionally unimplemented
  }

  private final TestCases testCases = new TestCases();

  private void setModelState(final Map<String, Angled> state) {
    this.body.setAngles(state.get(BODY_NAME));
    this.head.setAngles(state.get(HEAD_NAME));
    this.legs[0].bodyJoint().setAngles(state.get(TOP1_BODY_NAME));
    this.legs[0].femurJoint().setAngles(state.get(TOP1_FEMUR_NAME));
    this.legs[0].tibiaJoint().setAngles(state.get(TOP1_TIBIA_NAME));
    this.legs[0].clawJoint().setAngles(state.get(TOP1_CLAW_NAME));
    
    this.legs[1].bodyJoint().setAngles(state.get(TOP2_BODY_NAME));
    this.legs[1].femurJoint().setAngles(state.get(TOP2_FEMUR_NAME));
    this.legs[1].tibiaJoint().setAngles(state.get(TOP2_TIBIA_NAME));
    this.legs[1].clawJoint().setAngles(state.get(TOP2_CLAW_NAME));
    
    this.legs[2].bodyJoint().setAngles(state.get(T_MIDDLE1_BODY_NAME));
    this.legs[2].femurJoint().setAngles(state.get(T_MIDDLE1_FEMUR_NAME));
    this.legs[2].tibiaJoint().setAngles(state.get(T_MIDDLE1_TIBIA_NAME));
    this.legs[2].clawJoint().setAngles(state.get(T_MIDDLE1_CLAW_NAME));
    
    this.legs[3].bodyJoint().setAngles(state.get(T_MIDDLE2_BODY_NAME));
    this.legs[3].femurJoint().setAngles(state.get(T_MIDDLE2_FEMUR_NAME));
    this.legs[3].tibiaJoint().setAngles(state.get(T_MIDDLE2_TIBIA_NAME));
    this.legs[3].clawJoint().setAngles(state.get(T_MIDDLE2_CLAW_NAME));
    
    this.legs[4].bodyJoint().setAngles(state.get(H_MIDDLE1_BODY_NAME));
    this.legs[4].femurJoint().setAngles(state.get(H_MIDDLE1_FEMUR_NAME));
    this.legs[4].tibiaJoint().setAngles(state.get(H_MIDDLE1_TIBIA_NAME));
    this.legs[4].clawJoint().setAngles(state.get(H_MIDDLE1_CLAW_NAME));
    
    this.legs[5].bodyJoint().setAngles(state.get(H_MIDDLE2_BODY_NAME));
    this.legs[5].femurJoint().setAngles(state.get(H_MIDDLE2_FEMUR_NAME));
    this.legs[5].tibiaJoint().setAngles(state.get(H_MIDDLE2_TIBIA_NAME));
    this.legs[5].clawJoint().setAngles(state.get(H_MIDDLE2_CLAW_NAME));
    
    this.legs[6].bodyJoint().setAngles(state.get(HIND1_BODY_NAME));
    this.legs[6].femurJoint().setAngles(state.get(HIND1_FEMUR_NAME));
    this.legs[6].tibiaJoint().setAngles(state.get(HIND1_TIBIA_NAME));
    this.legs[6].clawJoint().setAngles(state.get(HIND1_CLAW_NAME));
    
    this.legs[7].bodyJoint().setAngles(state.get(HIND2_BODY_NAME));
    this.legs[7].femurJoint().setAngles(state.get(HIND2_FEMUR_NAME));
    this.legs[7].tibiaJoint().setAngles(state.get(HIND2_TIBIA_NAME));
    this.legs[7].clawJoint().setAngles(state.get(HIND2_CLAW_NAME));
    
    
    this.stateChanged = true;
  }

  /**
   * Interprets typed keys according to the following scheme:
   * 
   * 1 : toggle the front right leg for rotation
   * 
   * 2 : toggle the front left leg for rotation
   * 
   * 3 : toggle the front middle right leg for rotation
   * 
   * 4 : toggle the front middle left leg for rotation
   * 
   * 5 : toggle the back middle right leg for rotation
   * 
   * 6 : toggle the back middle left leg for rotation
   * 
   * 7 : toggle the hind right leg for rotation
   * 
   * 8 : toggle the hind left leg for rotation
   * 
   * A,a : toggle the body joint for rotation
   * 
   * B,b : toggle the femur or middle joint for rotation
   * 
   * C,c : toggle the tibia or distal joint for rotation
   * 
   * D,d : toggle the claw joint for rotation
   * 
   * H,h : toggle the head for rotation
   * 
   * X,x : use the X axis rotation at the active joint(s)
   * 
   * Y,y : use the Y axis rotation at the active joint(s)
   * 
   * Z,z : use the Z axis rotation at the active joint(s)
   * 
   * P,p : cycle through test poses
   * 
   * R,r : resets the view to the initial rotation
   * 
   * K,k : prints the angles of the legs for debugging purposes
   * 
   * Q,q, Esc : exits the program
   * 
   */
  public void keyTyped(final KeyEvent key) {
    switch (key.getKeyChar()) {
    case 'Q':
    case 'q':
    case KeyEvent.VK_ESCAPE:
      new Thread() {
        @Override
        public void run() {
          PA2.this.animator.stop();
        }
      }.start();
      System.exit(0);
      break;

    // print the angles of the components
    case 'K':
    case 'k':
      printJoints();
      break;

    // set the state of the spider to the next test case
    case 'P':
    case 'p':
      this.setModelState(this.testCases.next());
      break;

    // set the viewing quaternion to 0 rotation
    // reset spider to initial position
    case 'R':
    case 'r':
      this.viewing_quaternion.reset();
      this.setModelState(this.testCases.stop());
      break;

    // Toggle which leg(s) are affected by the current rotation
    case '1':
      toggleSelection(this.legs[0]);
      break;
    case '2':
      toggleSelection(this.legs[1]);
      break;
    case '3':
      toggleSelection(this.legs[2]);
      break;
    case '4':
      toggleSelection(this.legs[3]);
      break;
    case '5':
      toggleSelection(this.legs[4]);
      break;
    case '6':
        toggleSelection(this.legs[5]);
        break;
    case '7':
        toggleSelection(this.legs[6]);
        break;
    case '8':
        toggleSelection(this.legs[7]);
        break;

    // toggle which joints are affected by the current rotation
    case 'A':
    case 'a':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.bodyJoint());
      }
      break;
    case 'B':
    case 'b':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.femurJoint());
      }
      break;
      
    case 'C':
    case 'c':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.tibiaJoint());
      }
      break;
      
    case 'D':
    case 'd':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.clawJoint());
      }
      break;

    case 'H':
    case 'h':
      toggleSelection(this.head);
      break;

    // change the axis of rotation at current active joint
    case 'X':
    case 'x':
      this.selectedAxis = Axis.X;
      break;
    case 'Y':
    case 'y':
      this.selectedAxis = Axis.Y;
      break;
    case 'Z':
    case 'z':
      this.selectedAxis = Axis.Z;
      break;
    default:
      break;
    }
  }

  /**
   * Prints the joints on the System.out print stream.
   */
  private void printJoints() {
    this.printJoints(System.out);
  }

  /**
   * Prints the joints on the specified PrintStream.
   * 
   * @param printStream
   *          The stream on which to print each of the components.
   */
  private void printJoints(final PrintStream printStream) {
    for (final Component component : this.components) {
      printStream.println(component);
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseClicked(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Updates the rotation quaternion as the mouse is dragged.
   * 
   * @param mouse
   *          The mouse drag event object.
   */
  public void mouseDragged(final MouseEvent mouse) {
	if (this.rotate_world) {
		// get the current position of the mouse
		final int x = mouse.getX();
		final int y = mouse.getY();
	
		// get the change in position from the previous one
		final int dx = x - this.last_x;
		final int dy = y - this.last_y;
	
		// create a unit vector in the direction of the vector (dy, dx, 0)
		final double magnitude = Math.sqrt(dx * dx + dy * dy);
		final float[] axis = magnitude == 0 ? new float[]{1,0,0}: // avoid dividing by 0
			new float[] { (float) (dy / magnitude),(float) (dx / magnitude), 0 };
	
		// calculate appropriate quaternion
		final float viewing_delta = 3.1415927f / 180.0f;
		final float s = (float) Math.sin(0.5f * viewing_delta);
		final float c = (float) Math.cos(0.5f * viewing_delta);
		final Quaternion Q = new Quaternion(c, s * axis[0], s * axis[1], s
				* axis[2]);
		this.viewing_quaternion = Q.multiply(this.viewing_quaternion);
	
		// normalize to counteract acccumulating round-off error
		this.viewing_quaternion.normalize();
	
		// save x, y as last x, y
		this.last_x = x;
		this.last_y = y;
	}
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseEntered(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseExited(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseMoved(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Starts rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse press event object.
   */
  public void mousePressed(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.last_x = mouse.getX();
      this.last_y = mouse.getY();
      this.rotate_world = true;
    }
  }

  /**
   * Stops rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse release event object.
   */
  public void mouseReleased(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.rotate_world = false;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @param drawable
   *          {@inheritDoc}
   * @param x
   *          {@inheritDoc}
   * @param y
   *          {@inheritDoc}
   * @param width
   *          {@inheritDoc}
   * @param height
   *          {@inheritDoc}
   */
  public void reshape(final GLAutoDrawable drawable, final int x, final int y,
      final int width, final int height) {
    final GL2 gl = (GL2)drawable.getGL();

    // prevent division by zero by ensuring window has height 1 at least
    final int newHeight = Math.max(1, height);

    // compute the aspect ratio
    final double ratio = (double) width / newHeight;

    // reset the projection coordinate system before modifying it
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();

    // set the viewport to be the entire window
    gl.glViewport(0, 0, width, newHeight);

    // set the clipping volume
    this.glu.gluPerspective(25, ratio, 0.1, 100);

    // camera positioned at (0,0,6), look at point (0,0,0), up vector (0,1,0)
    this.glu.gluLookAt(0, 0, 12, 0, 0, 0, 0, 1, 0);

    // switch back to model coordinate system
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

  private void toggleSelection(final Component component) {
    if (this.selectedComponents.contains(component)) {
      this.selectedComponents.remove(component);
      component.setColor(INACTIVE_COLOR);
    } else {
      this.selectedComponents.add(component);
      component.setColor(ACTIVE_COLOR);
    }
    this.stateChanged = true;
  }

  private void toggleSelection(final Leg leg) {
    if (this.selectedLegs.contains(leg)) {
      this.selectedLegs.remove(leg);
      this.selectedComponents.removeAll(leg.joints());
      for (final Component joint : leg.joints()) {
        joint.setColor(INACTIVE_COLOR);
      }
    } else {
      this.selectedLegs.add(leg);
    }
    this.stateChanged = true;
  }

@Override
public void dispose(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub
	
}
}
