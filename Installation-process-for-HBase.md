# Installation process for Geomesa with HBase 
##On Windows Machine
###Prerequisites
The Windows machine must be capable of running Hyper-V. If Hyper-V is not enabled / running, enable / run Hyper-V. To check if Hyper-V is enable / running or not perform the following steps:

- Press `WinKey` + `R` and type `services.msc` in the run window.
- Check if "Hyper-V Host Compute Service" is running or not. If it is not running, start the service. If you are not able to find the service, refer to the following URL to enable Hyper-V.

URL: https://learn.microsoft.com/en-us/virtualization/hyper-v-on-windows/quick-start/enable-hyper-v

###WSL (Windows Subsystem for Linux) must be Enabaled and Running 
Make sure WSL has been installed and configured on your system. For the subsequent part of this document we will refer to Ubuntu 22 as the Linux Distribution. Use the following command to check if you have any Linux Distribution installed on your system or not on WSL.
`wsl -l -v`

If no Linux Distribution is found under WSL perform the following to install Ubuntu 22 distribution.

- Get the supported Linux Distributions by running the following command
`wsl --list --online`
You should see Ubuntu-22.04 in the list.
- Run the following command to install Ubuntu 22 in WSL
`wsl --install -d Ubuntu-22.04`

Once the Linux OS is installed perform the steps below

###Install Java 8 if not installed already
- Run `java -version` to check if Java is already installed or not. If Java is not installed do the following.
```
sudo bash
apt-get update
apt-get install openjdk-8-jdk
```

####Set JAVA_HOME
- Run command `update-alternatives --list java` to get the java library path. You may get a path similar to the following
`/usr/lib/jvm/<base_folder>/jre/bin/java`
The <base_folder> name may vary from system to system.
- edit .bashrc file (using vi or nano) and append the path till the root of the java installation folder from the above step as shown below.
`export JAVA_HOME="/usr/lib/jvm/<base_folder>/"`

###Install HBase and run in Standalone Local mode
We will be installing HBase 2.4.16
- HBase can be installed in any folder of your choice but as best practice, either use `/opt` or `/usr/local` folder.
- For this example we will consider `/opt` folder to install HBase. Go to /opt folder
`cd /opt`
- Download the hbase binary using the following command
`wget https://www.apache.org/dyn/closer.lua/hbase/2.4.16/hbase-2.4.16-bin.tar.gz`
- Un-compress the tarfile using the following command
`tar xzvf hbase-2.4.16-bin.tar.gz`
- Run the following commands
`cd hbase-2.4.16/bin`
`./start-hbase.sh`
This  should start HBase in standalone local mode. Run `jps` command to see whether HBase is running or not. If HBase is running, you should see `HMaster` in the list.
- Stop HBase. We will configure HBase to run HBase in Network mode.
`./stop-hbase.sh`

###Run HBase in Network Mode with Zookeeper
- Download zookeeper (version 3.7.1) and configure
```
cd /opt
wget https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz
tar xzvf apache-zookeeper-3.7.1-bin.tar.gz
cd apache-zookeeper-3.7.1-bin/conf
cp zoo_sample.cfg zoo.cfg
```
Check the local IP Address of your Linux Machine
`ifconfig`
The IP Address may be under eth0 or under Wi-Fi. Note down the IP Address.
We need to set the IP Address in the zoo.cfg file to bind Zookeeper to that IP Address.
- Edit zoo.cfg file and append the following line to the bottom of the config file.
`clientPortAddress=<Local_IP_Address>`

Note: If your local computer is allocated an IP Address through DHCP, the IP address may change. In such a case, we may need to edit the IP Address when the IP changes.

`// TODO: Need to find ways to run HBase with zookeeper without having to run ZK on fixed IP Address. By default ZK runs on all IPs.`

You can also see that by default Zookeeper runs on port 2181 (clientPort) and dataDir path is set to `\tmp\zookeeper`. Note these configurations. We will need to configure them in HBase Configuration.

After the necessary changes to `zoo.cfg` are done, save the file and start the Zookeeper server by running the following commands.

`/opt/apache-zookeeper-3.7.1-bin/bin/zkServer.sh start`

Run jps to check if zookeeper is running or not. If you see a process "QuorumPeerMain", zookeeper has been started.

####Configure hbase to run with zookeeper
This configuration will run HBase in non-distributed mode.

- Open the HBase configuration file named `hbase-site.xml` in your favorite editor. The config file is in the `conf` folder.
- Remove all the properties from the configuration and paste the following after replacing the IP Address from your local computer.

```
  <property>
    <name>hbase.cluster.distributed</name>
    <value>false</value>
  </property>
  <property>
    <name>hbase.zookeeper.quorum</name>
    <value><Local_IP_Address></value>
  </property>
  <property>
    <name>hbase.zookeeper.property.clientPort</name>
    <value>2181</value>
  </property>
  <property>
    <name>hbase.tmp.dir</name>
    <value>/opt/hbase-2.4.16/bin/tmp/hbase</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/tmp/zookeeper</value>
  </property>
  <property>
    <name>hbase.unsafe.stream.capability.enforce</name>
    <value>false</value>
  </property>
```
Save the configuration file and start HBase by running the following commmand.
`/opt/hbase-2.4.16/bin/start-hbase.sh`

To verify whether HBase started or not, run `jps` to check the processes. You should see `HMaster`. To further verify run the following command to check the listening ports for HBase Services.

`lsof -i -P | grep LISTEN`

You should see HBase services running.

Open the following URL in your Web Browser to check the statuses
URL: http://localhost:16010/


####To connect remote host and hbase
To communicate host machine and remote machine, add addresses in /etc/host of both machines.


###Configure Geomesa
```
// TODO: This is a placeholder for the Geomesa steps
```

##On Apple MacOS

```
// TODO: This is a placeholder for the steps for MacOS
```

##On Docker

```
// TODO: This is a placeholder for the steps for MacOS
```