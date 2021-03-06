//Zadatak:
//Program provjerava status zadanih domena, koriste?i bazen dretvi te ih ispisuje kroz standardni
//output.
package bazenDretvi;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class StatusDomena {
	private static final int MYTHREADS = 3;
	public static void main(String args[]) throws Exception {
		ExecutorService DretvaIzvrsitelj =
		Executors.newFixedThreadPool(MYTHREADS);
		String[] hostList = { "http://crunchify.com",
				"http://yahoo.com",
				"http://veleri.hr",
				"http://www.ebay.com", "http://google.com",
				"https://paypal.com"};
		for (int i=0; i<hostList.length;i++) {
			String adresa = hostList[i];
			Runnable domena = new NovaDretva(adresa);
			DretvaIzvrsitelj.execute(domena);
		}
		DretvaIzvrsitelj.shutdown();
		// ?ekaj dok se sve dretve ne izvr?e
		while (!DretvaIzvrsitelj.isTerminated()) {
		}
		System.out.println("\nZavr?ene sve dretve");
	}
	public static class NovaDretva implements Runnable {
		private final String adresa;
		NovaDretva(String adresa) {
			this.adresa = adresa;
		}
		public void run() {
			String aktivnost = "";
			int status = 200; //responseCode za neaktivne stranice
			String dretva = Thread.currentThread().getName();
			try {
				URL webAdresa = new URL(adresa);HttpURLConnection connection = (HttpURLConnection)
						webAdresa.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				status = connection.getResponseCode();
				if (status == 200) {
					aktivnost = "Aktivna";
				}
				else if (status == 404) {
					aktivnost = "Nije pronadjena";
				}
			} catch (Exception e) {
				aktivnost = "Neaktivna";
			}
			System.out.println(adresa + "\t\tStatus:" + aktivnost + "\t\tDretva: " + dretva);
		}
	}
}
