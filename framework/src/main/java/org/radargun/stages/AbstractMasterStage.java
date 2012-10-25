package org.radargun.stages;

import org.apache.log4j.Logger;
import org.radargun.state.MasterState;
import org.radargun.utils.ClassLoadHelper;
import org.radargun.MasterStage;

/**
 * Support class for MasterStages.
 *
 * @author Mircea.Markus@jboss.com
 */
public abstract class AbstractMasterStage implements MasterStage {

   private static final String PREV_PRODUCT = "AbstractMasterStage.previousProduct";
   private static final String CLASS_LOADER = "AbstractMasterStage.classLoader";
   protected Logger log = Logger.getLogger(getClass());
      
   protected MasterState masterState;
   private ClassLoadHelper classLoadHelper;

   public void init(MasterState masterState) {
      this.masterState = masterState;
      classLoadHelper = new ClassLoadHelper(true, this.getClass(),
            masterState.nameOfTheCurrentBenchmark(), masterState, PREV_PRODUCT, CLASS_LOADER);
   }

   public AbstractMasterStage clone() {
      try {
         return (AbstractMasterStage) super.clone();
      } catch (CloneNotSupportedException e) {
         throw new IllegalStateException(e);
      }
   }

   @Override
   public String toString() {
      return "{An instance of " + getClass().getSimpleName() + "}";
   }
   
   protected Object createInstance(String classFqn) throws Exception {
      return classLoadHelper.createInstance(classFqn);
   }
}