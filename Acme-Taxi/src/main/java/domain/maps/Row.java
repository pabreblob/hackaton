
package domain.maps;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

	@SerializedName("elements")
	@Expose
	private List<Element>	elements	= null;


	public List<Element> getElements() {
		return this.elements;
	}

	public void setElements(final List<Element> elements) {
		this.elements = elements;
	}

}
