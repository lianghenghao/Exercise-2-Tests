import org.junit.Assert;
import org.junit.Test;

public class junitTest {
	//choose name here
	private static String name = "HYATT AUSTIN";

	@Test 
	public void testId() throws Exception {
		//Get station Id and address
		Station bean = JSONOutput.getStationAddressAndId(name);
		//get id from bean
		String id = bean.getId();
		//Assert id is as expected
		Assert.assertEquals("This is the wrong id", "62029", id);
	}

	@Test
	public void testAddress() throws Exception {
		//Get station Id and address
		Station bean = JSONOutput.getStationAddressAndId(name);
		//get address from bean
		String address = bean.getAddress();
		//Assert id is as expected
		Assert.assertEquals("This is the wrong address",
				"HYATT AUSTIN 208 Barton Springs Rd Austin TX 78704", address);
	}
}
