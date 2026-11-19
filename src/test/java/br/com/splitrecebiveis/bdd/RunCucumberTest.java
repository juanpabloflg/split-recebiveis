package br.com.splitrecebiveis.bdd;
 
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
 
import org.junit.platform.suite.api.ConfigurationParameter;

import org.junit.platform.suite.api.IncludeEngines;

import org.junit.platform.suite.api.SelectClasspathResource;

import org.junit.platform.suite.api.Suite;
 
/**

* Executa os cenários .feature da pasta src/test/resources/features com o Cucumber.

*/

@Suite

@IncludeEngines("cucumber")

@SelectClasspathResource("features")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.splitrecebiveis.bdd")

@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html")

public class RunCucumberTest {

}
 
