package sokoban.model;

import java.util.ArrayList;
import java.util.List;

public class ElementStack {
    private List<Element> elements;

    public ElementStack() {
        this.elements = new ArrayList<>();
    }

    public boolean addElement(Element element) {
        if (isValidElement(element)) {
            elements.add(element);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidElement(Element newElement) {
        for (Element element : elements) {
            if (!isCombinationValid(element, newElement)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCombinationValid(Element existingElement, Element newElement) {
        if ((existingElement instanceof Ground || existingElement instanceof Wall) &&
                (newElement instanceof Ground || newElement instanceof Wall)) {
            return true;
        }
        else if ((existingElement instanceof Ground) &&
                (newElement instanceof Player || newElement instanceof Target || newElement instanceof Box)) {
            return true;
        }
        else if ((existingElement instanceof Ground) &&
                ((newElement instanceof Player || newElement instanceof Box) && elementsContainTarget())) {
            return true;
        }
        return false;
    }

    private boolean elementsContainTarget() {
        for (Element element : elements) {
            if (element instanceof Target) {
                return true;
            }
        }
        return false;
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }

    public List<Element> getElements() {
        return elements;
    }
}
