import scala.io.Source
import org.scalatest.funsuite.AnyFunSuite

class WrapperTest extends AnyFunSuite {

  test("don't wrap if max is greater than text size") {
    val sb = new StringBuilder()
    val input = "In 1991, while studying"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == input)
  }

  test("wrap after given number of chars") {
    val sb = new StringBuilder()
    val input = "In 1991, while studying computer science at"
    val expected = "In 1991, while studying computer science\nat"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == expected)
  }

  test("preserve extra spaces") {
    val sb = new StringBuilder()
    val input = "In 1991,  while studying computer science at"
    val expected = "In 1991,  while studying computer\nscience at"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == expected)
  }

  test("preserve extra endlines") {
    val sb = new StringBuilder()
    val input = "In 1991, while\nstudying computer"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == input)
  }

    test("preserve tabs") {
    val sb = new StringBuilder()
    val input = "\tIn 1991, while studying computer science at"
    val expected = "\tIn 1991, while studying computer\nscience at"
    val source = Source.fromString(input)
    Wrapper.wrap(40, source, sb.append)
    val actual = sb.toString()
    assert(actual == expected)
  }
}
