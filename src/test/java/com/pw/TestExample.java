package com.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExample {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        PlaywrightAssertions.setDefaultAssertionTimeout(30_000);
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
/*        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));*/
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
//        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./trace.zip")));
        context.close();
    }

    @Test
    void shouldClickButton() throws InterruptedException {
        page.navigate("data:text/html,<script>var result;</script><button onclick='result='Clicked''>Go</button>");
        page.locator("button").click(new Locator.ClickOptions().setNoWaitAfter(false));
        assertEquals("Clicked", page.evaluate("result"));
        Thread.sleep(1000);
    }

    @Test
    void shouldCheckTheBox() {
        page.setContent("<input id='checkbox' type='checkbox'></input>");
        page.locator("input").check();
        assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
    }

    @Test
    void shouldSearchWiki() {
        page.navigate("https://www.wikipedia.org/");
        page.locator("input[name='search']").click();
        page.locator("input[name='search']").fill("playwright");
        page.locator("input[name='search']").press("Enter");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
    }
}