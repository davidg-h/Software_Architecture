package ohm.softa.a02;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 *         Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList, Iterable<Object> {
    private Element headElem;
    private int size;

    public SimpleListImpl() {
        headElem = null;
        size = 0;
    }

    @Override
    public Iterator<Object> iterator() {
        return new SimpleIteratorImpl(headElem);
    }

    @Override
    public void add(Object o) {
        Element newElem = new Element(o, null);

        if (headElem != null) {
            Element current = headElem;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newElem);
            size++;
            return;
        }
        headElem = newElem;
        size = 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public SimpleList filter(SimpleFilter filter) {
        SimpleList result = new SimpleListImpl();
        for (Object o : this) {
            if (filter.include(o)) {
                result.add(o);
            }
        }
        return result;
    }

    // region private classes (Elements, Iterator)

    // inner class is static, so it won't need the outer class to be accessed and
    // private so access is limited to only the list
    private static class Element {
        private final Object item;
        private Element next;

        Element(Object item, Element next) {
            this.item = item;
            this.next = next;
        }

        public Object getItem() {
            return item;
        }

        public Element getNext() {
            return next;
        }

        void setNext(Element next) {
            this.next = next;
        }
    }

    // non-static because it is an iterator of the list and so a part of the list
    private class SimpleIteratorImpl implements Iterator<Object> {
        Element cur;

        SimpleIteratorImpl(Element e) {
            cur = e;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Object next() {
            Object tmp = cur.getItem();
            cur = cur.getNext();
            return tmp;
        }
    }
    // endregion
}
