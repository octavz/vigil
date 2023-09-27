import scala.io.Source

@main
def main(filename: String): Unit = {
  val source = Source.fromFile(filename)
  try {
    Wrapper.wrap(40L, source, print)
  } finally {
    source.close()
  }
}
