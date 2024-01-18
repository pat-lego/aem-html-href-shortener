# AEM URL Shortener for href 

This repository shortens outgoing URL's from HTML pages generated by Sling.

## How to build

`mvn clean install`

## How to use 

1. Build and install the bundle
2. Configure the service com.adobe.aem.support.linkrewriter.LinkRewriterImpl
   1. Provide the regex that it needs to match
   2. Provide the output value from the regex
3. Load the page

## Developer

Patrique Legault