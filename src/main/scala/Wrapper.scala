import scala.annotation.tailrec
import scala.io.Source

object Wrapper {

  private val EOL: String = "\n" //this works better on windows

  def wrap(max: Long, source: Source, write: String => Unit): Unit = {
    val buffer = StringBuilder()

    @tailrec
    def parse(count: Int = 0): Unit = {
      val currentToken = if (source.hasNext) Some(source.next) else None

      val (bufferSize, word) = (buffer.size, buffer.toString)
      val overflow = count + bufferSize + 1 > max
      val newCount = currentToken match {
        case Some('\r') => count // ignore  \r on windows
        case Some(' ') => //buffer new word, it will not preserve the whitespaces at the start of the line
          buffer.clear()
          if (overflow) { // wrap at space if is the case if not just write the word
            write(s"$EOL$word")
            bufferSize + 1
          } else {
            val withSpace =
              if (count > 0) s" $word" else word //don't add spaces at the start of the row
            write(withSpace)
            count + withSpace.length()
          }
        case Some('\n') => //move to another row
          buffer.clear()
          if (overflow) {
            write(s"$EOL$word")
            bufferSize
          } else {
            if (count > 0) write(" ") //don't add spaces at the start of the row
            write(s"$word$EOL")
            0
          }
        case Some(c) => // buffer a word
          buffer.append(c)
          count
        case None =>
          if (overflow) write(EOL) else write(" ")
          write(buffer.toString())
          -1
      }
      if (newCount >= 0) parse(newCount)
      else ()
    }

    parse()
  }
}
