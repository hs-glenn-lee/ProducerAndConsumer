package hsGlennLee.me.ProducerAndConsumer;

public abstract class Product {
	public String getContent() { throw new UnsupportedOperationException(); }
	public boolean isPoisonPill() { return false; }
}
