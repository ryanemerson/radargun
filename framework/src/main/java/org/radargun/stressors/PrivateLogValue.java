package org.radargun.stressors;

import java.io.Serializable;

/**
 * @author Radim Vansa &lt;rvansa@redhat.com&gt;
 */
public class PrivateLogValue implements Serializable {
   private final int threadId;
   private final long[] operationIds;
   public PrivateLogValue(int threadId, long operationId) {
      this.threadId = threadId;
      operationIds = new long[] { operationId };
   }

   private PrivateLogValue(int threadId, long[] operationIds) {
      this.threadId = threadId;
      this.operationIds = operationIds;
   }

   public PrivateLogValue with(long operationId) {
      long[] newOperationIds = new long[operationIds.length + 1];
      System.arraycopy(operationIds, 0, newOperationIds, 0, operationIds.length);
      newOperationIds[operationIds.length] = operationId;
      return new PrivateLogValue(threadId, newOperationIds);
   }

   public PrivateLogValue shift(int checkedValues, long operationId) {
      long[] newOperationIds = new long[operationIds.length - checkedValues + 1];
      System.arraycopy(operationIds, checkedValues, newOperationIds, 0, operationIds.length - checkedValues);
      newOperationIds[operationIds.length - checkedValues] = operationId;
      return new PrivateLogValue(threadId, newOperationIds);
   }

   public int size() {
      return operationIds.length;
   }

   public long getOperationId(int i) {
      return operationIds[i];
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("[").append(threadId).append(" #").append(operationIds.length).append(": ");
      for (long op : operationIds) {
         sb.append(op).append(", ");
      }
      return sb.append("]").toString();
   }

   public int getThreadId() {
      return threadId;
   }
}
