# automation-support
Generic automation framework using latest Selenium, Spring, Java 1.8.  Work in progress....

## About this Project
Automation-support project to build multiple robust Selenium/UI suites.  Contains logic to retry failed tests, 
take screenshots of test failures, and all selenium/web-driver setup.  The URL Builders are somewhat specific
to current projects I'm working on so I need to refactor that quite a bit to make this more generic for other
suites.

## TODO / Known Issues
1. ScreenShotRule is not functioning properly.  Need to refactor. 
2. URLBuilder / Test Modules are too specific to current projects.  Need to refactor
3. I have only added support for Chrome and headless chrome, but it would take very little effort to 
add support for PhantomJS/Firefox & other browsers if needed.