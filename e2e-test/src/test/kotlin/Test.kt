import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class Test {
    @Test
    fun `test google search`() {
        val driver: WebDriver = ChromeDriver(
            ChromeOptions()
                .addArguments("--headless")
                .addArguments("--disable-gpu")
                .addArguments("--window-size=1920,1080")
        )
        driver.get("https://www.google.com")
        assertEquals("Google", driver.title)
        driver.quit()
    }
}