package ohm.softa.a02;

import java.util.Iterator;
/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList {
    Element head;
    Element tail;

    public SimpleListImpl(){
        head = null;
        tail = head;
    }
    @Override
    public void add(Object o) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public SimpleList filter(SimpleFilter filter) {
        return null;
    }

    @Override
    public boolean include(Object item) {
        return false;
    }

    // TODO: Implement the required methods.

    // inner class is static so it wont need the outer class to be accessed and private so access is limited
    private static class Element {
        Object item;
        Element next;

        Element(Object item, Element next){
            this.item = item;
            this.next = next;
        }

        public Object getItem() {
            return item;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }
    }

    private class SimpleIteratorImpl implements Iterator<Object>{
        Element cur;

        SimpleIteratorImpl(Element e){
            cur = e;
        }
        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Object next() {
            Element next = cur.next;
            cur = cur.next;
            return next.item;
        }
    }
}
