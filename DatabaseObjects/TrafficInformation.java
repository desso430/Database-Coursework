package DatabaseObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class TrafficInformation implements Serializable {

	private static final long serialVersionUID = -4488599343025855609L;
	ArrayList<TrafficForUser> list = new ArrayList<TrafficForUser>();

	public void add(TrafficForUser statistic) {
		list.add(statistic);
	}
	
	public ArrayList<TrafficForUser> getList() {
		return list;
	}
}
