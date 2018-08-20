# Enrichment: Angular Day 3 - Java Server
## Setup
1. Recommended: use Netbeans and set up Payara server and MySQL database with [Sakila sample database](https://dev.mysql.com/doc/sakila/en/)
2. Install [MySQL Connector/J 5.1.47](https://dev.mysql.com/downloads/connector/j/) on your Payara server
    - Hint: put it in your /lib directory in your Payara install directory
3. Using the Payara admin view, create your Connection Pool with the following properties: server, portnumber, user, password, password, databasename, url