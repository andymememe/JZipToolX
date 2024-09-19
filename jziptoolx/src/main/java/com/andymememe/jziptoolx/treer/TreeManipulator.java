/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andymememe.jziptoolx.treer;

import javafx.scene.control.TreeItem;

/**
 *
 * @author andymememe
 */
public class TreeManipulator {
    private TreeItem<String> _root;

    /* Generate root */
    public void generateRoot(String filename) {
        _root = new TreeItem<>(filename);
    }

    /* Add node */
    public void addEntryNode(String[] fullPath) {
        TreeItem<String> newNode = new TreeItem<>(fullPath[fullPath.length-1]);
        if (fullPath.length == 1) {
            _root.getChildren().add(newNode);
        } else {
            TreeItem<String> parent = findParentItem(fullPath);
            parent.getChildren().add(newNode);
        }
    }

    /* Get node */
    public TreeItem<String> getRoot() {
        return _root;
    }

    private TreeItem<String> findParentItem(String[] fullPath) {
        TreeItem<String> curr = _root;
        for (int i = 0; i < fullPath.length-1; i++) {
            String node = fullPath[i];
            curr = curr.getChildren().filtered(
                (TreeItem<String> child) -> {
                    return child.getValue().equals(node);
                }
            ).get(0);
        }
        return curr;
    }
}
