package automation.webdriver;


import static com.google.common.base.Preconditions.checkState;

import com.google.common.io.ByteStreams;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
public enum Browser
{
    CHROME
            {
                @Override
                public WebDriver createDriver()throws IOException
                {
                    checkDriverProperty("webdriver.chrome.driver", getUrl("chrome/" + executable("chromedriver")));
                    ChromeDriver driver = new ChromeDriver(new DesiredCapabilities());
                    driver.manage().window().maximize();

                    return  driver;
                }
            },
    CHROME_HEADLESS
            {
                @Override
                public WebDriver createDriver()throws IOException
                {
                    checkDriverProperty("webdriver.chrome.driver", getUrl("chrome/" + executable("chromedriver")));
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(new String [] {"--headless", "--enable-logging", " --v=5","--allow-running-insecure-content", "--ignore-certificate-errors"});
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    capabilities.setCapability("acceptSslCert", true);
                    LOGGER.debug("capabilities " + capabilities.toString());

                    return new ChromeDriver(capabilities);
                }
            },

    FIREFOX
            {
                @Override
                public WebDriver createDriver()throws IOException
                {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setCapability(FirefoxDriver.PROFILE,
                            new ProfilesIni().getProfile(System.getProperty("firefox.profile")));
                    capabilities.setCapability("acceptSslCert", true);

                    return new FirefoxDriver(capabilities);
                }
            };

    public abstract WebDriver createDriver() throws IOException;

    private static final Logger LOGGER = LogManager.getLogger(Browser.class);

    private static URL getUrl(String resourceName) throws IOException
    {
        ClassLoader classLoader = Browser.class.getClassLoader();
        return classLoader.getResource(resourceName);
    }

    private static void checkDriverProperty(String propertyName, URL resource) throws IOException
    {
        final Path driverPath;
        String property = System.getProperty(propertyName);
        if (property == null)
        {
            driverPath = writeToTempFile(propertyName, resource);
            LOGGER.info(propertyName + " Driver defaulted to " + driverPath.toUri());
            System.setProperty(propertyName, driverPath.toString());
        }
        else
        {
            driverPath = Paths.get(property);
        }
        if (!Files.exists(driverPath))
        {
            throw new FileNotFoundException("Driver file does not exist: " + driverPath);
        }
    }

    private static Path writeToTempFile(String name, URL resource) throws IOException
    {
        Path path = Files.createTempFile(name, "");
        LOGGER.debug("executable file path: " + path);
        File file = path.toFile();
        file.deleteOnExit();
        checkState(file.setExecutable(true), "Unable to make driver executable");
        try (InputStream in = resource.openStream())
        {
            Files.write(path, ByteStreams.toByteArray(in), StandardOpenOption.WRITE);
        }
        return path;
    }

    private static String executable(String filename)
    {
        String osName = System.getProperty("os.name").toLowerCase();
        String extension;
        if (osName.startsWith("windows"))
        {
            extension = ".exe";
        }
        else if (osName.startsWith("mac"))
        {
            extension = "_mac";
        }
        else // assume Linux-compatible
        {
            extension = "_linux" + addBitness();
            LOGGER.debug("platform binary "+ extension);
        }
        return filename + extension;
    }

    /**
     * @return "64" for 64-bit JVMs, "32" otherwise
     */
    private static String addBitness()
    {
        String dataModel = System.getProperty("sun.arch.data.model");
        return dataModel.equalsIgnoreCase("unknown") ? "32" : dataModel;
    }
}

