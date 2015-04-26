# Spring Social Mockbin

Spring Social Mockbin builds on [Spring Social Bootstrap SDK](https://github.com/robinhowlett/spring-social-bootstrap/tree/master/spring-social-bootstrap-sdk) to provide a Java API client for [Mockbin](http://mockbin.com/).

Mockbin, created by [Mashape](https://www.mashape.com/), "allows you to generate custom endpoints to test, mock, and track HTTP requests & responses between libraries, sockets and APIs".

This project follows the Spring Social convention of declaring a base interface (`Mockbin`) whose implementation (`MockbinTemplate`) provides access to implementations of Bin and Utility resource API operations:

```java
Mockbin mockbin = new MockbinTemplate();

// Create Bin
String newBinId = mockbin.binOperations().create(alfHarResponseWithEightHeadersAndHelloWorldHtmlBody());

// Inspect Bin
AlfHarResponse harResponseObject = mockbin.binOperations().inspect(newBinId);

// Request Bin
ResponseEntity<Object> response = mockbin.binOperations().request(
		newBinId, POST, "/extra/path", "a=b&c=d", eightHttpHeaders(), helloWorldHtmlContent().getText());

// Bin Access Log
AlfHar accessLogHar = mockbin.binOperations().accessLog(newBinId);

// Debugging: Echo
ResponseEntity<Object> echo = 
		mockbin.debuggingOperations().echo(POST, "/extra/path", "a=b&c=d", eightHttpHeaders(), helloWorldHtmlContent().getText(), String.class);

// Debugging: HTTP Request
AlfHarEntry entry = 
		mockbin.debuggingOperations().request(PUT, "/extra/path", "a=b&c=d", eightHttpHeaders(), helloWorldHtmlContent().getText());

// Debugging: HTTP Archive
AlfHar har = 
		mockbin.debuggingOperations().har(PATCH, "/extra/path", "a=b&c=d", eightHttpHeaders(), helloWorldHtmlContent().getText());

// IP: Origin IP
String originIp = mockbin.ipOperations().originIp();

// IP: Proxied IPs
List<String> proxiedIps = mockbin.ipOperations().proxiedIps();

// Status: Custom Status
ResponseEntity<StatusResponse> customStatusResponse = mockbin.statusOperations().customStatus(HttpStatus.PERMANENT_REDIRECT);

// Headers: List Request Headers
RequestHeaders requestHeaders = mockbin.headerOperations().get(eightHttpHeaders());

// Cookies: List All Cookies
List<HarCookie> cookies = mockbin.cookieOperations().get(cookiesAsStringMap());

// Redirect: Redirect to URL
ResponseEntity<String> redirectResponse = mockbin.redirectOperations().redirect(PERMANENT_REDIRECT, "http://example.com");

// Redirect: Custom Redirect Loop (3 redirects)
ResponseEntity<String> redirectLoopResponse = mockbin.redirectOperations().redirectLoop(MOVED_PERMANENTLY, 3, "http://example.com");

// Stream: Stream Output (4 chunks)
ResponseEntity<String> streams = mockbin.streamOperations().output(4);

// Delay: Delayed Response (5 seconds)
DelayResponse delayedResponse = mockbin.delayOperations().delay(5000);

// Compression: GZipped Response Body
ResponseEntity<String> responseEntity = mockbin.compressionOperations().gzip();
```
