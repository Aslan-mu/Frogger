/* A counter holds a number which is incremented on every tick, and which rolls
over at a given limit, passing on a tick to a further counter. */

class Counter {
    private int limit, count;
    private Counter next;

    Counter(int limit0, int count0, Counter next0) {
        limit = limit0;
        count = count0;
        next = next0;
    }

    void tick() {
        count++;
        if (count == limit) {
            count = 0;
            if (next != null) next.tick();
        }
    }

    // Get the count as a two-digit string
    String display() {
        if (count < 10) return "0" + count;
        else return "" + count;
    }
}