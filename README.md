<img src="http://img.jcabi.com/logo.png" width="200px" height="48px" />

More details are here: [odesk.jcabi.com](http://odesk.jcabi.com/)

Set of classes in `com.jcabi.odesk` package is
an object oriented API of [Odesk](http://www.odesk.com):

```java
import com.jcabi.odesk.RtOdesk;
import com.jcabi.odesk.Odesk;
public class Main {
  public static void main(String[] args) {
    Odesk odesk = new RtOdesk(
      key, // odesk application key
      secret, // odesk application secret code
      token, // OAuth access token
      tsecret // OAuth access token, secret part
    );
    Team team = odesk.teams().team(/* team reference ID */);
    String ref = team.adjustments().add(
      "13369463", // contract number,
      new Cash.S("10.00"), // amount to be paid to the supplier
      new Cash.S("0.0"),
      "bonus payment for you dude", // comments
      "thanks for your good work, keep it up!" // notes
    );
  }
}
```

You need just this dependency:

```xml
<dependency>
  <groupId>com.jcabi</groupId>
  <artifactId>jcabi-odesk</artifactId>
  <version>0.3</version>
</dependency>
```

## Questions?

If you have any questions about the framework, or something doesn't work as expected,
please [submit an issue here](https://github.com/jcabi/jcabi-odesk/issues/new).
If you want to discuss, please use our [Google Group](https://groups.google.com/forum/#!forum/jcabi).

## How to contribute?

Fork the repository, make changes, submit a pull request.
We promise to review your changes same day and apply to
the `master` branch, if they look correct.

Please run Maven build before submitting a pull request:

```
$ mvn clean install -Pqulice
```
