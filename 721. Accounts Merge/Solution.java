//copypasted
//something new
// explain in detail
// 721. Accounts Merge
class Solution {
    class UnionFind {
        int[] parent;
        int[] weight;
        
        public UnionFind(int num) {
            parent = new int[num];
            weight = new int[num];
            
            for(int i =  0; i < num; i++) {
                parent[i] = i;
                weight[i] = 1;
            }
        }
        
        public void union(int a, int  b) {
            int rootA = root(a);
            int rootB = root(b);
            
            if (rootA == rootB) {
                return;
            }
            
            if (weight[rootA] > weight[rootB]) {
                parent[rootB] = rootA;
                weight[rootA] += weight[rootB];
            } else {
                parent[rootA] = rootB;
                weight[rootB] += weight[rootA];
            }
        }
        
        public int root(int a) {
            if (parent[a] == a) {
                return a;
            }
            
            parent[a] = root(parent[a]);
            return parent[a];
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int size = accounts.size();

        UnionFind uf = new UnionFind(size);

        // prepare a hash with unique email address as key and index in accouts as value
        HashMap<String, Integer> emailToId = new  HashMap<>();
        for(int i = 0; i < size; i++) {
            List<String> details = accounts.get(i);
            for(int j = 1; j < details.size(); j++) {
                String email = details.get(j);
                
				// if we have already seen this email before, merge the account  "i" with previous account
				// else add it to hash
                if (emailToId.containsKey(email)) {
                    uf.union(i, emailToId.get(email));
                } else  {
                    emailToId.put(email, i);
                }
            }
        }
        
        // prepare a hash with index in accounts as key and list of unique email address for that account as value
        HashMap<Integer, List<String>> idToEmails = new HashMap<>();
        for(String key : emailToId.keySet()) {
            int root = uf.root(emailToId.get(key));
            
            if (!idToEmails.containsKey(root)) {
                idToEmails.put(root, new ArrayList<String>());
            }
            
            idToEmails.get(root).add(key);
        }
        
        // collect the emails from idToEmails, sort it and add account name at index 0 to get the final list to add to final return List
        List<List<String>> mergedDetails =  new ArrayList<>();
        for(Integer id : idToEmails.keySet()) {
            List<String> emails =  idToEmails.get(id);
            Collections.sort(emails);
            emails.add(0, accounts.get(id).get(0));
            
            mergedDetails.add(emails);
        }
        
        return  mergedDetails;
    }
}

//similar yet faster approach

class Solution2 {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int counter = 0;
        Map<String, String> emailToName = new HashMap<>();
        Map<String, Integer> emailToId = new HashMap<>();

        int[] parents = new int[10000];

        for (int i = 0; i < 10000; i++) parents[i] = -1; // Each email is in their own set

        int id;
        int firstID;
        for (List<String> account: accounts) {
            String name = account.get(0);
            String firstEmail = account.get(1);

            if (!emailToId.containsKey(firstEmail)) {
                emailToId.put(firstEmail, counter++);
                emailToName.put(firstEmail, name);
            }
            firstID = emailToId.get(firstEmail);
            
            int n = account.size();
        
            for (int i = 2; i < n; i++) {                
                String email = account.get(i);

                if (!emailToId.containsKey(email)) {
                    emailToId.put(email, counter++);
                    emailToName.put(email, name);
                }

                id = emailToId.get(email);
                union(parents, firstID, id);
            }
        }
        Map<Integer, TreeSet<String>> grouped = new HashMap<>();
        for (Map.Entry<String, Integer> entry: emailToId.entrySet()) {
            String email = entry.getKey();
            id = entry.getValue();
            int root = find(parents, id);

            TreeSet<String> group = grouped.computeIfAbsent(root, (k) -> new TreeSet<>());
            group.add(email);
        }

        List<List<String>> ans = new ArrayList<>();
        for (TreeSet<String> entry: grouped.values()) {
            String user = emailToName.get(entry.first());

            List<String> list = new ArrayList<>();
            list.add(user);
            list.addAll(entry);
            ans.add(list);
        }

        return ans; 


    }

    public int find(int[] parents, int i) {
        while (parents[i] >= 0) {
            i = parents[i];
        }
        return i;
    }

    public void union(int[] parents, int i, int j) {
        if (i == j) {
            return;
        }

        int parentI = find(parents, i);
        int parentJ = find(parents, j);

        if (parentI == parentJ) return;
        if (parents[parentI] > parents[parentJ]) {
            parents[parentJ] += parents[parentI];
            parents[parentI] = parentJ;
        } else {
            parents[parentI] += parents[parentJ];
            parents[parentJ] = parentI;
        }
    }
}