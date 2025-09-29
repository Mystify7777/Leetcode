
// 332. Reconstruct Itinerary
// copypaste
class Solution {
// public List<String> findItinerary(String[][] tickets) {
//     Map<String, PriorityQueue<String>> targets = new HashMap<>();
//     for (String[] ticket : tickets)
//         targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
//     List<String> route = new LinkedList();
//     Stack<String> stack = new Stack<>();
//     stack.push("JFK");
//     while (!stack.empty()) {
//         while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
//             stack.push(targets.get(stack.peek()).poll());
//         route.add(0, stack.pop());
//     }
//     return route;
// }

/** 
   public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    visit("JFK");
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();
List<String> route = new LinkedList();

void visit(String airport) {
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());
    route.add(0, airport);
}*/
public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            graph.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            graph.get(ticket.get(0)).add(ticket.get(1));
        }
        
        LinkedList<String> itinerary = new LinkedList<>();
        
        dfs("JFK", graph, itinerary);
        
        return itinerary;
    }
    
    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> itinerary) {
        PriorityQueue<String> nextAirports = graph.get(airport);
        while (nextAirports != null && !nextAirports.isEmpty()) {
            dfs(nextAirports.poll(), graph, itinerary);
        }
        itinerary.addFirst(airport);
    }
/**
import java.util.*;

public class Solution {
    static void dfs(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {
        PriorityQueue<String> arrivals = graph.get(airport);
        for (; arrivals != null && !arrivals.isEmpty();) dfs(arrivals.poll(), graph, route);
        route.addFirst(airport);
    }
    static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        int i = 0;
        while (i < tickets.size()) {
            List<String> ticket = tickets.get(i);
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
            i++;
        }
        LinkedList<String> route = new LinkedList<>();
        dfs("JFK", graph, route);
        return route;
    }
}
 */
}