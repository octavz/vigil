import scala.io.Source
import org.scalatest.funsuite.AnyFunSuite

class WrapperTest extends AnyFunSuite {

  test("don't wrap if max is greater than text size") {
    val sb = new StringBuilder()
    val input = "In 1991, while studying"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    print(actual)
    assert(actual == input)
  }

  test("wrap after given number of chars") {
    val sb = new StringBuilder()
    val input = "In 1991, while studying computer science at"
    val expected =
      """In 1991, while studying computer science
        |at""".stripMargin
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == expected)
  }
}
