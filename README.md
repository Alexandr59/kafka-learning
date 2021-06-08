# kafka-learning

### Installation:

1. Install and run Zookeeper (bin\zkServer.cmd)
2. Install and run Kafka (bin\windows\kafka-server-start.bat .\config\server.properties)
3. Create kafka topics called "myTopic"
4. Run kafka producer and consumer
5. Send a message through command line

Setting Up and Running Apache Kafka on Windows OS
https://dzone.com/articles/running-apache-kafka-on-windows-os

### Tasks 1 (without Docker):

0. Kakfka:
    - Create a topic in Kafka from command line.
    - Write a producer which sends the first n Fibonacci numbers through Kafka (the n number can be given as a command
      line argument).
    - Write a consumer which reads the numbers from Kafka, calculates the sum of them.

### Tasks 2 (with Docker):

1. Reading
    - Read about Horizontally and Vertically Scaling.
    - Read about Kafka Consumer Group. What is the main purpose of Consumer Group?
2. Run kafka + zookeeper in docker. Use Docker-compose. Create one topic.
3. Create two files: first_consumer.txt, second_consumer.txt.
4. Create and run kafka producer, write 1000 messages to the topic.
    - Delay between messages = 100 milliseconds.
    - Message should consist of the message number and some text. For example: 1hello, 2hello, 3hello ...
5. Create and run two kafka consumers within one consumer group.
6. Execution flow: start the producer > start the first consumer > delay 20_000 milliseconds > start the second
   consumer.
7. Write all messages from two consumers in files.
    - The total number of lines in two files must be equal 1000. for example
    - Foe example: Consumer1 gets 600 messages and consumer2 gets 400 messages. In total it should be 1000 messages.
    - Each line should look like this: consumer name, message, partition number.
7. What is the difference between:
    1) Producer sends (topic name, partition number, value)
    2) Producer sends (topic name, value)
    3) Producer sends (topic name, key, value) key is unique for all messages
    4) Producer sends (topic name, key, value) the same key for all messages

If you can not connect to kafka:

https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/
https://www.kaaproject.org/blog/kafka-docker
https://rmoff.net/2018/08/02/kafka-listeners-explained/

