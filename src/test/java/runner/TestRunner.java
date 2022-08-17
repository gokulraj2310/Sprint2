package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(publish = true,features = {"src/test/java/features/Student.feature","src/test/java/features/User.feature"},glue = "steps",plugin = {"json:target/cucumber.json","pretty",
"html:target/cucumber-reports.html"})
    public class TestRunner extends AbstractTestNGCucumberTests {
    }




