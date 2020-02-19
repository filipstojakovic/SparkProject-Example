# OPOS-SparkProject

Simple Example: using Cluster to sort array of chars

Things used in this project:
- Cluster with 3 nodes. 
Using VirtualBox i made three Virtual Machines (1 namenode/master, 2 slaves/workers) 
and i set up a DHCP server to always give same IP adress to my three nodes so i dont 
worry about IP's when i change network
- Spark with Hadoop
- Maven
- Java 8 and JavaFX (making responsive UI so it doesn't freeze when background task is active)


>cat /etc/hosts
10.10.1.12 datanode1
10.10.1.13 datanode2
10.10.1.14 namenode1
