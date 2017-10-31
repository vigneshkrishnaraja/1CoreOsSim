/*******************************************************************************
/
/      filename:  Event.java
/
/   description:  Implementation of all the characteristics of events and their
/                 definitions and declarations.
/
/        author:  Krishnaraja, Vignesh
/      login id:  FA_17_CPS356_14
/
/         class:  CPS 356
/    instructor:  Perugini
/    assignment:  Midterm Project
/
/      assigned:  October 12, 2017
/           due:  October 26, 2017
/
/******************************************************************************/

public class Event implements Comparable{
   public String event;
   public int arrival_time;
   public int job;
   public int memory;
   public int runtime;
   public int eventA_arrival_time;
   public int eventE_arrival_time;
   public int complete_time;
   public int CPU_arrival_time;
   public int CPU_start_time;
   public int burst_time_left;
   public int io_start_time;
   public int io_burst_time;
   public int io_complete_time;
   public Event(){
      this.event = "Empty";
      this.arrival_time = 0;
      this.job = 0;
      this.memory = 0;
      this.runtime = 0;
      this.eventA_arrival_time = 0;
      this.eventE_arrival_time = 0;
      this.complete_time = 0;
      this.CPU_start_time = 0;
      this.CPU_arrival_time = 0;
      this.burst_time_left = 0;
      this.io_start_time = 0;
      this.io_burst_time = 0;
      this.io_complete_time = 0; 
   }
   
   @Override
   public int compareTo(Object o) {
      int complete=((Event)o).io_complete_time;
        return this.io_complete_time-complete;
   }

}