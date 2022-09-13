# Playwright With Java
[Playwright - Docs](https://playwright.dev/java/docs/intro)

[Playwright - API](https://playwright.dev/java/docs/api/class-playwright)

`
    
    import com.microsoft.playwright.*;
    import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
    
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserContext context;
            try (Browser browser = playwright.webkit()
                    .launch(new BrowserType.LaunchOptions()
                    .setHeadless(false))) 
                {
                    context = browser.newContext();
                }
            Page page = context.newPage();
            page.navigate("https://www.google.co.in/");
            page.frameLocator("iframe[role='presentation']").locator("text=No thanks").click();
            page.locator("[aria-label='Search']").fill("noida");
            page.locator("text=noida twin tower demolition").click();
            assertThat(page).hasURL("https://www.google.co.in/search?q=noida+twin+tower+demolition");
            page.locator("#hdtb-msb >> text=Images").click();
            assertThat(page).hasURL("https://www.google.co.in/search?q=noida+twin+tower+demolition");
        }
    }
`
