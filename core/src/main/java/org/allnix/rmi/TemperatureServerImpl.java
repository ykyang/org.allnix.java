package org.allnix.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ./gradlew -PmainClass=org.allnix.rmi.TemperatureServerImpl runApp
 * 
 * @author Yi-Kun Yang ykyang@gmail.com
 *
 */
public class TemperatureServerImpl implements TemperatureServer, Runnable {
	static private Logger logger = LoggerFactory.getLogger(TemperatureServerImpl.class);

	private List<TemperatureListener> listenerList = new ArrayList<>();
	private volatile double temperature;

	protected TemperatureServerImpl() {
		temperature = 88;
	}

	public static void main(String[] args) throws RemoteException {
		TemperatureServerImpl server = new TemperatureServerImpl();
		Remote stub = UnicastRemoteObject.exportObject(server, 0);
		boolean done = false;
		int port = 1099;
		while (!done && port < 65535) {
			try {
				Registry registry = LocateRegistry.createRegistry(port);
				registry.rebind("TemperatureService", stub);
				Thread thread = new Thread(server);
				thread.start();
				System.out.println("Temperature Service Started");
				done = true;
				logger.info("Use port: {}", port);
			} catch (java.rmi.server.ExportException e) {
				logger.info("Port in use: {}", port);
				port++;
			}
		}
		if (!done) {
			logger.error("Cannot find a free port");
		}

	}

	@Override
	public void run() {
		Random random = new Random();
		while (true) {
			int duration = random.nextInt() % 10000 + 2000;
			duration = Math.abs(duration);

			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				// ignore
			}

			int dir = random.nextInt();
			if (dir < 0) {
				temperature -= 0.5;
			} else {
				temperature += 0.5;
			}

			this.notifyTemperatureListeners(temperature);
		}
	}

	private void notifyTemperatureListeners(double temp) {
		for (TemperatureListener listener : listenerList) {
			try {
				listener.temperatureChanged(temp);
			} catch (RemoteException e) {
				listenerList.remove(listener);
			}
		}
	}

	@Override
	public void addTemperatureListener(TemperatureListener listener) {
		listenerList.add(listener);
	}

	@Override
	public void removeTemperatureListener(TemperatureListener listener) {
		listenerList.remove(listener);
	}

	@Override
	public Double getTemperature() {
		return temperature;
	}

}