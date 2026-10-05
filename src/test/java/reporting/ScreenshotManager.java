package reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotManager {
        public static String capture(WebDriver driver, String elementName) throws IOException {
    String folderPath = System.getProperty("user.dir") + "/extent-reports/click-screenshots";
    Files.createDirectories(Paths.get(folderPath));
    String fileName = elementName.replaceAll("[^a-zA-Z0-9-_]", "_") + "_" + System.currentTimeMillis() + ".png";
    Path filePath = Paths.get(folderPath, fileName);
    try {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Temporarily remove sticky/fixed positioning
        js.executeScript(
            "window.__originalPositions = [];" +
            "document.querySelectorAll('*').forEach(function(el) {" +
            "  var style = window.getComputedStyle(el);" +
            "  if (style.position === 'sticky' || style.position === 'fixed') {" +
            "    window.__originalPositions.push({" +
            "      el: el," +
            "      position: el.style.position," +
            "      top: el.style.top," +
            "      bottom: el.style.bottom," +
            "      left: el.style.left," +
            "      right: el.style.right," +
            "      zIndex: el.style.zIndex" +
            "    });" +
            "    el.style.position = 'static';" +
            "    el.style.top = 'auto';" +
            "    el.style.bottom = 'auto';" +
            "    el.style.left = 'auto';" +
            "    el.style.right = 'auto';" +
            "    el.style.zIndex = 'auto';" +
            "  }" +
            "});"
        );
        Thread.sleep(300);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = ((org.openqa.selenium.chromium.ChromiumDriver) driver)
            .executeCdpCommand(
                "Page.captureScreenshot",
                Map.of(
                    "format", "png",
                    "captureBeyondViewport", true,
                    "fromSurface", true
                )
            );
        String base64Screenshot = (String) result.get("data");
        Files.write(filePath, Base64.getDecoder().decode(base64Screenshot));
        // Restore original positioning
        js.executeScript(
            "if (window.__originalPositions) {" +
            "  window.__originalPositions.forEach(function(item) {" +
            "    item.el.style.position = item.position;" +
            "    item.el.style.top = item.top;" +
            "    item.el.style.bottom = item.bottom;" +
            "    item.el.style.left = item.left;" +
            "    item.el.style.right = item.right;" +
            "    item.el.style.zIndex = item.zIndex;" +
            "  });" +
            "  window.__originalPositions = null;" +
            "}"
        );
    } catch (Exception e) {
        // Restore page even if screenshot fails
        try {
            ((JavascriptExecutor) driver).executeScript(
                "if (window.__originalPositions) {" +
                "  window.__originalPositions.forEach(function(item) {" +
                "    item.el.style.position = item.position;" +
                "    item.el.style.top = item.top;" +
                "    item.el.style.bottom = item.bottom;" +
                "    item.el.style.left = item.left;" +
                "    item.el.style.right = item.right;" +
                "    item.el.style.zIndex = item.zIndex;" +
                "  });" +
                "  window.__originalPositions = null;" +
                "}"
            );
        } catch (Exception ignored) {
        }
        // Normal screenshot fallback
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(screenshot.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    return filePath.toString();
}
}
