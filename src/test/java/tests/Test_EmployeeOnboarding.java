package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Workflow.EOR_Excel_EmployeeOnboarding;
import dataprovider.Dataprovider;
import models.Data;
import core.TestBase;

public class Test_EmployeeOnboarding extends TestBase {

	@Test(dataProvider = "sendData", dataProviderClass = Dataprovider.class)
	public void registerCandidate(Data input) throws Exception {
		EOR_Excel_EmployeeOnboarding eorEmployeeOnboarding = new EOR_Excel_EmployeeOnboarding();
		boolean loginError =eorEmployeeOnboarding.loginAdmin(input);

		Assert.assertFalse(loginError, "Login failed. Error message displayed.");

		eorEmployeeOnboarding.RegisterCandidate(input);
		eorEmployeeOnboarding.logoutAccount();

		eorEmployeeOnboarding.yopmailAcceptLetterOfIntent(input);

		eorEmployeeOnboarding.loginCandidateWithStoredCredentials(input);

		eorEmployeeOnboarding.CandidatePersonal(input);

		eorEmployeeOnboarding.forgotPassword(input);
	}
}
