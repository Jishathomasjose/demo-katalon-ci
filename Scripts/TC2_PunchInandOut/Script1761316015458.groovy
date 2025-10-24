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

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/a_Leave_oxd-main-menu-item'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/i_Attendance_oxd-icon bi-chevron-down'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/a_Attendance_oxd-topbar-body-nav-tab-link'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/input_Time_oxd-input oxd-input--focus'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Time_oxd-input oxd-input--focus oxd-t_84d7a6'), '06')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/input__am'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/textarea_Note_oxd-textarea oxd-textarea--fo_bc10e6'), 'sample attendance')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_Note_oxd-button oxd-button--medium o_d0e972'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/input_Time_oxd-input oxd-input--focus_1'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/input_Time_oxd-input oxd-input--focus oxd-t_84d7a6_1'), '09')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/input_AM_pm'))

WebUI.setText(findTestObject('Object Repository/Page_OrangeHRM/textarea_Note_oxd-textarea oxd-textarea--fo_bc10e6'), 'sample test')

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_Note_oxd-button oxd-button--medium o_d0e972_1'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/span_Timesheets_oxd-topbar-body-nav-tab-item'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/a_Attendance_oxd-topbar-body-nav-tab-link_1'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/i_sample test_oxd-icon bi-trash'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/button_No, Cancel_oxd-button oxd-button--me_0e1504'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/span_Upgrade_oxd-userdropdown-tab'))

WebUI.click(findTestObject('Object Repository/Page_OrangeHRM/a_Change Password_oxd-userdropdown-link'))

