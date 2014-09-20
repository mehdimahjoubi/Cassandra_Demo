package CassandraDataAccess;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class ConnectionFactory {

	private static String NODE = "127.0.0.1";
	
	private static Session session = null;
	private static Cluster cluster;
	
	public static Session getSession(){
		if (session == null) {
			cluster = Cluster.builder().addContactPoints(NODE).build();
			session = cluster.connect();
			// checking connection infos:
			Metadata metadata = cluster.getMetadata();
			System.out.printf("Connected to cluster: %s\n",
					metadata.getClusterName());
			for (Host host : metadata.getAllHosts()) {
				System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
						host.getDatacenter(), host.getAddress(), host.getRack());
			}
		}
		if (session.isClosed()) {
			if (cluster.isClosed())
				cluster = Cluster.builder().addContactPoints(NODE).build();
			session = cluster.connect();
		}
		return session;
	}
	
	public static void CloseConnection(){
		session.close();
		cluster.close();
		session = null;
	}
	
}
