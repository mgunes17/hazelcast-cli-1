# What is hazelcast-cli?

hazelcast-cli is a Hazelcast management tool, a command line interface to install, configure, and upgrade Hazelcast on the local machine or cloud from any operating system.

# Installation

Run the following command to install hazelcast-cli: 

```brew install https://raw.githubusercontent.com/bilalyasar/homebrew/master/Library/Formula/hazelcast-cli.rb```

# Requirements

## hazelcast-cli.properties

You need to create a properties file in your `home` to define the host machines to run Hazelcast. Name the file as `hazelcast-cli.properties` and provide your host username, host IP and port in this file. Please see the below properties file content as an example:

```
europe1.user=ubuntu
europe1.ip=ec2-54-159-97-238.compute-1.amazonaws.com
europe1.port=22
```

If you want to use classes in cluster which you connect, you need to create a jar file contains that classes and need to write jar path to properties file.

```
jar.path=/home/mgunes/test.jar 
```

## SSH configuration

Your public key has to be configured on the target machine.

# Operations

## Install Hazelcast

`hazelcast --install --hostname europe1 --version 3.4.1`

## Start a Hazelcast Member

`hazelcast --start --hostname europe1 --clustername wildcats --nodename tiger`

## Stop a Hazelcast Member

`hazelcast --stop --hostname europe1 --clustername wildcats --nodename tiger`

## Start Management Center

`hazelcast --startMC --hostname europe1`

## Rolling Upgrade Management Center

`hazelcast --upgrade --clustername wildcats --version 3.4.2`

# TODO

* More operating system support: apt-get, exe, Android.
* Starting Hazelcast bundled Tomcat.










