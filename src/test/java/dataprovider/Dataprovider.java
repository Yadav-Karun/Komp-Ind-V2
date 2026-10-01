package dataprovider;

import java.io.File;
import java.util.List;

import org.testng.annotations.DataProvider;

import models.Data;
import utils.JsonDataReader;

public class Dataprovider {
	 @DataProvider(name = "sendData")
	public Object[][] sendData() throws Exception {
		List<Data> data = JsonDataReader.getJsonData(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
						+ "resources" + File.separator + "testdata" + File.separator + "InputData.json");

		int arraySize = data.size();

		Object[][] result = new Object[arraySize][1];
		for (int i = 0; i < arraySize; i++) {
			result[i][0] = data.get(i);
		}
		return result;
	}
}
