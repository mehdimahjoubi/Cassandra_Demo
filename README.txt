This project is an example demonstrating:

- The use of the NOSQL database Cassandra,
including its denormalization according to the requests it is going to receive
and how to connect and interrogate the database using DatarStax's Java Driver.

- The exposure of a Cassandra backed RESTful Web Service using the JAX-RS specification.


The example exposes some simple fonctionnalities for a minor Gaming Social Network.


Additional notes:

In order, to run this project, you will need to have your environment correctly set up with Cassandra up and running,
along with a configured web container for the web service.
You will also need to run the cql commands contained in gaming_network.CQL on your Cassandra database.
      
No client implementation comes with this project,
but most requests being HTTPGET based, you should be able to observe the JSON response using a simple web browser.