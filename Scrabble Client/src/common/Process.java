/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

/**
 * 
 * @author Romain Foncier <ro.foncier at gmail.com>
 */
public class Process {
    private String object;
    private String task;
    private String status;
    
    public Process(String object, String task, String status) {
        this.object = object;
        this.task = task;
        this.status = status;
    }
    
    public String getObject() {
        return this.object;
    }
    
     public String getTask() {
        return this.task;
    }
     
      public String getStatus() {
        return this.status;
    }
      
      public void setObject(String object) {
          this.object = object;
      }
      
      public void setTask(String task) {
          this.task = task;
      }
      
      public void setStatus(String status) {
          this.status = status;
      }
      
      public String formatProcess() {
          return this.object+"_"+this.task+"_"+this.status;
      }
}
