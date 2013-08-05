package obix.contracts;

import obix.*;

/**
 * Alarm
 *
 * @author    obix.tools.Obixc
 * @creation  24 May 06
 * @version   $Revision$ $Date$
 */
public interface Alarm
  extends IObj
{
  public static final String ALARM_CONTRACT = "obix:Alarm";
  
  public Ref source();

  public Abstime timestamp();

}
