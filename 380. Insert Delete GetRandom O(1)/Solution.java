// 380. Insert Delete GetRandom O(1)

class RandomizedSet {
    private ArrayList<Integer> list;
    private Map<Integer, Integer> map;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    public boolean search(int val) {
        return map.containsKey(val);
    }

    public boolean insert(int val) {
        if (search(val)) return false;

        list.add(val);
        map.put(val, list.size() - 1);
        return true;
    }

    public boolean remove(int val) {
        if (!search(val)) return false;

        int index = map.get(val);
        list.set(index, list.get(list.size() - 1));
        map.put(list.get(index), index);
        list.remove(list.size() - 1);
        map.remove(val);

        return true;
    }

    public int getRandom() {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}

/**
class RandomizedSet {
    HashMap<Integer,Integer> map;
    int index;
    int[] values;
    Random r;

    public RandomizedSet() {
        map = new HashMap<>();
        index = 0;
        values = new int[64];
        r = new Random();
    }
    
    public boolean insert(int val) {
        boolean inserted = map.get(val) != null;
        if(!inserted){
            checkArraySize();
            map.put(val,index);
            values[index] = val;
            index++;
        }

        return !inserted;
    }
    
    public boolean remove(int val) {
        boolean inserted = map.get(val) != null;
        if(inserted){
            //We move the last value in the array to the position of val
            int replacedValue = values[index-1];
            int newIndex = map.get(val);

            values[newIndex] = replacedValue;
            map.replace(replacedValue, newIndex);

            map.remove(val);
            index--;
        }

        return inserted;
    }
    
    public int getRandom() {
    
        int randomIndex = r.nextInt(index);

        return values[randomIndex];
    }

    private void checkArraySize(){
        if(index == values.length){
            int[] newValues = new int[values.length*2];
            for(int i = 0; i < index; i++){
                newValues[i] = values[i];
            }
            this.values = newValues;
        }
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
 