package hsGlennLee.me.ProducerAndConsumer;

public abstract class SourceDatum {
	public String getContent() { throw new UnsupportedOperationException(); }
	public boolean isPoisonPill() { return false; }
}
