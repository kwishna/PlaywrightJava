package pw;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class Twitter {
    public static void main(String[] args) throws InterruptedException {
        try (Playwright playwright = Playwright.create()) {
            BrowserContext context;
            try (Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false))) {
                context = browser.newContext(new Browser.NewContextOptions().setStrictSelectors(false));
            }
            Page page = context.newPage();

            // Go to https://twitter.com/
            page.navigate("https://twitter.com/");

            // Click text=Sign in
            page.locator("text=Sign in").click();
            assertThat(page).hasURL("https://twitter.com/i/flow/login");

            // Click label div >> nth=3
            page.locator("label div").nth(3).click();

            // Fill input[name="text"]
            page.locator("input[name=\"text\"]").fill("krsnasngh@gmail.com");

            // Click div[role="button"]:has-text("Next")
            page.locator("div[role=\"button\"]:has-text(\"Next\")").click();

            // Fill input[name="password"]
            page.locator("input[name=\"password\"]").fill("XXXXXXXXXX");

            // Click [data-testid="LoginForm_Login_Button"]
            page.locator("[data-testid=\"LoginForm_Login_Button\"]").click();
            assertThat(page).hasURL("https://twitter.com/home");

            page.locator(".css-4rbku5 > div:nth-child(4) > .css-1dbjc4n").first().click();
            assertThat(page).hasURL("https://twitter.com/krsnasngh");

            // Click text=Tweets & replies
            page.locator("text=Tweets & replies").click();
            assertThat(page).hasURL("https://twitter.com/krsnasngh/with_replies");
            double x = 500;

            while (true) {// a[href*='krsnasngh'] time[datetime]
//                page.click("(//span[text()='@krsnasngh']//ancestor::a[@href='/krsnasngh']//following::div[@aria-haspopup='menu' and @aria-label=\"More\"])[1]");
                page.click("//span[text()='Delete']");
                page.click("//span[text()='Delete']");

//                x += 500;
//                page.evaluate("window.scrollTo(0, "+x+");");
                page.keyboard().press("PageDown");
                page.keyboard().press("PageDown");
                Locator el = page.locator("a[href*='krsnasngh'] time[datetime]").first();
                page.evaluate("_handle => _handle.scrollIntoView();", el.elementHandle());
//                page.evaluate("window.scrollTo(0, (document.body.scrollHeight - 500));");


                Thread.sleep(500);
//
//                page.click("(//span[text()='@krsnasngh']//ancestor::a[@href='/krsnasngh']//following::div[@aria-haspopup='menu' and @aria-label=\"More\"])[3]");
//                page.click("//span[text()='Delete']");
//                page.click("//span[text()='Delete']");
//
//                Thread.sleep(500);
//                page.reload();
//
//                Thread.sleep(5000);
            }
        }
    }
}