import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Username_username'), 'Admin')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_OrangeHRM/input_Password_password'), 'hUKwJTbofgPU9eVlw/CnDQ==')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_Password_oxd-button oxd-button--medi_8860b7'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/span_Time_oxd-text oxd-text--span oxd-main-_a32e84'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_Search_oxd-button oxd-button--medium_24d872'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/input_Full Name_firstName'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Full Name_firstName'), 'Jisha')

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Full Name_lastName'), 'Thomas')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/div_Vacancy_oxd-select-text-input'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Email_oxd-input oxd-input--focus'), 'tjisha33@gmail.com')

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/textarea_Notes_oxd-textarea oxd-textarea--f_0ce0e8'), 'Test Candidate')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_Cancel_oxd-button oxd-button--medium_160139'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/span_Upgrade_oxd-userdropdown-tab'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/a_Change Password_oxd-userdropdown-link'))

WebUI.closeBrowser()

