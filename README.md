# 1CoreOsSim
This project simulates some of the job and CPU scheduling of a time-shared operating system. 
It can handle 6 different events.  
A, I, & D, & E, C, & T - Arrived, Input, Display, Time expired, I/O Complete,and Terminated respectively.
All events arrive in ascending order of time and no two events arrive at the same time.

Examples of events
Event Time Job Memory Run Time
A     1     1  24     200
Event Time I/O Burst Time
I     1     85
Event Time
D     2

There are five queues. Job Scheduling Queue, Ready Queue level1, Ready Queue level 2, I/O Wait Queue, and Finished Queue.
All the queues are maintained in FIFO order except for I/O Wait Queue. I/O Wait Queue is a priority queue. 
The CPU has a max memory of 512 units.
Ready Level 1 and 2 Queues use round robin technique. The jobs from ready queue 1 are allowed a quantum of 100 units on the CPU and the 
jobs from ready queue 2 are allowed a quantum of 300 time units on the CPU. Jobs from ready queue level 2 are allowed on CPU only when 
there are no jobs on ready queue level 1. When an I/O event arrives the job in the CPU is pre-empted and is put in I/O wait queue until 
the burst time completes. It is then sent to ready queue level 1. Once the job is finished it is sent to finished queue. 
The D event which is Display, prints all the contents of the 5 queues and gives the memory available in the system. 

This project gets the input from STDIN and gives the output to STDOUT. 
