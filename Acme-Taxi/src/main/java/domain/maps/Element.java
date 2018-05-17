
package domain.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

	@SerializedName("distance")
	@Expose
	private Distance	distance;
	@SerializedName("duration")
	@Expose
	private Duration	duration;
	@SerializedName("status")
	@Expose
	private String		status;


	public Distance getDistance() {
		return this.distance;
	}

	public void setDistance(final Distance distance) {
		this.distance = distance;
	}

	public Duration getDuration() {
		return this.duration;
	}

	public void setDuration(final Duration duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
