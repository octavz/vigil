import scala.annotation.tailrec
import scala.io.Source

object Wrapper {

  private val EOL: String = System.lineSeparator()

  private def isEOL(c: Char): Boolean = c == '\n'

  private def isSep(c: Char): Boolean = c == ' ' || isEOL(c)

  def wrap(max: Long, source: Source, write: String => Unit): Unit = {
    @tailrec
    def parse(count: Int, buffer: StringBuilder): Unit = {
      val currentToken = if (source.hasNext) Some(source.next) else None

      val newCount = currentToken match {
        case Some(c) if c == '\r' => count //ignore chars like \r
        case Some(c) if !isSep(c) => //buffer a word
          buffer.append(c)
          count
        case Some(c) if isSep(c) => //preserve line ends
          val bufferSize = buffer.size
          val word = buffer.toString()
          buffer.clear()
          if (count + bufferSize > max) { //wrap at space if is the case if not just write the word
            write(EOL)
            if (count > 0)
              write(word)
            bufferSize + 1
          } else { //write the word
            if (count > 0) write(" ")
            write(word)
            if (isEOL(c)) {
              0
            } else {
              count + bufferSize + 1
            }
          }
        case Some(_) => count //exhaustive
        case None =>
          if (count + buffer.size > max) write(EOL)
          else write(" ")
          write(buffer.toString())
          -1
      }
      if (newCount >= 0) parse(newCount, buffer)
      else ()
    }

    parse(0, StringBuilder())
  }
}
