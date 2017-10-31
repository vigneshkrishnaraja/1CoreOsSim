/*******************************************************************************
/
/      filename:  Display.java
/
/   description:  Displays the Contents of the 5 Queues involved - 
/                 Job Scheduling Queue, Ready Queue Level 1, Ready Queue Level 2
/                 , I/O Wait Queue and the Finished Queue. It also displays
/                 the contents of CPU.
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

import java.util.*;

public class Display {
   public Display(Queue<Event> jobSchedule, Queue<Event> firstLevel,
           Queue<Event> secondLevel, Queue<Event> finished, Event cpu, 
           int time, ArrayList<Event> ioQueue){
      System.out.println("\n**********************************************"
              + "**************");
      System.out.println("\nThe status of the simulator at time "+time+".");
      printJobSchedule(jobSchedule);
      printFirstReadyQueue(firstLevel);
      printSecondReadyQueue(secondLevel);
      printIOQueue(ioQueue);
      printCPU(cpu);
      printFinishedQueue(finished);
    }
    
   public static void printJobSchedule(Queue<Event> queue){
      System.out.println("\nThe contents of the JOB SCHEDULING QUEUE"
              + "\n----------------------------------------\n");
      if(queue.isEmpty()){
         System.out.println("The Job Scheduling Queue is empty.");
      }
      else{
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time\n"
                 + "-----  ---------  ---------  --------\n");
         Iterator<Event> iter = queue.iterator();
         while(iter.hasNext()){
            Event temp = iter.next();
            System.out.printf("%5d%11d%11d%10d\n",temp.job,
                    temp.arrival_time,temp.memory,temp.runtime);
         }
      }
   }
   
   public static void printFirstReadyQueue(Queue<Event> queue){
      System.out.println("\n\nThe contents of the FIRST LEVEL READY QUEUE"
              + "\n-------------------------------------------\n");
      if(queue.isEmpty()){
         System.out.println("The First Level Ready Queue is empty.");
      }
      else{
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time\n"
                 + "-----  ---------  ---------  --------\n");
         Iterator<Event> iter = queue.iterator();
         while(iter.hasNext()){
            Event temp = iter.next();
            System.out.printf("%5d%11d%11d%10d\n",temp.job,
                    temp.arrival_time,temp.memory,temp.runtime);
         }
      }
   }
   
   public static void printSecondReadyQueue(Queue<Event> queue){
      System.out.println("\n\nThe contents of the SECOND LEVEL READY QUEUE"
              + "\n--------------------------------------------\n");
      if(queue.isEmpty()){
         System.out.println("The Second Level Ready Queue is empty.");
      }
      else{
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time\n"
                 + "-----  ---------  ---------  --------\n");
         Iterator<Event> iter = queue.iterator();
         while(iter.hasNext()){
            Event temp = iter.next();
            System.out.printf("%5d%11d%11d%10d\n",temp.job,
                    temp.arrival_time,temp.memory,temp.runtime);
         }
      }
   }
   
   public static void printIOQueue(ArrayList<Event> queue){
      System.out.println("\n\nThe contents of the I/O WAIT QUEUE"
              + "\n----------------------------------\n");
      if(queue.isEmpty()){
         System.out.println("The I/O Wait Queue is empty.");
      }
      else{
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time  "
                 + "IO Start Time  IO Burst  Comp. Time\n-----  ---------  "
                 + "---------  --------  -------------  --------  "
                 + "----------\n");
         Iterator<Event> iter = queue.iterator();
         while(iter.hasNext()){
            Event temp = iter.next();
            System.out.printf("%5d%11d%11d%10d%15d%10d%12d\n",temp.job,
                    temp.arrival_time,temp.memory,
                    temp.runtime,temp.io_start_time,
                    temp.io_burst_time,temp.io_complete_time);
         }
      }
   }
   
   public static void printCPU(Event e){
      System.out.println("\n\nThe CPU  Start Time  CPU burst time left"
              + "\n-------  ----------  -------------------\n");
      if(e.event.equals("Empty"))
         System.out.println("The CPU is idle.");
      else
         System.out.printf("%7d%12d%21d\n",
                 e.job,e.CPU_start_time,e.burst_time_left);
   }
   
   public static void printFinishedQueue(Queue<Event> queue){
      System.out.println("\n\nThe contents of the FINISHED LIST"
              + "\n---------------------------------\n");
      if(queue.isEmpty()){
         System.out.println("The Finished List is empty.");
      }
      else{
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time  Start Time"
                 + "  Com. Time\n"
                 + "-----  ---------  ---------  --------  ----------"
                 + "  ---------\n");
         Iterator<Event> iter = queue.iterator();
         while(iter.hasNext()){
            Event temp = iter.next();
            System.out.printf("%5d%11d%11d%10d%12d%11d\n",temp.job,
                    temp.arrival_time,temp.memory,temp.runtime,
                    temp.CPU_start_time,temp.complete_time);
         }
      }
   }
}