package testing;

import com.aventstack.extentreports.Status;
import listeners.TestListeners;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.logoutPage;
import pages.productPage;

import java.io.IOException;


@Listeners(TestListeners.class)
public class LoginTestCase extends BaseTest {

    @Test
    public void InValidUserName() throws InterruptedException, IOException, ParseException {

            test = extent.createTest("InValidUserName", "Verify login with invalid username fails");

            LoginPage loginPage = new LoginPage();
            sleep(); // Assuming this is a custom method to introduce a wait time, ensure it's correctly implemented
            JSONObject invalidCredentials = getCredentials("invalidUsername");
            startRecording();
            loginPage.Login((String) invalidCredentials.get("username"), (String) invalidCredentials.get("password"));
            loginPage.ClickEnter();

    }

    @Test(dependsOnMethods = "InValidUserName", alwaysRun = true)
    public void InvalidPassword() throws InterruptedException, IOException, ParseException, ParseException {
        LoginPage loginPage = new LoginPage();
        JSONObject invalidCredentials = getCredentials("InvalidPassword");
        loginPage.Login((String) invalidCredentials.get("username"), (String) invalidCredentials.get("password"));
        loginPage.ClickEnter();
        takeScreenshot("invalid_password");
        extent.createTest("InvalidPassword", "The user wrote wrong password ");
    }

    @Test(dependsOnMethods = "InvalidPassword", alwaysRun = true)
    public void ValidUsername() throws InterruptedException, IOException, ParseException, ParseException {
        LoginPage loginPage = new LoginPage();
        JSONObject invalidCredentials = getCredentials("valid");
        extent.createTest("ValidUserName", "The user wrote the right username");
        loginPage.Login((String) invalidCredentials.get("username"), (String) invalidCredentials.get("password"));
        loginPage.ClickEnter();
    }

    @Test(dependsOnMethods = "ValidUsername", alwaysRun = true)
    public void chooseProduct() throws InterruptedException {
        extent.createTest("choose a product", "The user can choose a product");
        productPage productPages = new productPage(); // Corrected class name
        productPages.checkProducts(); // Assuming this is a method to check products, ensure it's correctly implemented
    }

    @Test(dependsOnMethods = "chooseProduct", alwaysRun = true)
    public void CheckOut() throws InterruptedException {
        extent.createTest("check out option", "The user can checkout a product");
        CheckoutPage check = new CheckoutPage();
        check.initializeElementsAndClickCheckoutButton(); // Assuming this is a method to initialize elements and click checkout, ensure it's correctly implemented
        check.fillTheInformation(); // Assuming this is a method to fill in information, ensure it's correctly implemented
        check.clickContinue(); // Assuming this is a method to click continue, ensure it's correctly implemented
    }

    @Test(dependsOnMethods = "CheckOut", alwaysRun = true)
    public void logOut() throws InterruptedException, IOException {
        extent.createTest("LogOut", "The user could log out");
        logoutPage logout = new logoutPage(); // Corrected class name
        logout.logout(); // Assuming this is a method to logout, ensure it's correctly implemented
        stopRecording();
    }
}




