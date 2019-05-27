# ReadMe

## Problem
Suppose you have a big batch files(500M) which contains payment activities of customer account:

    e.g.  
    customerActId, payOrReceive, amount, currency
    0001, P, 100, SGD
    0002, R, 30.65, SGD
    0001, R, 39, SGD

There is another file which contains customer contact information:
    
    e.g.
    customerActId, emailAddress
    0001, aaa@gmail.com
    0002, bbb@gmail.com

Suppose all customer account has 1000 SGD initialized, after the batch is finished, we want to notify the customer via email about their balance.

Please ensure:
- Thinking about how to handle the large batch file without memory issue.
- Any solution which could make the solution run faster.
- Think about cloud native principles if you are doing micro service development.
- Code must have good unit test coverage.
- Code should be able to compile & run for demo purpose.

## Solution
- The solution consists of 4 microservices 
	- Discovery Service
		- Service Discovery Server
	- Producer Service 
		- Will read the input transactions file (Used Spring Batch to handle large file)
		- Each time a file record is read, it will be published to a kafka topic in real time as a cash transaction message (This will help to improve memory utilization)
		- Once the file reading is over sends a notification to email service though another kafka topic
		- Start of a new file batch can be triggered by sending a get request to http://localhost:8082/file
	- Account Service
		- This service consumes cash transactions published by producer service and updates the balance accordingly
		- Keeps track on how many messages from topic it has consumed
		- Provides http://localhost:8083/balance/{accounID} end point to retrive balances for external services using account id
		- Provides http://localhost:8083/balance/lastOffset end point to retreive latest msg number it consumed from kafka
	- Email Service
		- Consumes the batch complete notification from producer service from kafka
		- Once the notification is recived, checks whether account service has consumed all the cash transactions
		- If all cash transactions are processed, starts processing email address file (Used Spring Batch to handle large file)
		- For each email address recieved sends a email notifying the account summary (For demo email sending is mocked as a console out)

## Enhancements and Expansion
- To improve throughput, can add multiple kafka clients as Account Services
- Can add multiple producers to process several files in parallel
- If multiple consumers are used, need to add another service to monitor all clients have reached the kafka topic end
- Then email service should call this new service to check whether it should start email sending
- Need to call an actual email sending service api to replace mocked email sending
- Need to add a loging utility for production usage

## How to build and run
Pre-Requisites:
- Zookeeper and Kafka should be up and running in their default ports (2181 and 9092) in local machine
- Kafka needs to have two topics created namely "transactions" and "notifications"

Build and run:
- Open a terminal window in discovery-service folder
	- Execute mvn install
	- Execute spring-boot:run
- Open a terminal window in producer-service folder
	- Execute mvn install
	- Execute spring-boot:run
- Open a terminal window in account-service folder
	- Execute mvn install
	- Execute spring-boot:run
- Open a terminal window in email-service folder
	- Execute mvn install
	- Execute spring-boot:run

## How to test
- Go to producer-service\src\main\resources and place transaction input file named as inputTransactions.csv
- Go to email-service\src\main\resources and place email address file named as emailAddresses.csv
- Send a get request to http://localhost:8082/file to start processing the files

## Test results
- Unit tested all functional classes
- Integration test done on a 1M records transaction file on personal computer within 2 mins

Log trace of test result:

Request received to process input transactions file
2019-05-27 21:54:07.431  INFO 342168 --- [nio-8082-exec-6] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=transaction-file-load]] launched with the following parameters: [{Time=1558965247421}]

2019-05-27 21:55:56.566  INFO 453992 --- [ntainer#0-0-C-1] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=emailInfo-file-load]] completed with the following parameters: [{Time=1558965356508}] and the following status: [COMPLETED]
Email info file processing status : COMPLETED
