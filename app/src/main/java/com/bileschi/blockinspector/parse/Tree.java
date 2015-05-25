package com.bileschi.blockinspector.parse;

import java.util.LinkedList;
import java.util.List;

// http://stackoverflow.com/questions/3522454/java-tree-data-structure
public class Tree<T> {

    public T data;
    public Tree<T> parent;
    public List<Tree<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    private List<Tree<T>> elementsIndex;

    public Tree(T data) {
        this.data = data;
        this.children = new LinkedList<Tree<T>>();
        this.elementsIndex = new LinkedList<Tree<T>>();
        this.elementsIndex.add(this);
    }

    public Tree<T> addChild(T child) {
        Tree<T> childNode = new Tree<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(Tree<T> node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public Tree<T> findTreeNode(Comparable<T> cmp) {
        for (Tree<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "children=" + children +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        if (children != null ? !children.equals(tree.children) : tree.children != null)
            return false;
        if (data != null ? !data.equals(tree.data) : tree.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }
}
