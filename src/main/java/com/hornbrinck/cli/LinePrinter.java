package com.hornbrinck.cli;

import com.hornbrinck.graph.AbstractNode;
import com.hornbrinck.metro.MetroStation;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class LinePrinter extends ConsolePrinter {
    public static void printLine(MetroStation head) {
        AbstractNode<String, String> current = head;

        // station
        //    |
        //  -------------
        //  |     |     |
        //
        //
        //
        //

        Queue<AbstractNode<String, String>> queue = new ArrayDeque<>();
        queue.add(current);
        HashSet<AbstractNode<String, String>> set = new HashSet<>();
        while (!queue.isEmpty()) {

            current = queue.poll();

            System.out.print("Prev: " + current.getData());
            System.out.print("| next --> ");
            for (AbstractNode<String, String> next : current.getPrevAndNextNodes()) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
                System.out.print(next.getData() + " ");
            }

            System.out.println();

        }

    }

}
