/**
 * 
 */


import java.util.HashMap;
import java.util.Map;

/**
 * Test cases for the spider rotations. Repurposed code. 
 * 
 * Original code by:
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2011
 */
public class TestCases extends CyclicIterator<Map<String, Angled>> {

  Map<String, Angled> stop() {
    return this.stop;
  }

  private final Map<String, Angled> stop;

  @SuppressWarnings("unchecked")
  TestCases() {
    this.stop = new HashMap<String, Angled>();
    final Map<String, Angled> right = new HashMap<String, Angled>();
    final Map<String, Angled> left = new HashMap<String, Angled>();
    final Map<String, Angled> jump = new HashMap<String, Angled>();
    final Map<String, Angled> bunch = new HashMap<String, Angled>();
    final Map<String, Angled> walk = new HashMap<String, Angled>();

    super.add(stop, right, left, jump, bunch, walk);

    // the body, eyes, and pincers don't change
    stop.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.LEYE_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.REYE_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.PIN1_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.PIN2_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.PIN1_TOP_NAME, new BaseAngled(0, 0, 0));
    
    stop.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    left.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    jump.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    bunch.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.PIN2_TOP_NAME, new BaseAngled(0, 0, 0));
    
    // the stop test case is the same as initial model view
    stop.put(PA2.TOP1_BODY_NAME, new BaseAngled(0, -40, 0));
    stop.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-80, -0, 0));
    stop.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    stop.put(PA2.TOP2_BODY_NAME, new BaseAngled(0, 40, 0));
    stop.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    stop.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(0, -60, 0));    
    stop.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    stop.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(0, 60, 0));
    stop.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    stop.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(0, -120, 0));
    stop.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));    
    stop.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(0, 120, 0));
    stop.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    stop.put(PA2.HIND1_BODY_NAME, new BaseAngled(0,-140, 0));
    stop.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    stop.put(PA2.HIND2_BODY_NAME, new BaseAngled(0, 140, 0));
    stop.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    stop.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    stop.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    

    // the right test case rotates only the right legs
    right.put(PA2.TOP1_BODY_NAME, new BaseAngled(0, -30, 10));
    right.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(50, 0, 0));
    right.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-95, -0, 0));
    right.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-20, 0, 0));
    right.put(PA2.TOP2_BODY_NAME, new BaseAngled(0, 40, 0));
    right.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    right.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    right.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    right.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(0, -50, 10));    
    right.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(50, 0, 0));
    right.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-95, 0, 0));
    right.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-20, 0, 0));  
    right.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(0, 60, 0));
    right.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    right.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    right.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    right.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(0, -110, 10));
    right.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(50, 0, 0));
    right.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-95, 0, 0));
    right.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-20, 0, 0));    
    right.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(0, 120, 0));
    right.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    right.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    right.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    right.put(PA2.HIND1_BODY_NAME, new BaseAngled(0,-130, 10));
    right.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(50, 0, 0));
    right.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-95, 0, 0));
    right.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-20, 0, 0));
    right.put(PA2.HIND2_BODY_NAME, new BaseAngled(0, 140, 0));
    right.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(30, 0, 0));
    right.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    right.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-10, 0, 0));



    // the left test case rotates only the left legs
    left.put(PA2.TOP1_BODY_NAME, new BaseAngled(0, -40, 0));
    left.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    left.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-80, -0, 0));
    left.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    left.put(PA2.TOP2_BODY_NAME, new BaseAngled(0, 50, 10));
    left.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(50, 0, 0));
    left.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    left.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    left.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(0, -60, 0));    
    left.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    left.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    left.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    left.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(0, 70, 10));
    left.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(50, 0, 0));
    left.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    left.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    left.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(0, -120, 0));
    left.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    left.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    left.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));    
    left.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(0, 130, 10));
    left.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(50, 0, 0));
    left.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    left.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    left.put(PA2.HIND1_BODY_NAME, new BaseAngled(0,-140, 0));
    left.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    left.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-80, 0, 0));
    left.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    left.put(PA2.HIND2_BODY_NAME, new BaseAngled(0, 150, 10));
    left.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(50, 0, 0));
    left.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    left.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-10, 0, 0));

    
    

    // the jump test case models the spider into a jumping position
    jump.put(PA2.TOP1_BODY_NAME, new BaseAngled(0, -40, -10));
    jump.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(10, 0, 0));
    jump.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-65, 0, 0));
    jump.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    jump.put(PA2.TOP2_BODY_NAME, new BaseAngled(0, 40, 10));
    jump.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(10, 0, 0));
    jump.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-65, 0, 0));
    jump.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    jump.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(0, -60, -10));    
    jump.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(10, 0, 0));
    jump.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-65, 0, 0));
    jump.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    jump.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(0, 60, 10));
    jump.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(10, 0, 0));
    jump.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-65, 0, 0));
    jump.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    jump.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(0, -120, 0));
    jump.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(60, 0, 0));
    jump.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    jump.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-30, 0, 0));    
    jump.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(0, 120, 0));
    jump.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    jump.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    jump.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-30, 0, 0));
    jump.put(PA2.HIND1_BODY_NAME, new BaseAngled(0,-140, 0));
    jump.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(60, 0, 0));
    jump.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    jump.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-30, 0, 0));
    jump.put(PA2.HIND2_BODY_NAME, new BaseAngled(0, 140, 0));
    jump.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    jump.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-110, 0, 0));
    jump.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-30, 0, 0));


    // the bunch test case pulls all the legs inward
    bunch.put(PA2.TOP1_BODY_NAME, new BaseAngled(10, -40, 0));
    bunch.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-120, -0, 0));
    bunch.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-30, 0, 0));
    bunch.put(PA2.TOP2_BODY_NAME, new BaseAngled(10, 40, 0));
    bunch.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-30, 0, 0));  
    bunch.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(10, -60, 0));    
    bunch.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-30, 0, 0));  
    bunch.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(10, 60, 0));
    bunch.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-30, 0, 0));
    bunch.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(10, -120, 0));
    bunch.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-30, 0, 0));    
    bunch.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(10, 120, 0));
    bunch.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-30, 0, 0));
    bunch.put(PA2.HIND1_BODY_NAME, new BaseAngled(10,-140, 0));
    bunch.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    bunch.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-20, 0, 0));
    bunch.put(PA2.HIND2_BODY_NAME, new BaseAngled(10, 140, 0));
    bunch.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    bunch.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    bunch.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-30, 0, 0));

    // the walk test case models the spider in a walk position
    walk.put(PA2.TOP1_BODY_NAME, new BaseAngled(0, -55, 0));
    walk.put(PA2.TOP1_FEMUR_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.TOP1_TIBIA_NAME, new BaseAngled(-90, -0, 0));
    walk.put(PA2.TOP1_CLAW_NAME, new BaseAngled(-10, 0, 0));
    walk.put(PA2.TOP2_BODY_NAME, new BaseAngled(0, 25, 0));
    walk.put(PA2.TOP2_FEMUR_NAME, new BaseAngled(0, 0, 0));
    walk.put(PA2.TOP2_TIBIA_NAME, new BaseAngled(-60, 0, 0));
    walk.put(PA2.TOP2_CLAW_NAME, new BaseAngled(-30, 0, 0));  
    walk.put(PA2.T_MIDDLE1_BODY_NAME, new BaseAngled(0, -50, 0));    
    walk.put(PA2.T_MIDDLE1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    walk.put(PA2.T_MIDDLE1_TIBIA_NAME, new BaseAngled(-90, 0, 0));
    walk.put(PA2.T_MIDDLE1_CLAW_NAME, new BaseAngled(-10, 0, 0));  
    walk.put(PA2.T_MIDDLE2_BODY_NAME, new BaseAngled(0, 75, 0));
    walk.put(PA2.T_MIDDLE2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    walk.put(PA2.T_MIDDLE2_TIBIA_NAME, new BaseAngled(-120, 0, 0));
    walk.put(PA2.T_MIDDLE2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    walk.put(PA2.H_MIDDLE1_BODY_NAME, new BaseAngled(0, -135, 0));
    walk.put(PA2.H_MIDDLE1_FEMUR_NAME, new BaseAngled(20, 0, 0));
    walk.put(PA2.H_MIDDLE1_TIBIA_NAME, new BaseAngled(-60, 0, 0));
    walk.put(PA2.H_MIDDLE1_CLAW_NAME, new BaseAngled(-30, 0, 0));    
    walk.put(PA2.H_MIDDLE2_BODY_NAME, new BaseAngled(0, 120, 0));
    walk.put(PA2.H_MIDDLE2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    walk.put(PA2.H_MIDDLE2_TIBIA_NAME, new BaseAngled(-90, 0, 0));
    walk.put(PA2.H_MIDDLE2_CLAW_NAME, new BaseAngled(-30, 0, 0));
    walk.put(PA2.HIND1_BODY_NAME, new BaseAngled(0,-140, 0));
    walk.put(PA2.HIND1_FEMUR_NAME, new BaseAngled(30, 0, 0));
    walk.put(PA2.HIND1_TIBIA_NAME, new BaseAngled(-60, 0, 0));
    walk.put(PA2.HIND1_CLAW_NAME, new BaseAngled(-20, 0, 0));
    walk.put(PA2.HIND2_BODY_NAME, new BaseAngled(0, 155, 0));
    walk.put(PA2.HIND2_FEMUR_NAME, new BaseAngled(60, 0, 0));
    walk.put(PA2.HIND2_TIBIA_NAME, new BaseAngled(-60, 0, 0));
    walk.put(PA2.HIND2_CLAW_NAME, new BaseAngled(-10, 0, 0));
    
    //head rotations
    stop.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    right.put(PA2.HEAD_NAME, new BaseAngled(10, 0, 0));
    left.put(PA2.HEAD_NAME, new BaseAngled(-10, 0, 0));
    jump.put(PA2.HEAD_NAME, new BaseAngled(30, 0, 0));
    bunch.put(PA2.HEAD_NAME, new BaseAngled(30, 0, 0));
    walk.put(PA2.HEAD_NAME, new BaseAngled(20, 0, 0));
  }
}
