package hsGlennLee.me.ProducerAndConsumer;

import java.text.BreakIterator;
import java.util.Iterator;

class Words implements Iterable<String> {

	private final String text;

	public Words(String text) {
		this.text = text;
	}

	private class WordIterator implements Iterator<String> {
		int start;
		int end;
		public WordIterator() {
			start = 0;
			end = text.indexOf("$", start);
		}

		public boolean hasNext() {
			return end != -1;
		}

		public String next() {
			String s = text.substring(start, end);
			start = end+1;
			end = text.indexOf("$", start);
			return s;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public Iterator<String> iterator() {
		return new WordIterator();
	}
}
