import scala.io.Source

@main
def main(): Unit = {
  val filename = "input.txt"
  val source = Source.fromFile(filename)
  try {
    Wrapper.wrap(40L, source, print)
  } finally {
    source.close()
  }
}
