package org.example.FMSPRO.Routes;

import java.util.*;

public class RouteNetwork {
    private final Map<String, List<ConnectionNode>> routes = new HashMap<>();

    public void addRoute(String from, String to, int millage) {
        routes.computeIfAbsent(from, k -> new ArrayList<>()).add(new ConnectionNode(to, millage));
        routes.computeIfAbsent(to, k -> new ArrayList<>()).add(new ConnectionNode(from, millage));
    }

    public Map<String, List<ConnectionNode>> GetRoutes() {
        Map<String, List<ConnectionNode>> result = new HashMap<>();

        for (Map.Entry<String, List<ConnectionNode>> entry : routes.entrySet()) {
            result.put(entry.getKey(), List.copyOf(entry.getValue()));
        }

        return Collections.unmodifiableMap(result);
    }

    public boolean hasDirectPath(String source, String destination) {
        if (!routes.containsKey(source))
            return false;

        for (ConnectionNode node : routes.get(source)) {
            if (Objects.equals(node.getDestination(), destination))
                return true;
        }
        return false;
    }

    public Path findShortestPath(String start, String target) {
        if (!routes.containsKey(start) || !routes.containsKey(target)) {
            return new Path(List.of(), -1);
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(
                Comparator.comparingInt(NodeDistance::getDistance)
        );

        for (String node : routes.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        queue.add(new NodeDistance(start, 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();

            if (current.getDistance() > distances.get(current.getNode())) {
                continue;
            }

            if (current.getNode().equals(target)) {
                break;
            }

            for (ConnectionNode connection : routes.get(current.getNode())) {
                int newDistance = current.getDistance() + connection.getMillage();

                if (newDistance >= distances.get(connection.getDestination()))
                    continue;

                distances.put(connection.getDestination(), newDistance);
                previousNodes.put(connection.getDestination(), current.getNode());
                queue.add(new NodeDistance(connection.getDestination(), newDistance));
            }
        }

        if (distances.get(target) == Integer.MAX_VALUE) {
            return new Path(List.of(), -1);
        }

        LinkedList<String> pathNodes = new LinkedList<>();
        String currentNode = target;

        while (currentNode != null) {
            pathNodes.addFirst(currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        return new Path(pathNodes, distances.get(target));
    }

}

