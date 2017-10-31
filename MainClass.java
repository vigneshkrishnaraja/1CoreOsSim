/*******************************************************************************
/
/      filename:  MainClass.java
/
/   description:  Simulates the Job Scheduling process using the other 3 files.
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

import java.util.Scanner;
import java.util.*;

public class MainClass {
   static final Scanner stdin = new Scanner(System.in);
   static String inputLine = "";
   static final int Max_Memory = 512;
   static int Memory_Available = 512;
   static int sysTime = 0;
   static int eventTime = 0;
   static final int quantumA = 100;
   static final int quantumE = 300;
   static Queue<Event> jobScheduleQueue = new Queue<Event>();
   static Queue<Event> readyQueue2 = new Queue<Event>();
   static Queue<Event> readyQueue1 = new Queue<Event>();
   static Queue<Event> finishedQueue = new Queue<Event>();
   static ArrayList<Event> ioWaitQueue = new ArrayList<Event>();
   static Event CPU = new Event();
   static Event temp = new Event();
   static int display = -1;
   
   //getting Input from stdin and initializing the appropriate events
   public static boolean getInput(){
      if(stdin.hasNextLine() && !((inputLine = stdin.nextLine()).equals(""))){
         Scanner nextIn = new Scanner(inputLine);
         String tempevent = nextIn.next();
         if(tempevent.equals("A")){
            temp.event = "A";
            temp.arrival_time = nextIn.nextInt();
            temp.job = nextIn.nextInt();
            temp.memory = nextIn.nextInt();
            temp.runtime = nextIn.nextInt();
            temp.burst_time_left = temp.runtime;
            eventTime = temp.arrival_time;
            return true;
         }
         else if(tempevent.equals("I")){
            temp.event = "I";
            temp.arrival_time = nextIn.nextInt();
            temp.io_burst_time = nextIn.nextInt();
            eventTime = temp.arrival_time;
            moveToIOQueue();
            return true;
         }
         else if(tempevent.equals("D")){
            display = nextIn.nextInt();
            eventTime = display;
            return true;
         }
      }
      inputLine = "Empty";
      return false;
   }
   
   
   //puts the job in the job schedule queue
   public static void JobSchedule(){
      if(temp.event.equals("A") && sysTime == eventTime){
         System.out.println("Event: "+temp.event+"   Time: "+sysTime);
         if(temp.memory <= Max_Memory){
            jobScheduleQueue.enqueue(temp);
            temp = new Event();
            ReadyQueue1();
         }
         else
            System.out.println("This job exceeds the system's main "
                    + "memory capacity.");
      } 
      else
         ReadyQueue1();
   }
   
   //gets the job from job schedule queue until memory is full
   public static void ReadyQueue1(){
      while(!jobScheduleQueue.isEmpty() && Memory_Available > 0){ 
         if((Memory_Available - (jobScheduleQueue.peek().memory)) >=  0){
            Event temp1 = jobScheduleQueue.dequeue();
            temp1.eventA_arrival_time = sysTime;
            readyQueue1.enqueue(temp1);
            Memory_Available -= temp1.memory;
         }
         else
            break;
      }
      if(!readyQueue1.isEmpty()){
         PutJobinCPU();
      }
      else if(!readyQueue2.isEmpty() && CPU.event.equals("Empty")){
         CPU = readyQueue2.dequeue();
         CPU.CPU_arrival_time = sysTime;
      }
   }
   
   //Puts the job in CPU
   public static void PutJobinCPU(){
      if(readyQueue1.isEmpty()){
         JobSchedule();
      }
      if(!readyQueue1.isEmpty()){
         if(CPU.event.equals("Empty")){
            CPU = readyQueue1.dequeue();
            if(CPU.event.equals("A")){
               CPU.CPU_start_time = sysTime;
               CPU.CPU_arrival_time = sysTime;
            }
            else if(CPU.event.equals("C")){
               CPU.CPU_arrival_time = sysTime;
            }
         }
         else if(CPU.event.equals("E")){
            CPU.eventE_arrival_time = sysTime;
            readyQueue2.enqueue(CPU);
            CPU = new Event();
            CPU = readyQueue1.dequeue();
            if(CPU.event.equals("A")){
               CPU.CPU_start_time = sysTime;
            }
            CPU.CPU_arrival_time = sysTime;
         }
      }
      else if(!readyQueue2.isEmpty() && CPU.event.equals("Empty")){
         CPU = readyQueue2.dequeue();
         CPU.CPU_arrival_time = sysTime;
      }
   }
   
   //putting the process in the I/O Queue when input is needed
   public static void moveToIOQueue(){
      if(temp.event.equals("I") && sysTime == temp.arrival_time){
         CPU.event = "I";
         CPU.io_start_time = sysTime;
         CPU.io_burst_time = temp.io_burst_time;
         CPU.io_complete_time = CPU.io_start_time + CPU.io_burst_time;
         temp = new Event();
         ioWaitQueue.add(CPU);
         Collections.sort(ioWaitQueue);
         System.out.println("Event: I   Time: "+sysTime);
         CPU = new Event();
         CPUProcess();
      }
   }
   
   //If the I/O burst is done then the process is moved to Ready Queue Level 1
   public static void moveIOToReadyQueue(){
      if(!ioWaitQueue.isEmpty()){
         if(sysTime == ioWaitQueue.get(0).io_complete_time){
            Event temp1 = ioWaitQueue.get(0);
            temp1.event = "C";
            ioWaitQueue.remove(0);
            readyQueue1.enqueue(temp1);
            System.out.println("Event: C   Time: "+sysTime);
         }
      }
   }
   
   //The process in CPU is executed/simulated
   public static void CPUProcess(){
      if(!CPU.event.equals("Empty")){
         if(CPU.burst_time_left > 0){
            CPU.burst_time_left--;
         }
         if(CPU.burst_time_left == 0){
            CPU.event = "T";
            CPU.complete_time = sysTime;
            finishedQueue.enqueue(CPU);
            System.out.println("Event: "+CPU.event+"   Time: "+sysTime);
            Memory_Available += CPU.memory;
            CPU = new Event();
            JobSchedule();
         }
         else if((CPU.event.equals("A") || CPU.event.equals("C")) && 
                 (sysTime - CPU.CPU_arrival_time) == quantumA){
            CPU.event = "E";
            CPU.eventE_arrival_time = sysTime;
            readyQueue2.enqueue(CPU);
            System.out.println("Event: "+CPU.event+"   Time: "+sysTime);
            CPU = new Event();
            PutJobinCPU();
         }
         else if(CPU.event.equals("E") && 
                 (sysTime - CPU.CPU_arrival_time) == quantumE){
            CPU.event = "E";
            CPU.eventE_arrival_time = sysTime;
            readyQueue2.enqueue(CPU);
            System.out.println("Event: "+CPU.event+"   Time: "+sysTime);
            CPU = new Event();
            PutJobinCPU();
         }
         else if(CPU.event.equals("E") && !readyQueue1.isEmpty()){
            PutJobinCPU();
         }
      }      
      else
         PutJobinCPU();
   }
   
   //prints the final finished list of processes
   public static void completeList(){
      System.out.println("\nThe contents of the FINAL FINISHED LIST\n"
              + "---------------------------------------\n");
      double turnaroundTime = 0;
      double waitTime = 0;
      if(!finishedQueue.isEmpty()){
         System.out.println("Job #  Arr. Time  Mem. Req.  Run Time  Start Time  Com. Time\n"
              + "-----  ---------  ---------  -------- "
              + " ----------  ---------\n");
         Iterator<Event> iter = finishedQueue.iterator();
         while(iter.hasNext()){
            Event temp1 = iter.next();
            System.out.printf("%5d%11d%11d%10d%12d%11d\n",temp1.job,
                    temp1.arrival_time,temp1.memory,temp1.runtime,
                    temp1.CPU_start_time,temp1.complete_time);
            turnaroundTime += (temp1.complete_time - temp1.arrival_time);
            waitTime += (temp1.eventA_arrival_time - temp1.arrival_time);
         }
         System.out.printf("\n\nThe Average Turnaround Time for the simulation"
                 + " was %.3f units.\n",
                 (turnaroundTime/(finishedQueue.size())));
         System.out.printf("\nThe Average Job Scheduling Wait Time for "
                 + "the simulation was %.3f units.\n",
                 (waitTime/(finishedQueue.size())));   
      }
      else{
         System.out.println("The Final Finished List is empty.");
         System.out.printf("\n\nThe Average Turnaround Time for the simulation"
                 + " was 0 units.\n");
         System.out.printf("\nThe Average Job Scheduling Wait Time for "
                 + "the simulation was 0 units.\n");
      }
         
         System.out.println("\nThere are "+Memory_Available
                 +" blocks of main memory "
                 + "available in the system.");
   }
   
   //Main method 
   public static void main(String args[]){
      while(getInput() || !jobScheduleQueue.isEmpty() || !readyQueue2.isEmpty()
              || !readyQueue1.isEmpty() || !CPU.event.equals("Empty") 
              || !ioWaitQueue.isEmpty()){
         while(!inputLine.equals("Empty") && sysTime < eventTime){
            moveIOToReadyQueue();
            CPUProcess();
            sysTime++;
         }
         if(display == sysTime){
            moveIOToReadyQueue();
            CPUProcess();
            System.out.println("Event: D   Time: "+sysTime);
            new Display(jobScheduleQueue,readyQueue1,readyQueue2,finishedQueue,
            CPU, sysTime, ioWaitQueue);
            System.out.println("\n\nThere are "+Memory_Available+
                    " blocks of main memory available in the system.\n");
         }
         else if(!inputLine.equals("Empty") && sysTime == eventTime){
            moveIOToReadyQueue();
            CPUProcess();
            moveToIOQueue();
            JobSchedule();
         }
         else if(inputLine.equals("Empty")){
            CPUProcess();
         }
         sysTime++;
      }
      completeList();
   }
}